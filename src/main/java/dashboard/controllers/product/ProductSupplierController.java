package dashboard.controllers.product;

import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/supplier")
public class ProductSupplierController {

    @Autowired
    ProductSupplierService productSupplierService;

    @GetMapping("")
    public ResponseEntity index(){
        return ResponseEntity.ok(productSupplierService.getAll());
    }

    @GetMapping("{productSupplierId}")
    public ResponseEntity getOne(
            @PathVariable(name = "productSupplierId")  Long productSupplierId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(productSupplierService.getOne(productSupplierId));
    }

}
