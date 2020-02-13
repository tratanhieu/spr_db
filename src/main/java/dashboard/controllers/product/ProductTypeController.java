package dashboard.controllers.product;

import dashboard.commons.ValidationUtils;
import dashboard.constants.PusherConstants;
import dashboard.dto.product.ProductTypeForm;
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
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long productTypeId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeService.getOne(productTypeId));
    }

    @GetMapping("create")
    public ResponseEntity getCreate() throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeService.getCreate());
    }

    @PostMapping (value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ProductTypeForm productTypeForm) {

        ValidationUtils.validate(productTypeForm);
        productTypeService.create(productTypeForm);

        return ResponseEntity.ok(productTypeService.getAll());
    }

    @PatchMapping (value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody ProductTypeForm productTypeForm) throws ResourceNotFoundException{
        ValidationUtils.validate(productTypeForm);
        productTypeService.update(productTypeForm);
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long productTypeId) throws ResourceNotFoundException {

        productTypeService.delete(productTypeId);
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @PostMapping(value = "bulk-update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_BRAND,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return ResponseEntity.ok(productTypeService.updateStatusByListId(multipleExecute.getListId(), (EntityStatus) multipleExecute.getStatus()));
    }

}