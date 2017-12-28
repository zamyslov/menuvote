package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Embeddable
@Table(name = "menus_dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id, dish_id"}, name = "menus_dishes_idx")})
public class MenuDishes extends MenuDishesId {

    @EmbeddedId
    private MenuDishesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menu_id")
    private Menu menu;

    @Column(name = "price")
    @NotBlank
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dish_id")
    private Dish dish;

    public MenuDishes() {
    }

    public MenuDishes(Menu menu, Double price, Dish dish) {
        this.menu = menu;
        this.price = price;
        this.dish = dish;
        this.id = new MenuDishesId(menu.getId(), dish.getId());
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

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public String toString() {
        return "MenuDishes{" +
                "menu=" + menu +
                ", price=" + price +
                ", dishes=" + dish +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MenuDishes that = (MenuDishes) o;
        return Objects.equals(menu, that.menu) &&
                Objects.equals(dish, that.dish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menu, dish);
    }


}
