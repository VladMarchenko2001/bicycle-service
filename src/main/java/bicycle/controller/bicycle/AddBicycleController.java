package bicycle.controller.bicycle;

import bicycle.lib.Injector;
import bicycle.model.Bicycle;
import bicycle.model.Manufacturer;
import bicycle.service.BicycleService;
import bicycle.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBicycleController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("bicycle");
    private final BicycleService bicycleService = (BicycleService) injector
            .getInstance(BicycleService.class);
    private final ManufacturerService manufacturerService = (ManufacturerService) injector
            .getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/bicycles/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String model = req.getParameter("model");
        long manufacturerId = Long.parseLong(req.getParameter("manufacturer_id"));
        Manufacturer manufacturer = manufacturerService.get(manufacturerId);
        Bicycle bicycle = new Bicycle(model, manufacturer);
        bicycleService.create(bicycle);
        resp.sendRedirect("/bicycles/add");
    }
}
