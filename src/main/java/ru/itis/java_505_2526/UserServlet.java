package ru.itis.java_505_2526;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/users")
public class UserServlet extends HttpServlet {
    // Обработка GET запроса (например, /api/users?id=10)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id"); // Получение Query параметра
        String userAgent = req.getHeader("User-Agent"); // Чтение заголовка

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            out.println("Ошибка: укажите id пользователя");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK); // 200
            out.println("Вы запросили пользователя с ID: " + idParam);
            out.println("Ваш браузер: " + userAgent);
        }
    }

    // Обработка POST запроса (создание ресурса)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Читаем параметры из тела запроса (x-www-form-urlencoded)
        String name = req.getParameter("name");

        resp.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
        resp.setContentType("application/json");
        resp.getWriter().write("{\"message\": \"Пользователь " + name + " создан!\"}");
    }

    // Как обработать PATCH? (в HttpServlet нет doPatch)
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            // логика для PATCH
            resp.getWriter().write("Обработан PATCH запрос");
        } else {
            // Для остальных методов вызываем стандартную реализацию
            super.service(req, resp);
        }
    }
}
