package bicycle.dao;

import bicycle.model.Bicycle;
import java.util.List;

public interface BicycleDao extends GenericDao<Bicycle> {
    List<Bicycle> getAllByUser(Long driverId);
}
