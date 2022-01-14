package bicycle.controller.user;

import bicycle.lib.Injector;
import bicycle.service.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("bicycle");
    private final UserService userService = (UserService) injector
            .getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        userService.delete(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("/users/all");
    }
}
