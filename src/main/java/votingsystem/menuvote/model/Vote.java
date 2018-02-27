package votingsystem.menuvote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "menu_id"}, name = "votes_idx")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "votes")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "menu")
    private Menu menu;

    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    @Type(type = "java.time.LocalDate")
    private LocalDate date = LocalDate.now();

    public Vote(Integer id, User user, Menu menu, LocalDate date) {
        super(id);
        this.user = user;
        this.menu = menu;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
