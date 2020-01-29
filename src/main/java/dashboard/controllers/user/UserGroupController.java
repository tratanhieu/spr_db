package dashboard.controllers.user;

import dashboard.commons.ValidationUtils;
import dashboard.constants.PusherConstants;
import dashboard.dto.user.UserGroupForm;
import dashboard.entities.user.UserGroup;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.UserGroupFeaturesRepository;
import dashboard.repositories.UserGroupRepository;
import dashboard.services.PusherService;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/group")

@Transactional
public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;
    @Autowired
    PusherService pusherService;
    @Autowired
    UserGroupRepository userGroupRepository;
    @Autowired
    UserGroupFeaturesRepository userGroupFeaturesRepository;

    private static final String CHANNEL = "USER_GROUP_FEATURES";

    @GetMapping("")
    public ResponseEntity getAll () {
        return ResponseEntity.ok(userGroupService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long userGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userGroupService.getOne(userGroupId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody UserGroupForm userGroupForm) {
        ValidationUtils.validate(userGroupForm);
        userGroupService.create(userGroupForm);
        return getAll();
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody UserGroupForm userGroupForm) throws ResourceNotFoundException {
        ValidationUtils.validate(userGroupForm);
        userGroupService.update(userGroupForm);
        return getAll();
    }

    @GetMapping(value = "{userGroupId}/delete")
    public HttpStatus delete(@PathVariable(name = "userGroupId") Long userGroupId) throws ResourceNotFoundException {
        userGroupService.delete(userGroupId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER_GROUP_FEATURES);
        return HttpStatus.OK;
    }

}
