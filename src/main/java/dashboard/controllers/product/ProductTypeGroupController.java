package dashboard.controllers.product;

import dashboard.constants.PusherConstants;
import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.entities.product.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.ProductTypeGroupService;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
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

        return ResponseEntity.ok(productTypeGroupService.getAll());
    }

    @GetMapping("{productTypeGroupId}")
    public ResponseEntity getOne(@PathVariable(name = "productTypeGroupId") Long productTypeGroupId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(productTypeGroupService.getOne(productTypeGroupId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ProductTypeGroupForm productTypeGroupForm) {
        // Save
        List response =  productTypeGroupService.create(productTypeGroupForm);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "{productTypeGroupId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @RequestBody ProductTypeGroupForm productTypeGroupForm
    ) {
        List response =  productTypeGroupService.update(productTypeGroupForm);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{productTypeGroupId}")
    public ResponseEntity delete(@PathVariable(name = "productTypeGroupId") Long productCategoryId) throws ResourceNotFoundException {
        List response = productTypeGroupService.delete(productCategoryId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, EntityStatus> multipleExecute) throws ResourceNotFoundException {
        productTypeGroupService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PRODUCT_CATEGORY);
        return HttpStatus.OK;
    }
}