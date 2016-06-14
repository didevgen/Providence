package dto.university;

/**
 * Created by Eugne on 28.05.2016.
 */
public abstract class _NamedDto {
    private String uuid;
    private String name;
    private String fullName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
