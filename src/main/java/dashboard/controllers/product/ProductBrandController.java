package dashboard.controllers.product;

import dashboard.commons.ActionUtils;
import dashboard.commons.ValidationUtils;
import dashboard.constants.PusherConstants;
import dashboard.dto.product.ProductBrandForm;
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

import javax.validation.Valid;
import java.util.List;

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
        return ResponseEntity.ok(productBrandService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable(name = "id") Long productBrandId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productBrandService.getOne(productBrandId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ProductBrandForm productBrandForm) {

        ValidationUtils.validate(productBrandForm);
         productBrandService.create(productBrandForm);

        return ResponseEntity.ok(productBrandService.getAll());
    }

    @PatchMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @RequestBody ProductBrandForm productBrandForm
    ) throws ResourceNotFoundException {
        ValidationUtils.validate(productBrandForm);
        productBrandService.update(productBrandForm);
        return ResponseEntity.ok(productBrandService.getAll());
    }

    @DeleteMapping(value = "{productBrandId}")
    public ResponseEntity delete(@PathVariable(name = "productBrandId") Long productBrandId){
        productBrandService.delete(productBrandId);

        return ResponseEntity.ok(productBrandService.getAll());
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_PRODUCT_BRAND,
                PusherConstants.PUSHER_ACTION_UPDATE_STATUS_MULTIPLE);
        return ResponseEntity.ok(productBrandService.updateStatusWithMultipleId(multipleExecute.getListId(), (EntityStatus) multipleExecute.getStatus()));
    }
}
