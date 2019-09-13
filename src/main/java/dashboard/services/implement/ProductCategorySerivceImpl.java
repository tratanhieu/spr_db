package dashboard.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dashboard.controllers.responses.ProductCategoryResponse;
import dashboard.entities.ProductCategory;
import dashboard.repositories.ProductCategoryRepository;
import dashboard.services.ProductCategoryService;

@Service
public class ProductCategorySerivceImpl implements ProductCategoryService{
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
	@Override
	public ProductCategoryResponse getAllWithPagination(Pageable pageable) {
		Page<ProductCategory> result = (Page<ProductCategory>) productCategoryRepository.findWithPageable(pageable);
		
		ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();
		
		productCategoryResponse.setPage(result.getNumber() + 1);
		productCategoryResponse.setPageSize(result.getSize());
		productCategoryResponse.setTotalPage(result.getTotalPages());
		productCategoryResponse.setProductCategoryList(result.getContent());
		
		return productCategoryResponse;
	}
	
	@Override
	public boolean create(ProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
		return true;
	}
}
