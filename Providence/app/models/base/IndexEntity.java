package models.base;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class IndexEntity extends UUIDEntity {

    private static final long serialVersionUID = -6027495641630765544L;

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "INT(11)")
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @NotNull
    private Long id;


    public Long getId() {
        return id == null ? null : id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
