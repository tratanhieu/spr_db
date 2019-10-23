package dashboard.repositories;

import dashboard.enums.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dashboard.entities.ProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
	  
	@Query("SELECT pc FROM ProductCategory pc WHERE pc.status != 'DELETED'")
	Page<ProductCategory> findWithPageable(Pageable pageable);

	@Query("SELECT pc FROM ProductCategory pc " +
            "WHERE pc.status != 'DELETED' " +
            "AND pc.status = :status")
	Page<ProductCategory> findWithPageableAndFilterByStatus(
        Pageable pageable,
        @Param("status") EntityStatus status
    );

	@Query("SELECT pc FROM ProductCategory pc " +
            "WHERE pc.status != 'DELETED' " +
            "AND (:name = NULL OR pc.name LIKE %:name%) " +
            "AND (:status = NULL OR pc.status = :status)")
	Page<ProductCategory> findWithPageableAndSearch(
        Pageable pageable,
        @Param("name") String name,
        @Param("status") EntityStatus status
    );
	@Modifying
	@Transactional
	@Query(value = "UPDATE ProductCategory pc " +
            "SET pc.status = :status " +
            "WHERE pc.productCategoryId IN (:listId)")
	int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") EntityStatus status);
}
