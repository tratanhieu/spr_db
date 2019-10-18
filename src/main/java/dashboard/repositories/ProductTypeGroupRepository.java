package dashboard.repositories;

import dashboard.entities.ProductCategory;
import dashboard.entities.ProductTypeGroup;
import dashboard.enums.EntityStatus;
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
public interface ProductTypeGroupRepository extends CrudRepository<ProductTypeGroup, Long> {

    @Query("SELECT ptg FROM ProductTypeGroup ptg WHERE ptg.status != 'DELETED'")
    Page<ProductTypeGroup> findWithPageable(Pageable pageable);

    @Query(value = "SELECT pgt FROM ProductTypeGroup pgt where pgt.status != 'DELETE' AND pgt.productCategoryId = :productCategory.productCategoryId ")
    Page<ProductTypeGroup> findByProductCategoryId(ProductCategory productCategory, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ProductTypeGroup ptg SET ptg.status = :status WHERE pc.productTypeGroup IN (:listId)")
    int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") EntityStatus status);

}
