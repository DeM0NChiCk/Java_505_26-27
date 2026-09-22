package ru.itis.java_505_2526;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/session-demo")
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Создаем Cookie
        Cookie myCookie = new Cookie("user_theme", "dark");
        myCookie.setMaxAge(60 * 60 * 24); // Живет 1 день
        resp.addCookie(myCookie); // Отправляем браузеру

        // Читаем Cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user_theme")) {
                    log.info("Тема пользователя: {}", c.getValue());
                }
            }
        }

        // Получаем сессию (если нет - создает новую, Tomcat сам выдаст JSESSIONID)
        HttpSession session = req.getSession();

        Integer visits = (Integer) session.getAttribute("visit_count");
        if (visits == null) {
            visits = 1;
        } else {
            visits++;
        }
        session.setAttribute("visit_count", visits); // Сохраняем в сессию

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write("Вы посетили эту страницу " + visits + " раз(а).");
    }
}
