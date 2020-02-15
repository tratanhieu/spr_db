package dashboard.controllers.client;

import dashboard.commons.ActionUtils;
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

        customerService.changePassword(passwordForm, userId);

        return HttpStatus.OK;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/verifyRegist", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody CustomerLoginForm customerLoginForm) {
        // THUC SU LA VAN CHUA HIEU CAI LOGIN NAY
        return ResponseEntity.ok(new String("OK"));
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

    @PostMapping(value = "/verifyForget", consumes = MediaType.APPLICATION_JSON_VALUE)
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
