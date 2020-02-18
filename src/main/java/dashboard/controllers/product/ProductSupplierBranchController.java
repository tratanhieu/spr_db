package dashboard.controllers.product;

import dashboard.services.ProductSupplierBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product/supplierBranch")
public class ProductSupplierBranchController {

    @Autowired
    ProductSupplierBranchService productSupplierBranchService;


}
