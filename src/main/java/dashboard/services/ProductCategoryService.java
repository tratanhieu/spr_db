package dashboard.services;

import dashboard.controllers.response.ListEntityResponse;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import dashboard.entities.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ListEntityResponse<ProductCategory> getAllWithPagination(Pageable pageable);

    ProductCategory getOne(Long productCategoryId) throws ResourceNotFoundException;

	ListEntityResponse create(ProductCategory productCategory, Pageable pageable);

    ListEntityResponse update(ProductCategory productCategory, Pageable pageable);

    ListEntityResponse delete(Long productCategoryId, Pageable pageable) throws ResourceNotFoundException;

    ListEntityResponse updateStatusWithMultipleId(List<Long> productCategoryListId, EntityStatus status, Pageable pageable) throws ResourceNotFoundException;
}
