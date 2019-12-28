package dashboard.services;

import dashboard.entities.product.ProductType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductTypeService {

    ListEntityResponse<ProductType> getAllWithPagination(Pageable pageable);

    ProductType getOne(Long productTypeId) throws ResourceNotFoundException;

    int create(ProductType productType);

    int update(ProductType productType) throws ResourceNotFoundException;

    int delete(Long productTypeId) throws ResourceNotFoundException;

    int updateStatusByListId(List<Long> listId, EntityStatus entityStatus) throws ResourceNotFoundException;

}
