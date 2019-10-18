package dashboard.services.implement;

import dashboard.generics.ListEntityResponse;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dashboard.entities.ProductCategory;
import dashboard.repositories.ProductCategoryRepository;
import dashboard.services.ProductCategoryService;

import java.util.Date;
import java.util.List;

@Service
public class ProductCategorySerivceImpl implements ProductCategoryService{
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
	@Override
	public ListEntityResponse<ProductCategory> getAllWithPagination(Pageable pageable) {
		Page<ProductCategory> result = productCategoryRepository.findWithPageable(pageable);

		ListEntityResponse<ProductCategory> productCategoryResponse = new ListEntityResponse<>();

		productCategoryResponse.setPage(result.getNumber() + 1);
		productCategoryResponse.setPageSize(result.getSize());
		productCategoryResponse.setTotalPage(result.getTotalPages());
		productCategoryResponse.setListData(result.getContent());
		
		return productCategoryResponse;
	}

    @Override
    public ProductCategory getOne(Long productCategoryId) throws ResourceNotFoundException {
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId).orElse(null);

        if (productCategory == null) {
            throw new ResourceNotFoundException();
        }

        return productCategory;
    }

    @Override
	public int create(ProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
		return 1;
	}

	@Override
	public int update(ProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
		return 1;
	}

    @Override
    public int delete(Long productCategoryId) throws ResourceNotFoundException {
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId).orElse(null);
        if (productCategory == null) {
            throw new ResourceNotFoundException();
        }
        productCategory.setStatus(EntityStatus.DELETED);
        productCategory.setDeleteDate(new Date());
	    productCategoryRepository.save(productCategory);

	    return 1;
    }

    @Override
    public int updateStatusWithMultipleId(List<Long> listId, EntityStatus status) throws ResourceNotFoundException {
        int res = productCategoryRepository.updateStatusByListId(listId, status);
        return res;
    }
}
