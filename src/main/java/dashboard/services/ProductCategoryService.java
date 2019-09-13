package dashboard.services;

import org.springframework.data.domain.Pageable;

import dashboard.controllers.responses.ProductCategoryResponse;
import dashboard.entities.ProductCategory;

public interface ProductCategoryService {
	
	ProductCategoryResponse getAllWithPagination(Pageable pageable);	
	
	boolean create(ProductCategory product);
}
