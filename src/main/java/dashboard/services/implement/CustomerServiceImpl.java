package dashboard.services.implement;

import dashboard.dto.user.customer.*;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
import dashboard.repositories.CustomerMapper;
import dashboard.services.CustomerService;
import dashboard.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public CustomerDto getCustomerInfo(Long userId) throws IOException, ResourceNotFoundException {
        CustomerDto customerDto = customerMapper.getCustomerInfo(userId).orElseThrow(ResourceNotFoundException::new);
        Map<String, String> mapProvince = new HashMap<>();
        Map<String, String> mapDistrict = new HashMap<>();
        Map<String, String> mapWard = new HashMap<>();

        mapProvince.put(customerDto.getProvinceId(), provinceService.getProvince(customerDto.getProvinceId()));
        customerDto.setProvince(mapProvince);

        mapDistrict.put(customerDto.getDistrictId(), provinceService.getDistrict(customerDto.getProvinceId(), customerDto.getDistrictId()));
        customerDto.setDistrict(mapDistrict);

        mapWard.put(customerDto.getWardId(), provinceService.getWard(customerDto.getProvinceId(), customerDto.getDistrictId(), customerDto.getWardId()));;
        customerDto.setWard(mapWard);

        return customerDto;
    }

    @Override
    public void updateCustomerInfo(CustomerForm customerForm) throws ResourceNotFoundException {
        customerMapper.getCustomerInfo(customerForm.getUserId()).orElseThrow(ResourceNotFoundException::new);
        customerMapper.updateCustomerInfo(customerForm);
    }

    @Override
    public int changePassword(PasswordForm passwordForm, Long userId) {
        String oldPassword = customerMapper.getCustomerPassword(userId);

        Map<String, String> error = new HashMap<>();

        if(!passwordForm.getNewPassword().equals(passwordForm.getConfirmNewPassword())) {
            // pass moi khong khop
            error.put("confirmPassword", "Confirm password is not matched");
            throw new ValidationException(error);
        }

        if(passwordForm.getNewPassword().equals(passwordForm.getOldPassword())) {
            // pass moi trung pass cu
            error.put("newPassword", "New password must be different from old password");
            throw new ValidationException(error);
        }

        if(!passwordEncoder.matches(passwordForm.getOldPassword(), oldPassword)) {
            error.put("oldPassword", "Old password is not corrected");
            throw new ValidationException(error);
        }

        String newHashedPassword = passwordEncoder.encode(passwordForm.getNewPassword());
        customerMapper.changePassword(newHashedPassword, userId);
        // ok
        return 1;
    }

    @Override
    public int registNewCustomer(RegisterForm registerForm) {
        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            // pass ko trung
            return -1;
        }

        registerForm.setPassword(passwordEncoder.encode(registerForm.getPassword()));

        customerMapper.registNewCustomer(registerForm);
        return 1;
    }

    @Override
    public void addOTP(String phone, String otpCode) {
        customerMapper.addOTP(phone, otpCode);
    }

    @Override
    public CustomerOTPDto getOTP(String phone) throws ResourceNotFoundException {
        CustomerOTPDto customerOTPDto = customerMapper.getOTP(phone);

        if(customerOTPDto == null) {
            throw new ResourceNotFoundException();
        }

        return customerOTPDto;
    }

    @Override
    public int completeRegistCustomer(String phone) {
        customerMapper.completeRegistCustomer(phone);
        return 1;
    }

    @Override
    public void deleteOTP(String phone) {
        customerMapper.deleteOTP(phone);
    }

    @Override
    public boolean getCustomerPhoneByPhone(String phone) throws ResourceNotFoundException {
        String phoneNumber = customerMapper.getCustomerPhoneByPhone(phone);

        if(phoneNumber == null) {
            throw new ResourceNotFoundException();
        }

        return true;
    }

    @Override
    public int changePassword(ChangeForgetPasswordForm changeForgetPasswordForm) {

        if(!changeForgetPasswordForm.getPassword().equals(changeForgetPasswordForm.getConfirmPassword())) {
            // pass moi khong khop
            Map<String, String> error = new HashMap<>();
            error.put("confirmPassword", "Confirm password is not matched");
            throw new ValidationException(error);
        }

        String newHashedPassword = passwordEncoder.encode(changeForgetPasswordForm.getPassword());
        customerMapper.changePassword(newHashedPassword, changeForgetPasswordForm.getPhone());
        // ok
        return 1;
    }
}
