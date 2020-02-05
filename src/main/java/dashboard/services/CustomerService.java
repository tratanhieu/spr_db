package dashboard.services;

import dashboard.dto.user.customer.CustomerDto;
import dashboard.dto.user.customer.CustomerForm;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.io.IOException;

public interface CustomerService {

    CustomerDto getCustomerInfo(Long userId) throws IOException, ResourceNotFoundException;

    void updateCustomerInfo(CustomerForm customerForm) throws ResourceNotFoundException;
}
