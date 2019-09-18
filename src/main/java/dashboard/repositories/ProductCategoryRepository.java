package dashboard.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dashboard.entities.ProductCategory;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
	  
	@Query("SELECT productCategory FROM ProductCategory productCategory")
	Page<ProductCategory> findWithPageable(Pageable pageable);
	
}
