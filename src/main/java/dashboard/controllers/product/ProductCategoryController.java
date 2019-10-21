package dashboard.controllers.product;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.generics.MultipleExecute;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    @Autowired
    PusherService pusherService;

    private static final String CHANNEL = "PRODUCT_CATEGORY";

	@GetMapping("")
    public ResponseEntity index (
    		@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
    		@RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "createDate,ASC") String sort,
            @RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "status", required = false) EntityStatus status
    ) {
//	    int str = filter.length;
        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(productCategoryService.getAllWithPagination(pageable, search, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long productCategoryId)
            throws ResourceNotFoundException {
	    return ResponseEntity.ok(productCategoryService.getOne(productCategoryId));
    }

	@PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody ProductCategory productCategory) {
        productCategoryService.create(productCategory);
	    pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_CREATE);
    	return HttpStatus.OK;
    }

	@PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus update(@RequestBody ProductCategory productCategory) {
        productCategoryService.update(productCategory);
	    pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_UPDATE);
		return HttpStatus.OK;
	}

	@GetMapping(value = "delete/{id}")
	public HttpStatus delete(@PathVariable(name = "id") Long productCategoryId) throws ResourceNotFoundException {
	    productCategoryService.delete(productCategoryId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_DELETE);
		return HttpStatus.OK;
	}

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        productCategoryService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
	    pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return HttpStatus.OK;
    }
}