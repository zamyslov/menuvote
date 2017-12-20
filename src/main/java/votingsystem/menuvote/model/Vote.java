package votingsystem.menuvote.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","vote_date"}, name = "votes_idx")})
public class Vote extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "user_id"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @CollectionTable(name = "menus", joinColumns = @JoinColumn(name = "menu_id"))
    private Menu menu;

    @Column(name = "vote_date")
    @NotBlank
    private Date date;

    public Vote(Integer id, User user, Menu menu, Date date) {
        super(id);
        this.user = user;
        this.menu = menu;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", menu=" + menu +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
