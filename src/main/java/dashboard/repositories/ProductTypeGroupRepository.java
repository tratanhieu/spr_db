package dashboard.repositories;

import dashboard.entities.product.ProductTypeGroup;
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
	  
	@Query("SELECT pt " +
			"FROM ProductTypeGroup pt JOIN pt.productCategory pc " +
			"WHERE pt.status != 'DELETED'")
	Page<ProductTypeGroup> findWithPageable(Pageable pageable);

    @Modifying
	@Transactional
	@Query(value = "UPDATE ProductCategory pc SET pc.status = :status WHERE pc.productCategoryId IN (:listId)")
	int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") EntityStatus status);
}
