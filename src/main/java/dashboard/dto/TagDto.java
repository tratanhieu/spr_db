package dashboard.dto;

public class TagDto {
    private String slugName;

    private String name;

    public TagDto(String slugName, String name) {
        this.slugName = slugName;
        this.name = name;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
