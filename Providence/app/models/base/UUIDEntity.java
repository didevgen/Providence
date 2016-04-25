package models.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class UUIDEntity implements Serializable {

    private static final long serialVersionUID = 2838583836051699523L;

    @Column(unique = true, nullable = false)//, columnDefinition = "uuid")
//    @org.hibernate.annotations.Type(type = "persistence.types.UUIDType")
    protected String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
