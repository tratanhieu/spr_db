package dashboard.controllers.post;

import dashboard.commons.ActionUtils;
import dashboard.commons.ValidationUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.post.Post;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.PostService;
import dashboard.services.PusherService;
import dashboard.services.TagService;
import org.apache.coyote.http2.HPackHuffman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.Date;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    TagService tagService;
    @Autowired
    PusherService pusherService;

    @GetMapping("")
    public ResponseEntity index(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "createDate,ASC") String sort,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) EntityStatus status
    ) {
        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(postService.getAllWithPagination(pageable, search, status));
    }

    @GetMapping("{postId}")
    public ResponseEntity<Post> getOne(@PathVariable(name = "postId") Long postId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(postService.getOne(postId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(
            @RequestBody Post post
    ) {
        postService.create(post);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST,
                PusherConstants.PUSHER_ACTION_CREATE);
        return HttpStatus.OK;
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
        post.setTags(postParams.getTags());

        postService.update(post);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST,
                PusherConstants.PUSHER_ACTION_UPDATE);

        return HttpStatus.OK;
    }

    @GetMapping("{postId}/delete")
    public HttpStatus delete(
        @PathVariable(name = "postId") Long postId
    ) throws ResourceNotFoundException {

        tagService.deletePostTag(postId);
        postService.delete(postId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST,
                PusherConstants.PUSHER_ACTION_DELETE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        postService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return HttpStatus.OK;
    }


}
