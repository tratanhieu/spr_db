package dashboard.controllers.product;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.product.ProductBrand;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.ProductBrandService;
import dashboard.services.PusherService;
import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/brand")
public class ProductBrandController {

    @Autowired
    ProductBrandService productBrandService;

    @Autowired
    PusherService pusherService;

    @GetMapping("")
    public ResponseEntity index (
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) EntityStatus status
            ) {

        Pageable pageable = ActionUtils.preparePageable(sort, page, size);
        return ResponseEntity.ok(productBrandService.getAllWithPagination());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long productBrandId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productBrandService.getOne(productBrandId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody ProductBrand productBrand) {
        productBrandService.create(productBrand);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_BRAND,
                PusherConstants.PUSHER_ACTION_CREATE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{productBrandId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
            @PathVariable Long productBrandId,
            @RequestBody ProductBrand productBrandParam
    ) throws ResourceNotFoundException {

        return HttpStatus.OK;
    }

    @GetMapping(value = "{productBrandId}/delete")
    public HttpStatus delete(@PathVariable(name = "productBrandId") Long productBrandId) throws ResourceNotFoundException {
        productBrandService.delete(productBrandId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_BRAND,
                PusherConstants.PUSHER_ACTION_DELETE);
        return HttpStatus.OK;
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_BRAND,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return ResponseEntity.ok(productBrandService.updateStatusWithMultipleId(multipleExecute.getListId(), (EntityStatus) multipleExecute.getStatus()));
    }
}
