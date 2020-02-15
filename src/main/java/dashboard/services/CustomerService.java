package dashboard.services;

import dashboard.dto.user.customer.*;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.io.IOException;

public interface CustomerService {

    CustomerDto getCustomerInfo(Long userId) throws IOException, ResourceNotFoundException;

    void updateCustomerInfo(CustomerForm customerForm) throws ResourceNotFoundException;

    int changePassword(PasswordForm passwordForm, Long userId);

    int registNewCustomer(RegisterForm registerForm);

    void addOTP(String phone, String otpCode);

    CustomerOTPDto getOTP(String phone) throws ResourceNotFoundException;

    int completeRegistCustomer(String phone);

    void deleteOTP(String phone);

    boolean getCustomerPhoneByPhone(String phone) throws ResourceNotFoundException;

    int changePassword(ChangeForgetPasswordForm changeForgetPasswordForm);


}
