package dashboard.services;

import dashboard.entities.ProductBrand;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductBrandService {

    ListEntityResponse<ProductBrand> getAllWithPagination(Pageable pageable);

    ProductBrand getOne(Long productBrandId) throws ResourceNotFoundException;

    int create(ProductBrand productBrand);

    int update(ProductBrand productBrand);

    int delete(Long productBrandId) throws ResourceNotFoundException;

    int updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) throws ResourceNotFoundException;
}
