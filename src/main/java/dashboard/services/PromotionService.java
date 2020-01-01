package dashboard.services;

import dashboard.entities.promotion.Promotion;
import dashboard.enums.PromotionStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {

    ListEntityResponse<Promotion> getAllWithPagination(Pageable pageable, String name, PromotionStatus status);

    Promotion getOne(Long promotionId) throws ResourceNotFoundException;

    void create(Promotion promotion);

    void update(Promotion promotion) throws ResourceNotFoundException;

    void delete(Long promotionId) throws  ResourceNotFoundException;

    int updateStatusWithMultipleId(List<Long> promotionId, PromotionStatus status) throws ResourceNotFoundException;

}
