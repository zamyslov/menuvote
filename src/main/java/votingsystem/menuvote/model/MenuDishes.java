package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Objects;

@Entity
//@Table(name = "menus_dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id, dish_id"}, name = "menus_dishes_idx")})
@Table(name = "menus_dishes")
public class MenuDishes {

    @EmbeddedId
    private MenuDishesId id = new MenuDishesId();

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
    @MapsId("menu_id")
    private Menu menu;

    @Column(name = "price")
    @NotBlank
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "dish_id", referencedColumnName = "id", nullable = false)
    @MapsId("dish_id")
    private Dish dish;

    public MenuDishes() {
    }

    public MenuDishes(Menu menu, Dish dish, Double price) {
        this.menu = menu;
        this.price = price;
        this.dish = dish;
        this.id = new MenuDishesId(menu.getId(), dish.getId());
    }

    public MenuDishesId getId() {
        return id;
    }

    public void setId(MenuDishesId id) {
        this.id = id;
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
