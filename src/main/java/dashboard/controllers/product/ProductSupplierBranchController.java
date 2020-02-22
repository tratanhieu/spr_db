package dashboard.controllers.product;

import dashboard.dto.product.ProductSupplierBranchForm;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductSupplierBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product/supplier/branch")
public class ProductSupplierBranchController {

    @Autowired
    ProductSupplierBranchService productSupplierBranchService;

    @GetMapping("")
    public ResponseEntity index(){
        return ResponseEntity.ok(productSupplierBranchService.getALL());
    }

    @GetMapping("{productSupplierBranchId}")
    public ResponseEntity getOne(
            @PathVariable(name = "productSupplierBranchId")  Long productSupplierBranchId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(productSupplierBranchService.getOne(productSupplierBranchId));
    }

    @GetMapping("create")
    public ResponseEntity geCreate(
            @PathVariable(name = "productSupplierBranchId")  Long productSupplierBranchId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(productSupplierBranchService.getCreate());
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
            @RequestBody ProductSupplierBranchForm productSupplierBranchForm
            ) throws ResourceNotFoundException {

        productSupplierBranchService.create(productSupplierBranchForm);

        return ResponseEntity.ok(productSupplierBranchService.getALL());
    }

    @PatchMapping (value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @RequestBody ProductSupplierBranchForm productSupplierBranchForm
    ) throws ResourceNotFoundException {

        productSupplierBranchService.update(productSupplierBranchForm);

        return ResponseEntity.ok(productSupplierBranchService.getALL());
    }

    @DeleteMapping(value = "{productSupplierBranchId}")
    public ResponseEntity delete(
            @PathVariable("productSupplierBranchId") Long productSupplierBranchId) throws ResourceNotFoundException {

        productSupplierBranchService.delete(productSupplierBranchId);
        return ResponseEntity.ok(productSupplierBranchService.getALL());
    }

}
