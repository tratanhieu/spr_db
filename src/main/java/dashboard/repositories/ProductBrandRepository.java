package dashboard.repositories;

import dashboard.enums.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dashboard.entities.product.ProductBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductBrandRepository extends CrudRepository<ProductBrand, Long> {

    //@Query("SELECT pd FROM ProductBrand pd WHERE pd.status != 'DELETED'")
    //Page<ProductBrand> findWithPageable(Pageable pageable);
    @Query("SELECT pb FROM ProductBrand pb " +
            "WHERE pb.status != 'DELETED' " +
            "AND (:name = NULL OR pb.name LIKE %:name%) " +
            "AND (:status = NULL OR pb.status = :status)")
    Page<ProductBrand> findWithPageableAndSearch(
            Pageable pageable,
            @Param("name") String name,
            @Param("status") EntityStatus status
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE ProductBrand pd SET pd.status = :status WHERE pd.productBrandId IN (:listId)")
    int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") EntityStatus status);
}
