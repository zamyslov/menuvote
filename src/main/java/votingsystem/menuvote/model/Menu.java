package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menus_idx")})
public class Menu extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @CollectionTable(name = "restaurants", joinColumns = @JoinColumn(name = "id"))
    private Restaurant restaurant;

    @Column(name = "date")
    @NotBlank
    private Date date;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<MenuDishes> menuDishes;

    @OneToMany(fetch = FetchType.LAZY)
    @CollectionTable(name = "votes", joinColumns = @JoinColumn(name = "menu_id"))
    private Set<Vote> votes;

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

    public Set<MenuDishes> getMenuDishes() {
        return menuDishes;
    }

    public void setMenuDishes(Set<MenuDishes> menuDishes) {
        this.menuDishes = menuDishes;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Menu that = (Menu) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, restaurant);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "restaurant=" + restaurant +
                ", date=" + date +
                ", dishes=" + menuDishes +
                ", votes=" + votes +
                ", id=" + id +
                '}';
    }
}
