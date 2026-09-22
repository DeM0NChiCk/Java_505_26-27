package ru.itis.java_505_2526;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users-view")
public class UserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Получаем данные (типа из БД)
        List<String> usersList = List.of("Алексей", "Мария", "Иван");

        // 2. Кладем данные в атрибуты запроса
        req.setAttribute("users", usersList);
        req.setAttribute("isAdmin", true);

        // 3. Перенаправляем (forward) на JSP страницу
        req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    }
}
