package dashboard.services;

import dashboard.dto.user.customer.CustomerDto;
import dashboard.dto.user.customer.CustomerForm;
import dashboard.dto.user.customer.PasswordForm;
import dashboard.dto.user.customer.RegisterForm;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.io.IOException;

public interface CustomerService {

    CustomerDto getCustomerInfo(Long userId) throws IOException, ResourceNotFoundException;

    void updateCustomerInfo(CustomerForm customerForm) throws ResourceNotFoundException;

    int changePassword(PasswordForm passwordForm, Long userId);

    int registNewCustomer(RegisterForm registerForm);

    void addOTP(String phone, String otpCode);

    int completeRegistCustomer(String phone);
}
