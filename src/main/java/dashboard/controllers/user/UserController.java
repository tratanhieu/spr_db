package dashboard.controllers.user;

import dashboard.commons.ValidationUtils;
import dashboard.dto.user.LoginForm;
import dashboard.dto.user.UserDto;
import dashboard.dto.user.UserForm;
import dashboard.dto.user.UserPasswordForm;
import dashboard.entities.user.CustomUserDetails;
import dashboard.entities.user.User;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
import dashboard.provider.JwtTokenProvider;
import dashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody LoginForm loginForm) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            loginForm.getUserName(),
            loginForm.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        String jwt = tokenProvider.generateToken(customUserDetails);
        Map<String, Object> map = new HashMap<>();
        map.put("token", jwt);
        map.put("avatar", user.getAvatar());
        map.put("fullName", user.getFullName());
        return ResponseEntity.ok(map);
    }

//    @PostMapping("/logout")
//    public ResponseEntity logout() {
//        String jwt = tokenProvider.
//        return ResponseEntity.ok(new HashMap<String, String>() {{ put("token", jwt); }});
//    }

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("{userId}")
    public ResponseEntity getOne(@PathVariable(name = "userId") Long userId) throws ResourceNotFoundException {
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

    @GetMapping(value = "profile")
    public ResponseEntity getUpdateProfile(
            HttpServletRequest request
    ) throws ResourceNotFoundException, IOException {
        String token = tokenProvider.getJwtFromRequest(request);
        Long userId = tokenProvider.getUserIdFromJWT(token);
        Map map =  userService.getUserProfile(userId);
        return ResponseEntity.ok(map);
    }

    @PatchMapping(value = "profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProfile(
            @RequestBody UserForm userForm,
            HttpServletRequest request
    ) throws ResourceNotFoundException, IOException {
        String token = tokenProvider.getJwtFromRequest(request);
        Long userId = tokenProvider.getUserIdFromJWT(token);
        userForm.setUserId(userId);
        userService.updateProfile(userForm);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(value = "change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changePassword(
            @RequestBody UserPasswordForm userPasswordForm,
            HttpServletRequest request
    ) throws ResourceNotFoundException {
        String token = tokenProvider.getJwtFromRequest(request);
        Long userId = tokenProvider.getUserIdFromJWT(token);
        Map<String, String> errors = new HashMap<>();
        if (!userPasswordForm.getNewPassword().equals(userPasswordForm.getConfirmPassword())) {
            errors.put("confirmPassword", "Confirm Password not match");
        }
        if (userPasswordForm.getNewPassword().equals(userPasswordForm.getOldPassword())) {
            errors.put("newPassword", "New Password must different Old Password");
        }
        if (errors.size() > 0) {
            throw new ValidationException(errors);
        }
        userService.updatePassword(
            userId,
            userPasswordForm.getOldPassword(),
            userPasswordForm.getNewPassword()
        );
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "active")
    public ResponseEntity active(@PathVariable("token") String token) {
        if (token != null) {
            userService.active(1L, EntityStatus.ACTIVE);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return (ResponseEntity) ResponseEntity.badRequest();
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity delete(@PathVariable(name = "userId") Long userId) {
        userService.delete(userId);
        return index();
    }
}
