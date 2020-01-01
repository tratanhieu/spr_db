package dashboard.services.implement;

import dashboard.entities.product.Product;
import dashboard.entities.promotion.Promotion;
import dashboard.entities.promotion.PromotionCode;
import dashboard.enums.PromotionStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductRepository;
import dashboard.repositories.PromotionCodeRepository;
import dashboard.repositories.PromotionRepository;
import dashboard.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PromotionCodeRepository promotionCodeRepository;
    @Override
    public ListEntityResponse<Promotion> getAllWithPagination(Pageable pageable, String name, PromotionStatus status) {
        Page<Promotion> result = promotionRepository.findWithPageable(pageable, name, status);

        ListEntityResponse<Promotion> promotionResponse = new ListEntityResponse<>();
        promotionResponse.setPage(result.getNumber() + 1);
        promotionResponse.setPageSize(result.getSize());
        promotionResponse.setTotalPage(result.getTotalPages());
        promotionResponse.setListData(result.getContent());

        return promotionResponse;
    }

    @Override
    public Promotion getOne(Long promotionId) throws ResourceNotFoundException {
        Promotion promotion = promotionRepository.findById(promotionId).orElse(null);

        if (promotion == null) {
            throw new ResourceNotFoundException();
        }

        return  promotion;
    }

    @Override
    public void create(Promotion promotion) {
        promotionRepository.save(promotion);

//        if (promotion.getAllProduct()) {
//            int rs = productRepository.updateAllPromotion(promotion.getPromotionId());
//        } else {
//            List<Long> listProductId = Arrays.asList(promotion.getListProductId());
//            int rs = productRepository.updatePromotionByListId(promotion.getPromotionId(), listProductId);
//        }

        PromotionCode promotionCode;

        for(PromotionCode pc : promotion.getPromotionCodeArray()) {
            promotionCode = new PromotionCode();

            promotionCode.setPromotionCode(promotionCode.getPromotionCode());
            promotionCode.setPercent(pc.getPercent());
            promotionCode.setPromotion(promotion);
            promotionCode.setQuantity(pc.getQuantity());

            promotionCodeRepository.save(promotionCode);
        }


    }

    @Override
    public void update(Promotion promotion) throws ResourceNotFoundException {

    }

    @Override
    public void delete(Long promotionId) throws ResourceNotFoundException {

        Promotion promotion = promotionRepository.findById(promotionId).orElse(null);

        if (promotion == null) {
            throw new ResourceNotFoundException();
        }

        promotion.setDeleteDate(new Date());
        promotion.setStatus(PromotionStatus.STOP);
        promotionRepository.save(promotion);
    }

    @Override
    public int updateStatusWithMultipleId(List<Long> promotionId, PromotionStatus status) throws ResourceNotFoundException {
        return promotionRepository.updateStatusByListId(promotionId, status);
    }
}
