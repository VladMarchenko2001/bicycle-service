package bicycle.controller.bicycle;

import bicycle.lib.Injector;
import bicycle.service.BicycleService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBicycleController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("bicycle");
    private final BicycleService bicycleService = (BicycleService) injector
            .getInstance(BicycleService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        bicycleService.delete(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("/bicycles/all");
    }
}
