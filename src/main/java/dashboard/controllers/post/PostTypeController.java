package dashboard.controllers.post;

import dashboard.commons.ActionUtils;
import dashboard.commons.ValidationUtils;
import dashboard.constants.PusherConstants;
import dashboard.dto.post.PostTypeForm;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.PostTypeService;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody PostTypeForm postTypeForm) {
        ValidationUtils.validate(postTypeForm);
        List response = postTypeService.create(postTypeForm);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @RequestBody PostTypeForm postTypeForm
    ) throws ResourceNotFoundException{
        ValidationUtils.validate(postTypeForm);
        List response = postTypeService.update(postTypeForm);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{postTypeId}")
    public ResponseEntity delete(@PathVariable(name = "postTypeId") Long postTypeId) {
        List response = postTypeService.delete(postTypeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) {
        postTypeService.updateStatusWithMultipleId(multipleExecute.getListId(), (EntityStatus) multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_POST_TYPE,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return HttpStatus.OK;
    }
}