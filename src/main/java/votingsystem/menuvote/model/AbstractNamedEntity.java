package votingsystem.menuvote.model;

import javax.persistence.Column;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public class AbstractNamedEntity extends AbstractBaseEntity{
    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    protected String name;

    public AbstractNamedEntity(Integer id, @NotBlank @Size(min = 2, max = 100) String name) {
        super(id);
        this.name = name;
    }

    public AbstractNamedEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s), (%s)", getClass().getName(), id, name);
    }

}
