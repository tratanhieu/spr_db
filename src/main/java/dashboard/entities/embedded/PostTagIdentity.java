package dashboard.entities.embedded;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dashboard.commons.DataUtils;
import dashboard.entities.post.Post;
import dashboard.entities.tag.Tag;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PostTagIdentity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "slug_name")
    private Tag tag;

    public PostTagIdentity(){}

    public PostTagIdentity(Post post, Tag tag){
        this.post = post;
        this.tag = tag;
    }

    public PostTagIdentity(Long postId, String tag){
        if (postId != null && tag != null) {
            this.post = new Post(postId);
            this.tag = new Tag(tag);
        }
    }

    public Post getPost() {
        return post;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTagIdentity that = (PostTagIdentity) o;
        return post.equals(that.post) &&
                tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, tag);
    }
}
