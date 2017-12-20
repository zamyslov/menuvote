package votingsystem.menuvote.model;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AbstractNamedEntity extends AbstractBaseEntity{
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    public AbstractNamedEntity(Integer id, @NotBlank @Size(min = 2, max = 100) String name) {
        super(id);
        this.name = name;
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
