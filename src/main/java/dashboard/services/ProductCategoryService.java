package dashboard.services;

import dashboard.controllers.responses.base.ListEntityResponse;
import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import dashboard.entities.ProductCategory;

public interface ProductCategoryService {

    ListEntityResponse<ProductCategory> getAllWithPagination(Pageable pageable);

    ProductCategory getOne(Long productTypeId) throws ResourceNotFoundException;

	ListEntityResponse create(ProductCategory productCategory, Pageable pageable);

	ListEntityResponse update(ProductCategory productCategory, Pageable pageable);
}
