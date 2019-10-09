package dashboard.controllers.product;

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
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
	    Sort sortable = sort.equals("ASC") ? Sort.by("createDate").ascending() : Sort.by("createDate").descending();
	    page = 1 >= page ? 0 : (page - 1);
		Pageable pageable = PageRequest.of(page, size, sortable);
        return ResponseEntity.ok(productCategoryService.getAllWithPagination(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long productCategoryId) throws ResourceNotFoundException {
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
	public ResponseEntity update(@RequestBody ProductCategory productCategory) {
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_UPDATE);
		return ResponseEntity.ok(productCategoryService.update(productCategory));
	}

	@GetMapping(value = "delete/{id}")
	public HttpStatus delete(@PathVariable(name = "id") Long productCategoryId) throws ResourceNotFoundException {
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_DELETE);
		return HttpStatus.OK;
	}

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
	    pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return ResponseEntity.ok(productCategoryService.updateStatusWithMultipleId(multipleExecute.getListId(), (EntityStatus) multipleExecute.getStatus()));
    }
}