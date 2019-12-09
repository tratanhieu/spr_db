package dashboard.services.implement;

import dashboard.entities.product.ProductType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductTypeRepository;
import dashboard.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public ProductType getOne(Long productTypeId) throws ResourceNotFoundException {
        ProductType productType = productTypeRepository.findById(productTypeId).orElse(null);

        if(productType == null) {
            throw new ResourceNotFoundException();
        }

        return productType;
    }

    @Override
    public int create(ProductType productType) {
        productTypeRepository.save(productType);
        return 1;
    }

    @Override
    public int update(ProductType productType) throws ResourceNotFoundException {
        ProductType productTypeFind = productTypeRepository.findById(productType.getProductTypeId()).orElse(null);

        if (productTypeFind == null) {
            throw new ResourceNotFoundException();
        }

        productTypeRepository.save(productType);

        return 1;
    }

    @Override
    public int delete(Long productTypeId) throws ResourceNotFoundException {
        ProductType productType = productTypeRepository.findById(productTypeId).orElse(null);

        if (productType == null) {
            throw new ResourceNotFoundException();
        }

        productType.setStatus(EntityStatus.DELETED);
        productType.setDeleteDate(new Date());
        productTypeRepository.save(productType);

        return 1;
    }

    @Override
    public int updateStatusByListId(List<Long> listId, EntityStatus entityStatus) throws ResourceNotFoundException {
        int result = productTypeRepository.updateStatusByListId(entityStatus, listId);
        return result;
    }
}
