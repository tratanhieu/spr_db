package dashboard.controllers.product;

import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductBrandForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product/brand")
public class ProductBrandController {

    @Autowired
    ProductBrandService productBrandService;

    @GetMapping
    public ResponseEntity index () {
        return ResponseEntity.ok(productBrandService.getAll());
    }

    @GetMapping("{productBrandId}")
    public ResponseEntity getOne(
        @PathVariable(name = "productBrandId") Long productBrandId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(productBrandService.getOne(productBrandId));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
        @RequestBody ProductBrandForm productBrandForm
    ) {
        ValidationUtils.validate(productBrandForm);
        productBrandService.create(productBrandForm);
        return ResponseEntity.ok(productBrandService.getAll());
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
        @RequestBody ProductBrandForm productBrandForm
    ) throws ResourceNotFoundException {
        ValidationUtils.validate(productBrandForm);
        productBrandService.update(productBrandForm);
        return ResponseEntity.ok(productBrandService.getAll());
    }

    @DeleteMapping(value = "{productBrandId}")
    public ResponseEntity delete(
        @PathVariable(name = "productBrandId") Long productBrandId
    ){
        productBrandService.delete(productBrandId);
        return ResponseEntity.ok(productBrandService.getAll());
    }
}
