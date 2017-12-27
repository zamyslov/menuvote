package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "menus_dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id, dish_id"}, name = "menus_dishes_idx")})
public class MenuDishes extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @CollectionTable(name = "menus", joinColumns = @JoinColumn(name = "menus_id"))
    private Menu menu;

    @Column(name = "price")
    @NotBlank
    private Double price;

    @OneToMany(fetch = FetchType.EAGER)
    @CollectionTable(name = "dishes", joinColumns = @JoinColumn(name = "id"))
    private Set<Dish> dishes;

    public MenuDishes() {
    }

    public MenuDishes(Menu menu, Double price, Set<Dish> dishes) {
        this.menu = menu;
        this.price = price;
        setDishes(dishes);
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "MenuDishes{" +
                "menu=" + menu +
                ", price=" + price +
                ", dishes=" + dishes +
                ", id=" + id +
                '}';
    }
}
