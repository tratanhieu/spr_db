package dashboard.services;

import dashboard.entities.ProductCategory;
import dashboard.generics.ListEntityResponse;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCategoryService {

    ListEntityResponse<ProductCategory> getAllWithPagination(Pageable pageable);

    ProductCategory getOne(Long productCategoryId) throws ResourceNotFoundException;

    void create(ProductCategory productCategory);

    void update(ProductCategory productCategory);

    void delete(Long productCategoryId) throws ResourceNotFoundException;

    void updateStatusWithMultipleId(List<Long> productCategoryListId, EntityStatus status) throws ResourceNotFoundException;
}