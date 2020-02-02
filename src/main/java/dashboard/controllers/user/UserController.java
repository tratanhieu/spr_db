package dashboard.controllers.user;

import dashboard.commons.ActionUtils;
import dashboard.commons.ValidationUtils;
import dashboard.constants.PusherConstants;
import dashboard.dto.user.UserDto;
import dashboard.dto.user.UserForm;
import dashboard.entities.user.CustomUserDetails;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.provider.JwtTokenProvider;
import dashboard.services.PusherService;
import dashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PusherService pusherService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody UserForm userForm) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userForm.getPhone(),
            userForm.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new HashMap<String, String>() {{ put("token", jwt); }});
    }

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getOne(userId));
    }

    @GetMapping("create")
    public ResponseEntity getCreate() {
        return ResponseEntity.ok(userService.getCreate());
    }

    @GetMapping("update/{userId}")
    public ResponseEntity getUpdate(
        @PathVariable(name = "userId") Long userId
    ) throws ResourceNotFoundException {
        Map<String, Object> map = userService.getCreate();
        map.put("user", userService.getOne(userId));
        return ResponseEntity.ok(map);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody UserForm userForm) {
        ValidationUtils.validate(userForm);
        userService.create(userForm);
        return index();
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody UserForm userForm) throws ResourceNotFoundException {
        UserDto userDto = userService.getOne(userForm.getUserId());
        if (!userDto.getUserGroupId().equals(userForm.getUserGroupId()) ||
                !userDto.getStatus().equals(userForm.getStatus())) {
            userService.update(userForm);
        }
        return index();
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity delete(@PathVariable(name = "userId") Long userId) {
        userService.delete(userId);
        return index();
    }
}
