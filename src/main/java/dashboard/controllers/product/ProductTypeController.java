package dashboard.controllers.product;

import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductTypeForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product/type")
public class ProductTypeController {

    @Autowired
    ProductTypeService productTypeService;

    @GetMapping
    public ResponseEntity index () {
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @GetMapping("{productTypeId}")
    public ResponseEntity getOne(@PathVariable(name = "productTypeId") Long productTypeId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeService.getOne(productTypeId));
    }

    @GetMapping("create")
    public ResponseEntity getCreate() throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeService.getCreate());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
        @RequestBody ProductTypeForm productTypeForm
    ) {
        ValidationUtils.validate(productTypeForm);
        productTypeService.create(productTypeForm);
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
        @RequestBody ProductTypeForm productTypeForm
    ) throws ResourceNotFoundException{
        ValidationUtils.validate(productTypeForm);
        productTypeService.update(productTypeForm);
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @DeleteMapping(value = "{productTypeId}")
    public ResponseEntity delete(
        @PathVariable(name = "productTypeId") Long productTypeId
    ) throws ResourceNotFoundException {
        productTypeService.delete(productTypeId);
        return ResponseEntity.ok(productTypeService.getAll());
    }
}