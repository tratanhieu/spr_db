package dashboard.controllers.product;

import dashboard.constants.PusherConstants;
import dashboard.entities.product.ProductBrand;
import dashboard.entities.product.ProductType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.ProductTypeService;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/type")
public class ProductTypeController {

    @Autowired
    ProductTypeService productTypeService;
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
        return ResponseEntity.ok(productTypeService.getAllWithPagination(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long productTypeId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeService.getOne(productTypeId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody ProductType productType) {
        productTypeService.create(productType);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_TYPE,
                PusherConstants.PUSHER_ACTION_CREATE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(@RequestBody ProductType productType) throws ResourceNotFoundException{
        productTypeService.update(productType);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_TYPE,
                PusherConstants.PUSHER_ACTION_UPDATE);
        return HttpStatus.OK;
    }

    @GetMapping(value = "delete/{id}")
    public HttpStatus delete(@PathVariable(name = "id") Long productTypeId) throws ResourceNotFoundException {
        productTypeService.delete(productTypeId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_TYPE,
                PusherConstants.PUSHER_ACTION_DELETE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "bulk-update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_BRAND,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return ResponseEntity.ok(productTypeService.updateStatusByListId(multipleExecute.getListId(), (EntityStatus) multipleExecute.getStatus()));
    }

}