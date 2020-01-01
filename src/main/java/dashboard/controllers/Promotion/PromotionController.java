package dashboard.controllers.Promotion;

import dashboard.commons.ActionUtils;
import dashboard.constants.PusherConstants;
import dashboard.entities.promotion.Promotion;
import dashboard.enums.PromotionStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.MultipleExecute;
import dashboard.services.PromotionService;
import dashboard.services.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @Autowired
    PusherService pusherService;

    @GetMapping("")
    public ResponseEntity index(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "createDate,DESC") String sort,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) PromotionStatus status
    ){
        Pageable pageable = (Pageable) ActionUtils.preparePageable(sort,page,size);
        return ResponseEntity.ok(promotionService.getAllWithPagination(pageable,search,status));
    }

    @GetMapping("{promotionId}")
    public ResponseEntity getOne(@PathVariable(name = "promotionId") long promotionId)
            throws ResourceNotFoundException {
             return ResponseEntity.ok(promotionService.getOne(promotionId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus create(@RequestBody Promotion promotion) throws Exception{
        promotionService.create(promotion);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PROMOTION);
        return HttpStatus.OK;
    }

    @PostMapping(value = "{promotionId}/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus update(
        @RequestBody Promotion promotionParam,
        @PathVariable(name = "promotionId") long promotionId
    ) throws ResourceNotFoundException{
        Promotion promotion = promotionService.getOne(promotionId);

        if (promotion.isEquals(promotionParam)) {
            return HttpStatus.NOT_MODIFIED;
        }

        promotion.setName(promotionParam.getName());
        promotion.setFromDate(promotionParam.getFromDate());
        promotion.setToDate(promotionParam.getToDate());
        promotion.setPercent(promotionParam.getPercent());
        promotion.setStatus(promotionParam.getStatus());
        promotion.setUpdateDate(new Date());
        promotionService.update(promotionParam);

        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PROMOTION);
        return HttpStatus.OK;
    }

    @GetMapping(value = "{productCategoryId}/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus delete(@PathVariable(name = "productCategoryId") Long productCategoryId) throws ResourceNotFoundException {
        promotionService.delete(productCategoryId);
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PROMOTION);
        return HttpStatus.OK;
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus execute(@RequestBody MultipleExecute<Long, PromotionStatus> multipleExecute) throws ResourceNotFoundException {
        promotionService.updateStatusWithMultipleId(multipleExecute.getListId(), multipleExecute.getStatus());
        pusherService.createAction(PusherConstants.PUSHER_CHANNEL_RELOAD_LIST,
                PusherConstants.PUSHER_DATA_PROMOTION);

        return HttpStatus.OK;
    }
}
