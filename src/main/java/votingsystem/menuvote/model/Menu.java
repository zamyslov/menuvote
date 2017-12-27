package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Date;
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

    @OneToMany(fetch = FetchType.EAGER)
    @CollectionTable(name = "menus_dishes", joinColumns = @JoinColumn(name = "menu_id"))
    private Set<MenuDishes> menuDishes;

    @OneToMany(fetch = FetchType.EAGER)
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
