package dashboard.controllers.post;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.PostTypeService;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post/type")
public class PostTypeController {

    @Autowired
    PostTypeService postTypeService;

    @Autowired
    PusherService pusherService;

    @GetMapping("")
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) EntityStatus status
    ) {
        Pageable pageable = ActionUtils.preparePageable(sort,page, size);
        return ResponseEntity.ok(postTypeService.getAll());
    }


    @GetMapping("/{postTypeId}")
    public ResponseEntity getOne(@PathVariable(name = "postTypeId") Long postTypeId) throws ResourceNotFoundException {
        return ResponseEntity.ok(postTypeService.getOne(postTypeId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody PostType postType) {
        postTypeService.create(postType);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST_TYPE,
                PusherConstants.PUSHER_ACTION_CREATE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{postTypeId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
            @RequestBody PostType postTypeParam,
            @PathVariable( name = "postTypeId") Long postTypeId
    ) throws ResourceNotFoundException{
        PostType postType = postTypeService.getOne(postTypeId);

        if (postType.isEquals(postTypeParam)) {
            return HttpStatus.NOT_MODIFIED;
        }
        postTypeService.update(postTypeParam);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST_TYPE,
                PusherConstants.PUSHER_ACTION_UPDATE);
        return HttpStatus.OK;
    }

    @GetMapping(value = "{postTypeId}/delete")
    public HttpStatus delete(@PathVariable(name = "postTypeId") Long postTypeId) throws ResourceNotFoundException {
        postTypeService.delete(postTypeId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST_TYPE,
                PusherConstants.PUSHER_ACTION_DELETE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        postTypeService.updateStatusWithMultipleId(multipleExecute.getListId(), (EntityStatus) multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST_TYPE,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return HttpStatus.OK;
    }
}