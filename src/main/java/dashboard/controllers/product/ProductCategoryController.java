package dashboard.controllers.product;

import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dashboard.entities.ProductCategory;
import dashboard.services.ProductCategoryService;

@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@GetMapping("")
    public ResponseEntity index (
    		@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
    		@RequestParam(name = "limit", required = false, defaultValue = "2") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
	    Sort sortable = sort.equals("ASC") ? Sort.by("createDate").ascending() : Sort.by("createDate").descending();
	    page = page <= 1 ? 0 : (page - 1);
		Pageable pageable = PageRequest.of(page, size, sortable);
		
        return ResponseEntity.ok(productCategoryService.getAllWithPagination(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne (@PathVariable(name = "id") Long productTypeId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productCategoryService.getOne(productTypeId));
    }

	@PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ProductCategory productCategory) {
		Pageable pageable = PageRequest.of(0, 2, Sort.by("createDate").descending());
    	return ResponseEntity.ok(productCategoryService.create(productCategory, pageable));
    }

	@PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@RequestBody ProductCategory productCategory) {
		Pageable pageable = PageRequest.of(0, 2, Sort.by("createDate").descending());

		return ResponseEntity.ok(productCategoryService.create(productCategory, pageable));
	}
}