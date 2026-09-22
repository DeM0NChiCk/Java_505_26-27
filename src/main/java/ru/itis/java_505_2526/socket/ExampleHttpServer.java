package ru.itis.java_505_2526.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleHttpServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен на порту 8080...");

            while (true) {
                // Ожидаем подключения клиента (браузера)
                Socket socket = serverSocket.accept();

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                // Читаем первую строку запроса (Например: GET /hello HTTP/1.1)
                String requestLine = input.readLine();
                System.out.println("Получен запрос: " + requestLine);

                // Если нужно, можем прочитать заголовки (до пустой строки)
                // String header;
                // while (!(header = input.readLine()).isEmpty()) {
                //     System.out.println(header);
                // }

                // Формируем сырой HTTP-ответ
                String html = "<html><body><h1>Hello from Example Sockets!</h1></body></html>";

                output.println("HTTP/1.1 200 OK"); // Стартовая строка
                output.println("Content-Type: text/html; charset=UTF-8"); // Заголовки
                output.println("Content-Length: " + html.getBytes().length);
                output.println("Connection: close");
                output.println(); // Пустая строка - обязательный разделитель!
                output.println(html); // Тело ответа

                socket.close(); // Закрываем соединение
            }
        }
    }
}
