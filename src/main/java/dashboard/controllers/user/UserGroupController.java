package dashboard.controllers.user;

import dashboard.exceptions.customs.ResourceNotFoundException;
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

    private static final String CHANNEL = "USER_GROUP_FEATURES";

    @GetMapping("")
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
        Sort sortable = sort.equals("ASC") ? Sort.by("createDate").ascending() : Sort.by("createDate").descending();
        page = 1 >= page ? 0 : (page - 1);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userGroupService.getAllWithPagination(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long userProductId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userGroupService.getOne(userProductId));
    }
}
