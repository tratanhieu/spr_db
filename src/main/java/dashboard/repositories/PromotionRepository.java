package dashboard.repositories;


import dashboard.entities.promotion.Promotion;
import dashboard.enums.PromotionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Long>{

    @Query("SELECT pmt FROM Promotion pmt " +
            "WHERE pmt.status != 'STOP'" +
            "AND (:name = NULL OR pmt.name LIKE %:name%) " +
            "AND (:status = NULL OR pmt.status = :status)")
    Page<Promotion> findWithPageable(
            Pageable pageable,
            @Param("name") String name,
            @Param("status") PromotionStatus status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Promotion pmt SET pmt.status = :status WHERE pmt.promotionId IN (:listId)")
    int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") PromotionStatus status);
}