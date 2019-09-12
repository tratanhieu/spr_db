package dashboard.services.implement;

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
	public Page<ProductCategory> getAllWithPagination(Pageable pageable) {
		
		return (Page<ProductCategory>) productCategoryRepository.findWithPageable(pageable);
	}
	
	@Override
	public boolean create(ProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
		return true;
	}
}
