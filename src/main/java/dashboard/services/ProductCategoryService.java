package dashboard.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dashboard.entities.ProductCategory;

public interface ProductCategoryService {
	
	Page<ProductCategory> getAllWithPagination(Pageable pageable);	
	
	boolean create(ProductCategory product);
}
