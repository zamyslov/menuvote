package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "menu_date"}, name = "menus_idx")})
public class Menu extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @CollectionTable(name = "restaurants", joinColumns = @JoinColumn(name = "restaurant_id"))
    private Restaurant restaurant;

    @Column(name = "menu_date")
    @NotBlank
    private Date date;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Dish> dishes;

    public Menu() {
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "restaurant=" + restaurant +
                ", date=" + date +
                ", dishes=" + dishes +
                ", id=" + id +
                '}';
    }
}
