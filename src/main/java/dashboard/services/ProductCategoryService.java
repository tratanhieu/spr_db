package dashboard.services;

import dashboard.controllers.responses.base.BaseResponse;
import org.springframework.data.domain.Pageable;

import dashboard.entities.ProductCategory;

public interface ProductCategoryService {

	BaseResponse<ProductCategory> getAllWithPagination(Pageable pageable);

	BaseResponse create(ProductCategory productCategory, Pageable pageable);

	BaseResponse update(ProductCategory productCategory, Pageable pageable);
}
