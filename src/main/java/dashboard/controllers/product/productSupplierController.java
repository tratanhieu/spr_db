package dashboard.controllers.product;

import dashboard.services.ProductSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/supplier")
public class productSupplierController {

    @Autowired
    ProductSupplierService productSupplierService;
}
