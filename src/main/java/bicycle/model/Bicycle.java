package bicycle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bicycle {
    private Long id;
    private String model;
    private Manufacturer manufacturer;
    private List<User> users;

    public Bicycle(String model, Manufacturer manufacturer) {
        this.model = model;
        this.manufacturer = manufacturer;
        users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", model='" + model + '\''
                + ", manufacturer=" + manufacturer
                + ", drivers=" + users
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bicycle bicycle = (Bicycle) o;
        return Objects.equals(id, bicycle.id) && Objects.equals(model, bicycle.model)
                && Objects.equals(manufacturer, bicycle.manufacturer)
                && Objects.equals(users, bicycle.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacturer, users);
    }
}
