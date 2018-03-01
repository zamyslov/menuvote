package votingsystem.menuvote.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuDishesId implements Serializable {
    @Column(name = "menu_id")
    private int menu_id;

    @Column(name = "dish_id")
    private int dish_id;


    public MenuDishesId() {

    }

    public MenuDishesId(int menu_id, int dish_id) {
        this.menu_id = menu_id;
        this.dish_id = dish_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MenuDishesId that = (MenuDishesId) o;
        return Objects.equals(menu_id, that.menu_id) &&
                Objects.equals(dish_id, that.dish_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menu_id, dish_id);
    }
}
