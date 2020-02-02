package dashboard.services.implement;

import dashboard.dto.product.ProductBrandDto;
import dashboard.entities.product.ProductBrand;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductBrandMapper;
import dashboard.repositories.ProductBrandRepository;
import dashboard.services.ProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ProductBrandSerivceImpl implements ProductBrandService {

    @Autowired
    ProductBrandRepository productBrandRepository;

    @Autowired
    ProductBrandMapper productBrandMapper;
    @Override
    public List<ProductBrandDto> getAllWithPagination() {
        return productBrandMapper.findAllActiveProductBrand();
    }

    @Override
    public ProductBrandDto getOne(Long productBrandId) throws ResourceNotFoundException {

        return productBrandMapper.findById(productBrandId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public int create(ProductBrand productBrand) {
        productBrandRepository.save(productBrand);
        return 1;
    }

    @Override
    public int update(ProductBrand productBrand) {
        productBrandRepository.save(productBrand);
        return 1;
    }

    @Override
    public int delete(Long productBrandId) throws ResourceNotFoundException {
        ProductBrand productBrand = productBrandRepository.findById(productBrandId).orElse(null);
        if (productBrand == null) {
            throw new ResourceNotFoundException();
        }
        productBrand.setStatus(EntityStatus.DELETED);
        productBrand.setDeleteDate(new Date());
        productBrandRepository.save(productBrand);

        return 1;
    }

    @Override
    public int updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) throws ResourceNotFoundException {
        int res = productBrandRepository.updateStatusByListId(ListId, status);
        return res;
    }
}
