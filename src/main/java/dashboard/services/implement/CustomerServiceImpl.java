package dashboard.services.implement;

import dashboard.dto.user.customer.CustomerDto;
import dashboard.dto.user.customer.CustomerForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.CustomerMapper;
import dashboard.services.CustomerService;
import dashboard.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
