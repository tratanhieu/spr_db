package dashboard.controllers.post;

import dashboard.commons.ValidationUtils;
import dashboard.dto.post.PostForm;
import dashboard.entities.post.Post;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.PostService;
import dashboard.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    TagService tagService;

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("{postId}")
    public ResponseEntity<Post> getOne(
        @PathVariable(name = "postId") Long postId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(postService.getOne(postId));
    }

    @GetMapping("create")
    public ResponseEntity getCreate() {
        return ResponseEntity.ok(postService.getCreate());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
        @RequestBody PostForm postForm
    ) {
        ValidationUtils.validate(postForm);
        List response = postService.create(postForm);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "{postId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
        @PathVariable(name = "postId") Long postId,
        @RequestBody Post postParams
    ) throws ResourceNotFoundException {

        Post post = postService.getOne(postId);

        if (post.equals(postParams)) {
            return HttpStatus.NOT_MODIFIED;
        }

        post.setName(postParams.getName());
        post.setSlugName(postParams.getSlugName());
        post.setImage(postParams.getImage());
        post.setContent(postParams.getContent());
        post.setStatus(postParams.getStatus());
        post.setUpdateDate(new Date());
//        post.setTags(postParams.getTags());
//        postService.update(post);
        return HttpStatus.OK;
    }

    @GetMapping("{postId}/delete")
    public HttpStatus delete(
        @PathVariable(name = "postId") Long postId
    ) throws ResourceNotFoundException {

        tagService.deletePostTag(postId);
        postService.delete(postId);
        return HttpStatus.OK;
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        postService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
        return HttpStatus.OK;
    }


}
