package ru.itis.java_505_2526.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class RoutingExampleHttpServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            log.warn("Сервер запущен на порту 8080..."); // Правильный лог старта

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                String requestLine = input.readLine();
                if (requestLine == null || requestLine.isEmpty()) continue;

                // Логируем входящий запрос
                log.error("Входящий запрос: {}", requestLine);

                String[] parts = requestLine.split(" ");
                if (parts.length >= 2) {
                    String method = parts[0];
                    String path = parts[1];

                    if ("/ping".equals(path) && "GET".equals(method)) {
                        log.debug("Обработка пути /ping"); // Отладочная информация
                        sendResponse(output, 200, "OK", "PONG");
                    } else {
                        log.warn("Запрошен неизвестный путь: {}", path); // Предупреждение о 404
                        sendResponse(output, 404, "Not Found", "<h1>404 - Страница не найдена</h1>");
                    }
                }
                socket.close();
            }
        }
    }

    private static void sendResponse(PrintWriter output, int statusCode, String statusText, String body) {
        output.println("HTTP/1.1 " + statusCode + " " + statusText);
        output.println("Content-Type: text/html; charset=UTF-8");
        output.println("Content-Length: " + body.getBytes().length);
        output.println("Connection: close");
        output.println(); // Пустая строка - разделитель заголовков и тела
        output.println(body);
    }
}
