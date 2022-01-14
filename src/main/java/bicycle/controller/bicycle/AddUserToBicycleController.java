package bicycle.controller.bicycle;

import bicycle.lib.Injector;
import bicycle.model.Bicycle;
import bicycle.model.User;
import bicycle.service.BicycleService;
import bicycle.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserToBicycleController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("bicycle");
    private final BicycleService bicycleService = (BicycleService) injector
            .getInstance(BicycleService.class);
    private final UserService userService = (UserService) injector
            .getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/bicycles/users/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        long userId = Long.parseLong(req.getParameter("user_id"));
        long bicycleId = Long.parseLong(req.getParameter("bicycle_id"));
        User user = userService.get(userId);
        Bicycle bicycle = bicycleService.get(bicycleId);
        bicycleService.addUserToBicycle(user, bicycle);
        resp.sendRedirect("/bicycles/users/add");
    }
}
