package dashboard.repositories;

import dashboard.entities.product.ProductType;
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
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {

    @Query("SELECT pt " +
            "FROM ProductType pt JOIN pt.productTypeGroup pc " +
            "WHERE pt.status != 'DELETED'")
    Page<ProductType> findWithPageable(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ProductType pt SET pt.status = :status WHERE pt.productTypeId in (:listId)")
    int updateStatusByListId(@Param("status") EntityStatus entityStatus, @Param("listId") List<Long> listId);
}
