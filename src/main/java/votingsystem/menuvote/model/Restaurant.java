package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 5, max = 150)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("date DESC")
    private List<Menu> menus;

    public Restaurant(Restaurant u) {
        this(u.getId(), u.getName(), u.getAddress());
    }

    public Restaurant(Integer id, String name, @NotBlank @Size(min = 5, max = 150) String address) {
        super(id, name);
        this.address = address;
    }

    public Restaurant() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Restaurant that = (Restaurant) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name +
                ", address='" + address +
                ", id=" + id +
                '}';
    }
}
