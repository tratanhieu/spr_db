package dashboard.controllers.user;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.user.Password;
import dashboard.entities.user.User;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.PusherService;
import dashboard.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PusherService pusherService;

    @GetMapping("")
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(userService.getAllWithPagination(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getOne(userId));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody User user) {
        String hashedPassWord = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassWord);

        userService.create(user);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{userId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
            @PathVariable(name = "userId") Long userId,
            @RequestBody User user
    ) throws ResourceNotFoundException{
        String hashedPassWord = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassWord);

        user.setUserId(userId);
        userService.update(user);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER);
        return HttpStatus.OK;
    }

    @GetMapping(value = "{userId}/delete")
    public HttpStatus delete(@PathVariable(name = "userId") Long userId) throws ResourceNotFoundException {
        userService.delete(userId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{userId}/update-profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus updateProfile(
            @PathVariable(name = "userId") Long userId,
            @RequestBody User user
    ) throws ResourceNotFoundException{
        user.setUserId(userId);
        userService.updateProfile(user);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_CHANNEL_USER);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus changePassword(
            @RequestBody Password password
            ) throws ResourceNotFoundException, NoSuchAlgorithmException {
        int result = userService.changePassword(password.getUserId(), password.getOldPassword(), password.getNewPassword());
        if(result == 1) {
            pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                    PusherConstants.PUSHER_CHANNEL_USER);
            return HttpStatus.OK;
        }
        return HttpStatus.NON_AUTHORITATIVE_INFORMATION;
    }
}
