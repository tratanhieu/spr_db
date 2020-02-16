package dashboard.controllers.product;

import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductCategoryForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductCategoryService;
import dashboard.services.ProductTypeGroupService;
import dashboard.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product/category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductTypeGroupService productTypeGroupService;

    @Autowired
    ProductTypeService productTypeService;

	@GetMapping
    public ResponseEntity index () {
        return ResponseEntity.ok(productCategoryService.getAll());
    }

    @GetMapping("{productCategoryId}")
    public ResponseEntity getOne(
        @PathVariable(name = "productCategoryId") Long productCategoryId
    ) throws ResourceNotFoundException {
	    return ResponseEntity.ok(productCategoryService.getOne(productCategoryId));
    }

    @GetMapping("select/{productCategoryId}")
    public ResponseEntity selectProductTypeGroup(
        @PathVariable(name = "productCategoryId") Long productCategoryId
    ) {
        return ResponseEntity.ok(
            productTypeGroupService.getAllActiveByProductCategoryId(productCategoryId)
        );
    }

    @GetMapping("select/{productCategoryId}/{productTypeGroupId}")
    public ResponseEntity selectProductType(
        @PathVariable(name = "productCategoryId") Long productCategoryId,
        @PathVariable(name = "productTypeGroupId") Long productTypeGroupId
    ) {
        return ResponseEntity.ok(
            productTypeService.getAllActiveByCategoryAndGroupType(
                productCategoryId,
                productTypeGroupId
            )
        );
    }

    @GetMapping("create")
    public ResponseEntity getCreate() {
	    return ResponseEntity.ok(productCategoryService.getAllActives());
    }

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
        @RequestBody ProductCategoryForm productCategoryForm
    ) {
        ValidationUtils.validate(productCategoryForm);
        productCategoryService.create(productCategoryForm);
    	return ResponseEntity.ok(productCategoryService.getAll());
    }

	@PatchMapping (consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(
        @RequestBody ProductCategoryForm productCategoryForm
    ) {
        ValidationUtils.validate(productCategoryForm);
        productCategoryService.update(productCategoryForm);
        return ResponseEntity.ok(productCategoryService.getAll());
	}

	@DeleteMapping(value = "{productCategoryId}")
	public ResponseEntity delete(@PathVariable(name = "productCategoryId") Long productCategoryId) throws ResourceNotFoundException {
	    productCategoryService.delete(productCategoryId);
        return ResponseEntity.ok(productCategoryService.getAll());
	}
}