package dashboard.controllers.product;

import dashboard.dto.product.ProductSupplierForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
            @RequestBody ProductSupplierForm productSupplierForm
            ) throws ResourceNotFoundException {

        productSupplierService.create(productSupplierForm);


        return ResponseEntity.ok(productSupplierService.getAll());
    }

    @PatchMapping (value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @RequestBody ProductSupplierForm productSupplierForm
    ) throws ResourceNotFoundException {

        productSupplierService.update(productSupplierForm);
        return ResponseEntity.ok(productSupplierService.getAll());
    }

    @DeleteMapping(value = "{productSupplierId}")
    public ResponseEntity delete(
        @PathVariable("productSupplierId") Long productSupplierId) throws ResourceNotFoundException {

        productSupplierService.delete(productSupplierId);
        return ResponseEntity.ok(productSupplierService.getAll());
    }
}