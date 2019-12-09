package dashboard.controllers.product;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
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
        return ResponseEntity.ok(productCategoryService.getAllWithPagination(pageable, search, status));
    }

    @GetMapping("{productCategoryId}")
    public ResponseEntity getOne(@PathVariable(name = "productCategoryId") Long productCategoryId)
            throws ResourceNotFoundException {
	    return ResponseEntity.ok(productCategoryService.getOne(productCategoryId));
    }

	@PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody ProductCategory productCategory) throws Exception{
        productCategoryService.create(productCategory);
	    pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PRODUCT_CATEGORY);
    	return HttpStatus.OK;
    }

	@PostMapping(value = "{productCategoryId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus update(
            @PathVariable(name = "productCategoryId") Long productCategoryId,
	        @RequestBody ProductCategory productCategoryParams
    ) throws ResourceNotFoundException {
	    ProductCategory productCategory = productCategoryService.getOne(productCategoryId);

	    if (productCategory.isEquals(productCategoryParams)) {
	        return HttpStatus.NOT_MODIFIED;
        }
	    // set params to update
        productCategory.setName(productCategoryParams.getName());
        productCategory.setSlugName(productCategoryParams.getSlugName());
        productCategory.setStatus(productCategoryParams.getStatus());
        productCategoryService.update(productCategory);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PRODUCT_CATEGORY);
        return HttpStatus.OK;
	}

	@GetMapping(value = "{productCategoryId}/delete")
	public HttpStatus delete(@PathVariable(name = "productCategoryId") Long productCategoryId) throws ResourceNotFoundException {
	    productCategoryService.delete(productCategoryId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PRODUCT_CATEGORY);
        return HttpStatus.OK;
	}

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        productCategoryService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PRODUCT_CATEGORY);

        return HttpStatus.OK;
    }
}