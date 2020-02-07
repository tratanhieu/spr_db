package dashboard.controllers.client;

import dashboard.commons.ValidationUtils;
import dashboard.dto.user.customer.CustomerForm;
import dashboard.dto.user.customer.PasswordForm;
import dashboard.dto.user.customer.RegisterForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/client/api/customer")

@Transactional
public class CustomerController {

    @Autowired
    CustomerService customerService;

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
        Long userId = 1L;

        if(customerService.changePassword(passwordForm, userId) == 1){
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus registNew(@RequestBody RegisterForm registerForm) {
        if(customerService.registNewCustomer(registerForm) == 1) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_MODIFIED;
    }
}
