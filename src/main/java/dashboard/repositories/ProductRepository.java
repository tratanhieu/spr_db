package dashboard.repositories;

import dashboard.entities.product.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product p SET p.promotion.promotionId = :promotionId WHERE p.productId IN (:listProductId)")
    int updatePromotionByListId(@Param("listProductId") List<Long> listProductId,
                                @Param("promotionId") Long promotionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product p " +
            "SET p.promotion.promotionId = :promotionId")
    int updateAllPromotion(@Param("promotionId") Long promotionId);
}
