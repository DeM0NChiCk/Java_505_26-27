package ru.itis.java_505_2526.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class NioHttpServer {
    public static void main(String[] args) throws IOException {
        // 1. Создаем Селектор
        Selector selector = Selector.open();

        // 2. Создаем серверный канал
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(8080));

        // ВАЖНО: Делаем канал неблокирующим!
        serverSocket.configureBlocking(false);

        // 3. Регистрируем серверный канал в селекторе на событие "Готов принять подключение"
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        log.info("NIO Сервер запущен на порту 8080. Поток: {}", Thread.currentThread().getName());

        // 4. Бесконечный цикл событий (Event Loop)
        while (true) {
            // Блокируется, пока не произойдет хотя бы одно событие
            selector.select();

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                // Удаляем ключ, чтобы не обработать его дважды
                keyIterator.remove();

                if (key.isAcceptable()) {
                    // Событие: пришел новый клиент
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);

                    // Регистрируем клиента на событие "Готов к чтению"
                    client.register(selector, SelectionKey.OP_READ);
                    log.info("Подключился новый клиент: {}", client.getRemoteAddress());

                } else if (key.isReadable()) {
                    // Событие: клиент прислал данные
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024); // Выделяем память

                    int bytesRead = client.read(buffer);
                    if (bytesRead == -1) {
                        client.close();
                        log.info("Клиент отключился");
                        continue;
                    }

                    // Переключаем буфер из режима записи в режим чтения
                    buffer.flip();
                    String request = new String(buffer.array(), 0, bytesRead);

                    log.info("Получены данные от клиента:\n{}", request.trim());

                    // Формируем и отправляем простейший HTTP-ответ
                    String httpResponse = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n\r\n" +
                            "Hello from NIO!";
                    client.write(ByteBuffer.wrap(httpResponse.getBytes()));
                    client.close();
                }
            }
        }
    }
}
