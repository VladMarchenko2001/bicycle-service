package bicycle.service;

import bicycle.dao.BicycleDao;
import bicycle.lib.Inject;
import bicycle.lib.Service;
import bicycle.model.Bicycle;
import bicycle.model.User;
import java.util.List;

@Service
public class BicycleServiceImpl implements BicycleService {
    @Inject
    private BicycleDao bicycleDao;

    @Override
    public void addUserToBicycle(User user, Bicycle bicycle) {
        bicycle.getUsers().add(user);
        bicycleDao.update(bicycle);
    }

    @Override
    public void removeUserFromBicycle(User user, Bicycle bicycle) {
        bicycle.getUsers().remove(user);
        bicycleDao.update(bicycle);
    }

    @Override
    public List<Bicycle> getAllByUser(Long driverId) {
        return bicycleDao.getAllByUser(driverId);
    }

    @Override
    public Bicycle create(Bicycle bicycle) {
        return bicycleDao.create(bicycle);
    }

    @Override
    public Bicycle get(Long id) {
        return bicycleDao.get(id).get();
    }

    @Override
    public List<Bicycle> getAll() {
        return bicycleDao.getAll();
    }

    @Override
    public Bicycle update(Bicycle bicycle) {
        return bicycleDao.update(bicycle);
    }

    @Override
    public boolean delete(Long id) {
        return bicycleDao.delete(id);
    }
}
