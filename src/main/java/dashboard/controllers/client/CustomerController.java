package dashboard.controllers.client;

import dashboard.commons.ValidationUtils;
import dashboard.dto.user.customer.*;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
import dashboard.services.CustomerService;
import dashboard.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/client/api/customer")

@Transactional
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SmsService smsService;

    @GetMapping("")
    public ResponseEntity getOne () throws IOException, ResourceNotFoundException {
        return ResponseEntity.ok(customerService.getCustomerInfo(1L));
    }

    @PatchMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody CustomerForm customerForm) throws ResourceNotFoundException, IOException {
        ValidationUtils.validate(customerForm);
        customerForm.setUserId(1L);
        customerService.updateCustomerInfo(customerForm);
        return getOne();
    }

    @PatchMapping(value = "/password/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus changePassword(@RequestBody PasswordForm passwordForm){
        Long userId = 4L;

        Map<String, String> errors = new HashMap<>();

        if(customerService.changePassword(passwordForm, userId) == 2){
            errors.put("confirmPassword", "Confirm password is not matched");
        }

        if(customerService.changePassword(passwordForm, userId) == 3){
            errors.put("newPassword", "New password must be different from old password");
        }

        if(customerService.changePassword(passwordForm, userId) == -1){
            errors.put("oldPassword", "Old password is not corrected");
        }

        if (errors.size() > 0) {
            throw new ValidationException(errors);
        }

        customerService.changePassword(passwordForm, userId);
        return HttpStatus.OK;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus registNew(@RequestBody RegisterForm registerForm) {
        String otpCode = "123456";

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

    @PostMapping(value = "verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus verifyOTP(@RequestBody CustomerOTPForm customerOTPForm) {

        if (1 == 1) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody CustomerLoginForm customerLoginForm) {
        // thay leader co code roi nen sau nay tham khao sau :))
        return ResponseEntity.ok(new String("OK"));
    }

    @PatchMapping(value = "/password/forget", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus forgetPassword(@RequestBody ForgetPasswordForm forgetPasswordForm) {
        // lam gi de suy nghi da
        return HttpStatus.OK;
    }
}
