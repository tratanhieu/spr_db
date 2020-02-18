package dashboard.controllers.client;

import dashboard.commons.ActionUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.user.LoginForm;
import dashboard.dto.user.customer.*;
import dashboard.entities.user.CustomUserDetails;
import dashboard.entities.user.Customer;
import dashboard.entities.user.User;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
import dashboard.provider.JwtTokenProvider;
import dashboard.services.CustomerService;
import dashboard.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/customer")

@Transactional
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SmsService smsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("")
    public ResponseEntity getOne (HttpServletRequest request) throws IOException, ResourceNotFoundException {
        String token = tokenProvider.getJwtFromRequest(request);
        String phone = tokenProvider.getUserIdFromJWT(token);
        Long userId = customerService.getUserIdByPhone(phone);
        return ResponseEntity.ok(customerService.getCustomerInfo(userId));
    }

    @PatchMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(HttpServletRequest request, @RequestBody CustomerForm customerForm) throws ResourceNotFoundException, IOException {
        ValidationUtils.validate(customerForm);
        customerForm.setUserId(1L);
        customerService.updateCustomerInfo(customerForm);
        return getOne(request);
    }

    @PatchMapping(value = "/password/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus changePassword(HttpServletRequest request, @RequestBody PasswordForm passwordForm){
        Long userId = 4L;

        customerService.changePassword(passwordForm, userId);

        return HttpStatus.OK;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus registNew(@RequestBody RegisterForm registerForm) {
        String otpCode = ActionUtils.genOtp();

        if(customerService.registNewCustomer(registerForm) == 1) {
            Map<String, String> result = (Map) smsService.sendSms(registerForm.getPhone(), otpCode);
            if ("100".equals(result.get("CodeResult"))) {
                customerService.addOTP(registerForm.getPhone(), otpCode);
                return HttpStatus.OK;
            }
            throw new ValidationException(result);
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @PostMapping(value = "/register/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus verifyOTPRegist(@RequestBody CustomerOTPForm customerOTPForm) throws ResourceNotFoundException {

        CustomerOTPDto customerOTPDto = customerService.getOTP(customerOTPForm.getPhone());

        if (customerOTPDto.getOTP().equals(customerOTPForm.getOTP())) {
            customerService.completeRegistCustomer(customerOTPForm.getPhone());
            customerService.deleteOTP(customerOTPForm.getPhone());
            return HttpStatus.OK;
        }
        Map<String, String> error = new HashMap<>();
        error.put("Error", "OTP is not correct");
        throw new ValidationException(error);
    }

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody LoginForm loginForm) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginForm.getUserName(),
                loginForm.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(customUserDetails);
        Map<String, Object> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseEntity.ok(map);
    }

    @PatchMapping(value = "/password/forget", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus forgetPassword(@RequestBody ForgetPasswordForm forgetPasswordForm) throws ResourceNotFoundException {

        if(customerService.getCustomerPhoneByPhone(forgetPasswordForm.getPhone())) {
            String otp = ActionUtils.genOtp();
            Map<String, String> result = (Map) smsService.sendSms(forgetPasswordForm.getPhone(), otp);
            if ("100".equals(result.get("CodeResult"))) {
                customerService.addOTP(forgetPasswordForm.getPhone(), otp);
                return HttpStatus.OK;
            }
            throw new ValidationException(result);
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @PostMapping(value = "/password/verifyForget", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus verifyOTPForget(@RequestBody CustomerOTPForm customerOTPForm) throws ResourceNotFoundException {

        CustomerOTPDto customerOTPDto = customerService.getOTP(customerOTPForm.getPhone());

        if (customerOTPDto.getOTP().equals(customerOTPForm.getOTP())) {
            return HttpStatus.OK;
        }
        Map<String, String> error = new HashMap<>();
        error.put("Error", "OTP is not correct");
        throw new ValidationException(error);
    }

    @PatchMapping(value = "/password/forget/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus changePasswordForget(@RequestBody ChangeForgetPasswordForm changeForgetPasswordForm) throws ResourceNotFoundException {

        CustomerOTPDto customerOTPDto = customerService.getOTP(changeForgetPasswordForm.getPhone());

        if (customerOTPDto.getOTP().equals(changeForgetPasswordForm.getOtp())) {
            if (customerService.changePassword(changeForgetPasswordForm) == 1) {
                customerService.deleteOTP(changeForgetPasswordForm.getPhone());
                return HttpStatus.OK;
            }
        }

        Map<String, String> error = new HashMap<>();
        error.put("Error", "Unknown error");
        throw new ValidationException(error);
    }
}
