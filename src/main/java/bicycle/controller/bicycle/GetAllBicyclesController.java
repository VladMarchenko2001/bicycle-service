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

public class GetAllBicyclesController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("bicycle");
    private final BicycleService bicycleService = (BicycleService) injector
            .getInstance(BicycleService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Bicycle> allBicycles = bicycleService.getAll();
        req.setAttribute("bicycles", allBicycles);
        req.getRequestDispatcher("/WEB-INF/views/bicycles/all.jsp").forward(req, resp);
    }
}
