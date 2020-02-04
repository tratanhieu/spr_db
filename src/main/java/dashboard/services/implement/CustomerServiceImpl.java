package dashboard.services.implement;

import dashboard.dto.user.customer.CustomerDto;
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
    public CustomerDto getCustomerInfo(Long userId) throws IOException {
        CustomerDto customerDto = customerMapper.getCustomerInfo(userId);
        Map<String, String> temp = new HashMap<>();


        return customerDto;
    }
}
