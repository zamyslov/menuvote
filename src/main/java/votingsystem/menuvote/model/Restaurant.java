package votingsystem.menuvote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 5, max = 150)
    private String address;


    public Restaurant(Integer id, String name, @NotBlank @Size(min = 5, max = 150) String address) {
        super(id, name);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
