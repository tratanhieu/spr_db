package dashboard.controllers.user;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.user.UserFeatures;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.PusherService;
import dashboard.services.UserFeaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/features")
public class UserFeaturesController {

    @Autowired
    UserFeaturesService userFeaturesService;
    @Autowired
    PusherService pusherService;

    private static final String CHANNEL = "USER_FEATURES";

    @GetMapping("")
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(userFeaturesService.getAllWithPagination(pageable));
    }

    @PostMapping(value = "/createUserFeatures", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody UserFeatures userFeatures) {
        userFeaturesService.create(userFeatures);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER_FEATURES);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{userFeaturesId}/updateUserFeatures", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
            @PathVariable(name = "userFeaturesId") String userFeaturesId,
            @RequestBody UserFeatures userFeatures
    ) throws ResourceNotFoundException {
        userFeatures.setFeaturesId(userFeaturesId);
        userFeaturesService.update(userFeatures);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER_FEATURES);
        return HttpStatus.OK;
    }

    @GetMapping(value = "{userFeaturesId}/delete")
    public HttpStatus delete(@PathVariable(name = "userFeaturesId") String userFeaturesId) throws ResourceNotFoundException {
        userFeaturesService.delete(userFeaturesId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER_FEATURES);
        return HttpStatus.OK;
    }

}
