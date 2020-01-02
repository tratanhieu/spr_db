package dashboard.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;

import dashboard.entities.embedded.PostTagIdentity;
import dashboard.entities.tag.Tag;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table( name = "post_tag")
@EntityListeners(AuditingEntityListener.class)
public class PostTag implements Serializable {

    private static final Long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    PostTagIdentity postTagIdentity;


    public PostTagIdentity getPostTagIdentity() {
        return postTagIdentity;
    }

    @JsonIgnore
    public Tag getTag(){
        return postTagIdentity.getTag();
    }

    public void setPostTagIdentity(PostTagIdentity postTagIdentity) {
        this.postTagIdentity = postTagIdentity;
    }

    @JsonProperty("slugName")
    public String getTagSlugName(){
        return postTagIdentity.getTag().getSlugName();
    }

    @JsonProperty("name")
    public String getTagName(){
        return postTagIdentity.getTag().getName();
    }

}
