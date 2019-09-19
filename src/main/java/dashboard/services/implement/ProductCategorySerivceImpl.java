package dashboard.services.implement;

import dashboard.controllers.response.ListEntityResponse;
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
	public ListEntityResponse<ProductCategory> create(ProductCategory productCategory, Pageable pageable) {
		productCategoryRepository.save(productCategory);
		return getAllWithPagination(pageable);
	}

	@Override
	public ListEntityResponse<ProductCategory> update(ProductCategory productCategory, Pageable pageable) {
		productCategoryRepository.save(productCategory);
		return getAllWithPagination(pageable);
	}

    @Override
    public ListEntityResponse delete(Long productCategoryId, Pageable pageable) throws ResourceNotFoundException {
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId).orElse(null);
        if (productCategory == null) {
            throw new ResourceNotFoundException();
        }
        productCategory.setStatus(EntityStatus.DELETED);
        productCategory.setDeleteDate(new Date());
	    productCategoryRepository.save(productCategory);

	    return getAllWithPagination(pageable);
    }

    @Override
    public ListEntityResponse updateStatusWithMultipleId(List<Long> listId, EntityStatus status, Pageable pageable) throws ResourceNotFoundException {
        int res = productCategoryRepository.updateStatusByListId(listId, status);
        return getAllWithPagination(pageable);
    }
}
