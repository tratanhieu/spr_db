package dashboard.services;

import dashboard.dto.user.customer.CustomerDto;

import java.io.IOException;

public interface CustomerService {

    CustomerDto getCustomerInfo(Long userId) throws IOException;
}
