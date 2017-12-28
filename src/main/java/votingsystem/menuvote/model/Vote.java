package votingsystem.menuvote.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "votes_idx")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "user_id"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @CollectionTable(name = "menus", joinColumns = @JoinColumn(name = "menu_id"))
    private Menu menu;

    public Vote(Integer id, User user, Menu menu) {
        super(id);
        this.user = user;
        this.menu = menu;
    }

    public Vote() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Vote that = (Vote) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", menu=" + menu +
                ", id=" + id +
                '}';
    }
}
