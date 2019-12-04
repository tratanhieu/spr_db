package dashboard.services;

import dashboard.entities.product.ProductType;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;


public interface ProductTypeService {

    ListEntityResponse<ProductType> getAllWithPagination(Pageable pageable);

    int create(ProductType productType);

}
