package dashboard.dto.post;

import dashboard.dto.BaseEntityDto;

import java.math.BigInteger;
import java.util.*;

public class PostDto extends BaseEntityDto {
    private BigInteger postId;

    private String name;

    private String slugName;

    private String description;

    private String content;

    private List tags;

    private String image;

    private String postTypeName;

    private String author;

    private String status;

    public PostDto(
            BigInteger postId,
            String name,
            String slugName,
            String description,
            String content,
            String tags,
            String image,
            String postTypeName,
            String author,
            String status,
            Date createDate,
            Date updateDate,
            Date deleteDate
    ) {
        super(createDate, updateDate, deleteDate);
        List<Map> listMap = new ArrayList<>();
        if (tags != null) {
            String[] mapTags = tags.split(",");
            String[] tagArr;
            Map<String, String> map = new HashMap<>();
            for (String tag: mapTags) {
                tagArr = tag.split("#");
                if (tagArr.length == 2) {
                    map.put("slugName", tagArr[0]);
                    map.put("name", tagArr[1]);
                    listMap.add(map);
                }
            }
        }
        this.postId = postId;
        this.name = name;
        this.slugName = slugName;
        this.description = description;
        this.content = content;
        this.tags = listMap;
        this.image = image;
        this.postTypeName = postTypeName;
        this.author = author;
        this.status = status;
    }

    public BigInteger getPostId() {
        return postId;
    }

    public void setPostId(BigInteger postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostTypeName() {
        return postTypeName;
    }

    public void setPostTypeName(String postTypeName) {
        this.postTypeName = postTypeName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
