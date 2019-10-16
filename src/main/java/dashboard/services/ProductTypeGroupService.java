package dashboard.services;

import dashboard.entities.ProductCategory;
import dashboard.entities.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductTypeGroupService {

    ListEntityResponse<ProductTypeGroup> getAllWithPagination(Pageable pageable);

    ProductTypeGroup getOne(Long productTypeGroupId) throws ResourceNotFoundException;

    void create(ProductTypeGroup productTypeGroup);

    void update(ProductTypeGroup productTypeGroup);

    void delete(Long productTypeGroupId) throws ResourceNotFoundException;

    void updateStatusWithMultipleId(List<Long> productTypeGroupListId, EntityStatus status) throws ResourceNotFoundException;
}
