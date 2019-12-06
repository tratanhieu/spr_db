package dashboard.services.implement;

import dashboard.entities.product.ProductBrand;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
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

    @Override
    public ListEntityResponse<ProductBrand> getAllWithPagination(Pageable pageable, String search, EntityStatus status) {
        Page<ProductBrand> result = productBrandRepository.findWithPageableAndSearch(pageable, search, status);

        ListEntityResponse<ProductBrand> productBrandResponse = new ListEntityResponse<>();

        productBrandResponse.setPage(result.getNumber() + 1);
        productBrandResponse.setPageSize(result.getSize());
        productBrandResponse.setTotalPage(result.getTotalPages());
        productBrandResponse.setListData(result.getContent());

        return productBrandResponse;
    }

    @Override
    public ProductBrand getOne(Long productBrandId) throws ResourceNotFoundException {

        ProductBrand productBrand = productBrandRepository.findById(productBrandId).orElse(null);

        if (productBrand == null) {
            throw new ResourceNotFoundException();
        }

        return productBrand;
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
