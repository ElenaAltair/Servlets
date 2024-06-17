package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html,charset=UTF-8");
        var path = req.getRequestURI();
        if (path.matches("/api/posts/\\d+")) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            resp.getWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            resp.getWriter().println("Был обработан Get-запрос и искал пост по id = " + id + " \n");
            controller.getById(id, resp);
        } else if (path.matches("/api/posts")) {
            resp.getWriter().println("Был обработан Get-запрос и получен список всех постов: \n");
            controller.all(resp);
        }
    }

    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html,charset=UTF-8");
        resp.getWriter().println("Был обработан Post-запрос и в коллекцию был добавлен новый пост \n");
        controller.save(req.getReader(), resp);
    }

    @Override
    protected synchronized void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html,charset=UTF-8");
        var path = req.getRequestURI();
        resp.getWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        resp.getWriter().println("Был обработан Delete-запрос и из коллекции был удалён пост под id = " + id + " \n");
        controller.removeById(id, resp);
    }
    /*
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            resp.setContentType("text/html,charset=UTF-8");


            // primitive routing
            if (method.equals("GET") && path.equals("/api/posts")) {
                resp.getWriter().println("Был обработан Get-запрос и получен список всех постов: \n");
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                resp.getWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
                resp.getWriter().println("Был обработан Get-запрос и искал пост по id = " + id + " \n");
                controller.getById(id, resp);
                return;
            }
            if (method.equals("POST") && path.equals("/api/posts")) {
                resp.getWriter().println("Был обработан Post-запрос и в коллекцию был добавлен новый пост \n");
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
                // easy way
                resp.getWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                resp.getWriter().println("Был обработан Delete-запрос и из коллекции был удалён пост под id = " + id + " \n");
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            //resp.getWriter().println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    */
}