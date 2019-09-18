package dashboard.services.implement;

import dashboard.controllers.responses.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dashboard.entities.ProductCategory;
import dashboard.repositories.ProductCategoryRepository;
import dashboard.services.ProductCategoryService;

@Service
public class ProductCategorySerivceImpl implements ProductCategoryService{
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
	@Override
	public BaseResponse<ProductCategory> getAllWithPagination(Pageable pageable) {
		Page<ProductCategory> result = productCategoryRepository.findWithPageable(pageable);

		BaseResponse<ProductCategory> productCategoryResponse = new BaseResponse<>(ProductCategory.class);

		productCategoryResponse.setPage(result.getNumber() + 1);
		productCategoryResponse.setPageSize(result.getSize());
		productCategoryResponse.setTotalPage(result.getTotalPages());
		productCategoryResponse.setListData(result.getContent());
		
		return productCategoryResponse;
	}
	
	@Override
	public BaseResponse<ProductCategory> create(ProductCategory productCategory, Pageable pageable) {
		productCategoryRepository.save(productCategory);
		return getAllWithPagination(pageable);
	}

	@Override
	public BaseResponse<ProductCategory> update(ProductCategory productCategory, Pageable pageable) {
		productCategoryRepository.save(productCategory);
		return getAllWithPagination(pageable);
	}
}
