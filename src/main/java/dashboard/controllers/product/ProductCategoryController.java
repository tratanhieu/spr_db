package dashboard.controllers.product;

import dashboard.commons.ActionUtils;
import dashboard.commons.ValidationUtils;
import dashboard.constants.PusherConstants;
import dashboard.dto.product.ProductCategoryDto;
import dashboard.dto.product.ProductCategoryForm;
import dashboard.generics.MultipleExecute;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dashboard.entities.product.ProductCategory;
import dashboard.services.ProductCategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    PusherService pusherService;

	@GetMapping("")
    public ResponseEntity index (
    		@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
    		@RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "createDate,DESC") String sort,
            @RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "status", required = false) EntityStatus status
    ) {
        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(productCategoryService.getAll());
    }

    @GetMapping("{productCategoryId}")
    public ResponseEntity getOne(@PathVariable(name = "productCategoryId") Long productCategoryId)
            throws ResourceNotFoundException {
	    return ResponseEntity.ok(productCategoryService.getOne(productCategoryId));
    }

    @GetMapping("create")
    public ResponseEntity getCreate() throws ResourceNotFoundException {

	    return  ResponseEntity.ok(productCategoryService.getAll());
    }

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ProductCategoryForm productCategoryForm) {

        ValidationUtils.validate(productCategoryForm);
         List<ProductCategoryDto> response =  productCategoryService.create(productCategoryForm);

    	return ResponseEntity.ok(response) ;
    }

	@PatchMapping (value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(
	        @RequestBody ProductCategoryForm productCategoryForm
    ) throws ResourceNotFoundException {

        ValidationUtils.validate(productCategoryForm);
        List<ProductCategoryDto> response = productCategoryService.update(productCategoryForm);
        return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "{productCategoryId}")
	public ResponseEntity delete(@PathVariable(name = "productCategoryId") Long productCategoryId) throws ResourceNotFoundException {
	     List<ProductCategoryDto> response = productCategoryService.delete(productCategoryId);
        return ResponseEntity.ok(response);
	}

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        productCategoryService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PRODUCT_CATEGORY);

        return HttpStatus.OK;
    }
}