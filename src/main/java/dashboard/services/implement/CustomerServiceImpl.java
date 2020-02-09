package dashboard.services.implement;

import dashboard.dto.user.customer.CustomerDto;
import dashboard.dto.user.customer.CustomerForm;
import dashboard.dto.user.customer.PasswordForm;
import dashboard.dto.user.customer.RegisterForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.CustomerMapper;
import dashboard.services.CustomerService;
import dashboard.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

        if(!passwordForm.getNewPassword().equals(passwordForm.getConfirmNewPassword())) {
            // pass moi khong khop
            return 2;
        }

        if(BCrypt.checkpw(passwordForm.getOldPassword(), oldPassword)) {
            String newHashedPassword = BCrypt.hashpw(passwordForm.getNewPassword(), BCrypt.gensalt());
            customerMapper.changePassword(newHashedPassword, userId);
            // ok
            return 1;
        }
        // pass cu sai
        return -1;
    }

    @Override
    public int registNewCustomer(RegisterForm registerForm) {
        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            // pass ko trung
            return -1;
        }

        customerMapper.registNewCustomer(registerForm);
        return 1;
    }

    @Override
    public int completeRegistCustomer(String phone) {
        customerMapper.completeRegistCustomer(phone);
        return 1;
    }
}
