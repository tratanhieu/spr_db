package dashboard.controllers.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dashboard.controllers.responses.ProductCategoryResponse;
import dashboard.entities.ProductCategory;
import dashboard.enums.EntityStatus;
import dashboard.services.ProductCategoryService;

@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@GetMapping("")
    public ResponseEntity<ProductCategoryResponse> index(
    		@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
    		@RequestParam(name = "size", required = false, defaultValue = "2") Integer size,
    		@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
	    Sort sortable = sort.equals("ASC") ? Sort.by("id").ascending() : Sort.by("id").descending();
	    page = page <= 1 ? 0 : (page - 1);
		Pageable pageable = PageRequest.of(page, size, sortable);
		
        return ResponseEntity.ok(productCategoryService.getAllWithPagination(pageable));
    }
    
	@PostMapping("create")
    public ResponseEntity<String> create(
    		@RequestParam("name") String name,
    		@RequestParam("active") Boolean active
    ) {
    	ProductCategory productCategory = new ProductCategory();
    	productCategory.setName(name);
    	productCategory.setSlugName(name);
    	productCategory.setStatus(active ? EntityStatus.ACTIVE : EntityStatus.HIDDEN);

		boolean result = productCategoryService.create(productCategory);
		
    	return ResponseEntity.ok(String.valueOf(result));
    }
}