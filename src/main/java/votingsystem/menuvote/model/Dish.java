package votingsystem.menuvote.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "dishes_unique_name_idx")})
public class Dish extends AbstractNamedEntity {

    public Dish(Integer id, String name) {
        super(id, name);
    }

    public Dish(Dish u) {
        this(u.getId(), u.getName());
    }

    public Dish() {
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name +
                ", id=" + id +
                '}';
    }
}
