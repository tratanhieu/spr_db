package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductCategoryDto;
import dashboard.dto.product.ProductCategoryForm;

import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dashboard.entities.product.ProductCategory;
import dashboard.repositories.ProductCategoryRepository;
import dashboard.services.ProductCategoryService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@Service
public class ProductCategorySerivceImpl implements ProductCategoryService{
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;

	@Autowired
    ProductCategoryMapper productCategoryMapper;
	
	@Override
	public List<ProductCategoryDto> getAll() { return productCategoryMapper.findAllActiveProductCategory();
	}

    @Override
    public ProductCategoryDto getOne(Long productCategoryId) throws ResourceNotFoundException {
        return productCategoryMapper.findById(productCategoryId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
	public List create(@Valid ProductCategoryForm productCategoryForm) {

	    if (productCategoryForm.getProductCategoryId() != null ) {
            throw new ValidationException("product brand id not null");
        }

	    if (ValidationUtils.isBlank(productCategoryForm.getSlugName())) {
	        productCategoryForm.setSlugName(DataUtils.makeSlug(productCategoryForm.getName()));
        }

        ProductCategory productCategory = new ProductCategory(productCategoryForm);
	    productCategoryMapper.save(productCategory);

	    return getAll();
	}

	@Override
	public List  update(@Valid ProductCategoryForm productCategoryForm) {

	    if (productCategoryForm.getProductCategoryId() == null) {
	        throw new ValidationException("Product category id is null");
        }

	    if (ValidationUtils.isBlank(productCategoryForm.getSlugName())){
	        productCategoryForm.setSlugName(DataUtils.makeSlug(productCategoryForm.getName()));
        }

	    ProductCategory productCategory = new ProductCategory(productCategoryForm);
	    productCategoryMapper.update(productCategory);
	    return getAll();
	}

    @Override
    public List delete(Long productCategoryId) throws ResourceNotFoundException {
        productCategoryMapper.deleteById(productCategoryId);

        return getAll();
    }

    @Override
    public void updateStatusWithMultipleId(List<Long> listId, EntityStatus status) {
        int res = productCategoryRepository.updateStatusByListId(listId, status);
    }
}
