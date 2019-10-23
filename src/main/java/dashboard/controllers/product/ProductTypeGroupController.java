package dashboard.controllers.product;

import dashboard.constants.PusherConstants;
import dashboard.entities.ProductCategory;
import dashboard.entities.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.repositories.ProductTypeGroupRepository;
import dashboard.services.ProductTypeGroupService;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/type_group")
public class ProductTypeGroupController {

    @Autowired
    ProductTypeGroupService productTypeGroupService;
    @Autowired
    PusherService pusherService;

    private static final String CHANNEL = "PRODUCT_TYPE_GROUP";

    @GetMapping("")
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
    ) {
        Sort sortable = sort.equals("ASC") ? Sort.by("createDate").ascending() : Sort.by("createDate").descending();
        page = 1 >= page ? 0 : (page - 1);
        Pageable pageable = PageRequest.of(page, size, sortable);
        return ResponseEntity.ok(productTypeGroupService.getAllWithPagination(pageable));
    }

    @GetMapping("{productTypeGroupId}")
    public ResponseEntity getOne(@PathVariable(name = "productTypeGroupId") Long productTypeGroupId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeGroupService.getOne(productTypeGroupId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody ProductTypeGroup productTypeGroup) {
        // Save
        productTypeGroupService.create(productTypeGroup);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_CREATE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{productTypeGroupId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
            @PathVariable(name = "productTypeGroupId") Long productTypeGroupId,
            @RequestBody ProductTypeGroup productTypeGroup
    ) {
        productTypeGroup.setProductTypeGroupId(productTypeGroupId);
        productTypeGroupService.update(productTypeGroup);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_UPDATE);
        return HttpStatus.OK;
    }

    @GetMapping(value = "{productTypeGroupId}/delete")
    public HttpStatus delete(@PathVariable(name = "productTypeGroupId") Long productCategoryId) throws ResourceNotFoundException {
        productTypeGroupService.delete(productCategoryId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_DELETE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        productTypeGroupService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_CATEGORY,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return HttpStatus.OK;
    }
}