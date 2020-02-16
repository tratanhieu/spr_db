package dashboard.controllers.product;

import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductTypeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product/type_group")
public class ProductTypeGroupController {

    @Autowired
    ProductTypeGroupService productTypeGroupService;

    @GetMapping
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {

        return ResponseEntity.ok(productTypeGroupService.getAll());
    }

    @GetMapping("{productTypeGroupId}")
    public ResponseEntity getOne(@PathVariable(name = "productTypeGroupId") Long productTypeGroupId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeGroupService.getOne(productTypeGroupId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
        @RequestBody ProductTypeGroupForm productTypeGroupForm
    ) {
        // Save
        List response =  productTypeGroupService.create(productTypeGroupForm);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
        @RequestBody ProductTypeGroupForm productTypeGroupForm
    ) {
        List response =  productTypeGroupService.update(productTypeGroupForm);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{productTypeGroupId}")
    public ResponseEntity delete(@PathVariable(name = "productTypeGroupId") Long productCategoryId) throws ResourceNotFoundException {
        List response = productTypeGroupService.delete(productCategoryId);
        return ResponseEntity.ok(response);
    }
}