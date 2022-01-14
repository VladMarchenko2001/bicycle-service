package bicycle.controller.bicycle;

import bicycle.lib.Injector;
import bicycle.model.Bicycle;
import bicycle.service.BicycleService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMyCurrentBicyclesController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("bicycle");
    private static final String USER_ID = "user_id";
    private final BicycleService bicycleService = (BicycleService) injector
            .getInstance(BicycleService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long driverId = (Long) session.getAttribute(USER_ID);
        List<Bicycle> carsByDriver = bicycleService.getAllByUser(driverId);
        req.setAttribute("bicycles", carsByDriver);
        req.getRequestDispatcher("/WEB-INF/views/bicycles/all.jsp").forward(req, resp);
    }
}
