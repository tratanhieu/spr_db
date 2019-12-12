package dashboard.controllers.user;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.embedded.UserGroupFeaturesIdentity;
import dashboard.entities.user.UserFeatures;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeatures;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.UserFeaturesRepositiory;
import dashboard.repositories.UserGroupFeaturesRepository;
import dashboard.repositories.UserGroupRepository;
import dashboard.services.PusherService;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/group")
public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;
    @Autowired
    PusherService pusherService;
    @Autowired
    UserGroupRepository userGroupRepository;
    @Autowired
    UserFeaturesRepositiory userFeaturesRepositiory;
    @Autowired
    UserGroupFeaturesRepository userGroupFeaturesRepository;

    private static final String CHANNEL = "USER_GROUP_FEATURES";

    @GetMapping("")
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(userGroupService.getAllWithPagination(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long userGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userGroupService.getOne(userGroupId));
    }

    @PostMapping(value = "{featureId}/createUserGroup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@PathVariable(name = "featureId") String featureId, @RequestBody UserGroup userGroup) {
//        userFeaturesRepositiory.save(new UserFeatures("MANAGE_USER", "Manage User"));
//        userGroupRepository.save(new UserGroup(1L, "Seller", EntityStatus.ACTIVE));
//        UserGroupFeatures userGroupFeatures = new UserGroupFeatures();
//
//        UserGroupFeaturesIdentity userGroupFeaturesIdentity = new UserGroupFeaturesIdentity(userGroupRepository.findById(1L).orElse(null), new UserFeatures("MANAGE_USER"));
//
//        userGroupFeatures.setUserGroupFeaturesIdentity(userGroupFeaturesIdentity);
//        userGroupFeaturesRepository.save(userGroupFeatures);
        userGroupService.create(userGroup);

        UserGroupFeatures userGroupFeatures = new UserGroupFeatures();
        Long userGroupId = userGroupRepository.getInserted(userGroup.getName());
        userGroup.setUserGroupId(userGroupId);
        UserFeatures userFeatures = new UserFeatures(featureId);
        UserGroupFeaturesIdentity userGroupFeaturesIdentity = new UserGroupFeaturesIdentity(userGroup, userFeatures);
        userGroupFeatures.setUserGroupFeaturesIdentity(userGroupFeaturesIdentity);
        userGroupFeaturesRepository.save(userGroupFeatures);

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
