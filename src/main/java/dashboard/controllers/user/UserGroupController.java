package dashboard.controllers.user;

import dashboard.commons.ValidationUtils;
import dashboard.constants.FeatureConstants;
import dashboard.dto.user.UserGroupForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/group")
public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userGroupService.getAll());
    }

    @GetMapping("{userGroupId}")
    public ResponseEntity getOne(
        @PathVariable(name = "userGroupId") Long userGroupId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(userGroupService.getOne(userGroupId));
    }

    @GetMapping("create")
    public ResponseEntity getCreate() {
        return ResponseEntity.ok(FeatureConstants.MAP_FEATURE);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody UserGroupForm userGroupForm) {
        ValidationUtils.validate(userGroupForm);
        userGroupService.create(userGroupForm);
        return getAll();
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
        @RequestBody UserGroupForm userGroupForm
    ) throws ResourceNotFoundException {
        ValidationUtils.validate(userGroupForm);
        userGroupService.update(userGroupForm);
        return getAll();
    }

    @DeleteMapping(value = "{userGroupId}")
    public ResponseEntity delete(
        @PathVariable(name = "userGroupId") Long userGroupId
    ) {
        userGroupService.delete(userGroupId);
        return getAll();
    }

}
