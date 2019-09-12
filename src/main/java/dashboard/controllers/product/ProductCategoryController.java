package dashboard.controllers.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dashboard.entities.ProductCategory;
import dashboard.enums.EntityStatus;
import dashboard.enums.UserStatus;
import dashboard.services.ProductCategoryService;

@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@RequestMapping(
		value = "", 
		params = {"page", "size"},
		method = RequestMethod.GET
	)
    public ResponseEntity<Page<ProductCategory>> index(
    		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
    		@RequestParam(name = "size", required = false, defaultValue = "0") Integer size,
    		@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
	    Sort sortable = sort.equals("ASC") ? Sort.by("id").ascending() : Sort.by("id").descending();

		Pageable pageable = PageRequest.of(page, size, sortable);
        Page<ProductCategory> productCategories= productCategoryService.getAllWithPagination(pageable);
        
        return ResponseEntity.ok(productCategories);
    }
    
    @RequestMapping(value = "create/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> create(@PathVariable String name) {
    	ProductCategory productCategory = new ProductCategory();
    	productCategory.setName(name);
    	productCategory.setStatus(EntityStatus.ACTIVE);
    	return ResponseEntity.ok(EntityStatus.ACTIVE.getName());
//		productCategoryService.create(productCategory);
//		return ResponseEntity.ok("Done");
    }
}