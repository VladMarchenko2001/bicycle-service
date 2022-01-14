package bicycle.service;

import bicycle.model.Bicycle;
import bicycle.model.User;
import java.util.List;

public interface BicycleService extends GenericService<Bicycle> {
    void addUserToBicycle(User user, Bicycle bicycle);

    void removeUserFromBicycle(User user, Bicycle bicycle);

    List<Bicycle> getAllByUser(Long driverId);
}
