package dashboard.services.implement;

import dashboard.entities.product.ProductType;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductTypeRepository;
import dashboard.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Override
    public ListEntityResponse<ProductType> getAllWithPagination(Pageable pageable) {
        Page<ProductType> result = productTypeRepository.findWithPageable(pageable);

        ListEntityResponse<ProductType> productTypeResponse = new ListEntityResponse<>();

        productTypeResponse.setPage(result.getNumber() + 1);
        productTypeResponse.setPageSize(result.getSize());
        productTypeResponse.setTotalPage(result.getTotalPages());
        productTypeResponse.setListData(result.getContent());

        return productTypeResponse;
    }

    @Override
    public int create(ProductType productType) {
        productTypeRepository.save(productType);
        return 1;
    }
}
