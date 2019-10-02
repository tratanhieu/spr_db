package dashboard.services;

import dashboard.generics.ListEntityResponse;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import dashboard.entities.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ListEntityResponse<ProductCategory> getAllWithPagination(Pageable pageable);

    ProductCategory getOne(Long productCategoryId) throws ResourceNotFoundException;

    int create(ProductCategory productCategory);

    int update(ProductCategory productCategory);

    int delete(Long productCategoryId) throws ResourceNotFoundException;

    int updateStatusWithMultipleId(List<Long> productCategoryListId, EntityStatus status) throws ResourceNotFoundException;
}
