package dashboard.controllers.user;

import dashboard.commons.ActionUtils;
import dashboard.entities.embedded.UserGroupFeaturesIdentity;
import dashboard.entities.user.UserFeatures;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeatures;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.UserFeaturesRepositiory;
import dashboard.repositories.UserGroupFeaturesRepository;
import dashboard.repositories.UserGroupRepository;
import dashboard.services.PusherService;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
//        userFeaturesRepositiory.save(new UserFeatures("MANAGE_USER", "Manage User"));
//        userGroupRepository.save(new UserGroup(1L, "Seller", EntityStatus.ACTIVE));
//        UserGroupFeatures userGroupFeatures = new UserGroupFeatures();
//
//        UserGroupFeaturesIdentity userGroupFeaturesIdentity = new UserGroupFeaturesIdentity(userGroupRepository.findById(1L).orElse(null), new UserFeatures("MANAGE_USER"));
//
//        userGroupFeatures.setUserGroupFeaturesIdentity(userGroupFeaturesIdentity);
//        userGroupFeaturesRepository.save(userGroupFeatures);
        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(userGroupService.getAllWithPagination(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long userProductId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userGroupService.getOne(userProductId));
    }
}
