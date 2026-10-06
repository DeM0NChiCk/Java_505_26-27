package ru.itis.java_505_2627;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("/users-view")
public class UserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet {}", req);
        // 1. Получаем данные (типа из БД)
        List<String> usersList = List.of("Алексей", "Мария", "Иван");

        // 2. Кладем данные в атрибуты запроса
        req.setAttribute("users", usersList);
        req.setAttribute("isAdmin", true);

        // 3. Перенаправляем (forward) на JSP страницу
        req.getRequestDispatcher("/api/users").forward(req, resp);
    }
}
