package dashboard.controllers.user;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.embedded.UserGroupFeaturesIdentity;
import dashboard.entities.user.UserFeatures;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeatures;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.UserGroupFeaturesRepository;
import dashboard.repositories.UserGroupRepository;
import dashboard.services.PusherService;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity index () {
        return ResponseEntity.ok(userGroupService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long userGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userGroupService.getOne(userGroupId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody UserGroup userGroupParams) {
        userGroupService.create(userGroupParams);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER_GROUP_FEATURES);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{userGroupId}/updateUserGroup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
            @PathVariable(name = "userGroupId") Long userGroupId,
            @RequestBody UserGroup userGroup
    ) throws ResourceNotFoundException {
        userGroup.setUserGroupId(userGroupId);
        userGroupService.update(userGroup);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER_GROUP_FEATURES);
        return HttpStatus.OK;
    }

    @GetMapping(value = "{userGroupId}/delete")
    public HttpStatus delete(@PathVariable(name = "userGroupId") Long userGroupId) throws ResourceNotFoundException {
        userGroupService.delete(userGroupId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER_GROUP_FEATURES);
        return HttpStatus.OK;
    }

}
