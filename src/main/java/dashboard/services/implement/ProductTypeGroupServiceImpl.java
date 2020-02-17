package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductTypeGroupDto;
import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.ProductCategoryMapper;
import dashboard.repositories.ProductTypeGroupMapper;
import dashboard.services.ProductTypeGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class ProductTypeGroupServiceImpl implements ProductTypeGroupService {

	@Autowired
    ProductTypeGroupMapper productTypeGroupMapper;

	@Autowired
    ProductCategoryMapper productCategoryMapper;

	@Override
	public List getAll() {
		return productTypeGroupMapper.findAll();
	}

    @Override
    public List getAllActiveByProductCategoryId(Long productCategoryId) {
        return productTypeGroupMapper.findActiveByProductCategoryId(productCategoryId);
    }

    @Override
    public ProductTypeGroupDto getOne(Long productCategoryId) throws ResourceNotFoundException {
        return productTypeGroupMapper.findById(productCategoryId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List getCreate() throws ResourceNotFoundException {
	    return productCategoryMapper.findAllActiveProductCategory();
    }

    @Override
	public List create(ProductTypeGroupForm productTypeGroupForm) {

	    if (productTypeGroupForm.getProductTypeGroupId() != null) {
	        throw new ValidationException("Product type group id is not null");
        }

	    if (ValidationUtils.isBlank(productTypeGroupForm.getSlugName())) {
	        productTypeGroupForm.setSlugName(DataUtils.makeSlug(productTypeGroupForm.getName()));
        }

        if (productTypeGroupMapper.checkExistKey(productTypeGroupForm.getProductCategoryId()) == 1){
            productTypeGroupMapper.save(productTypeGroupForm);
        } else {
            throw new ValidationException("Product category id is not exists");
        }

	    return getAll();
	}

	@Override
	public List update(ProductTypeGroupForm productTypeGroupForm) {
        if (productTypeGroupForm.getProductTypeGroupId() == null) {
            throw new ValidationException("Product type group id is null");
        }

        if (ValidationUtils.isBlank(productTypeGroupForm.getSlugName())) {

            productTypeGroupForm.setSlugName(DataUtils.makeSlug(productTypeGroupForm.getName()));
        }

        if (productTypeGroupMapper.checkExistKey(productTypeGroupForm.getProductCategoryId()) == 1){
            productTypeGroupMapper.update(productTypeGroupForm);
        } else {
            throw new ValidationException("Product category id is not exists");
        }

        return getAll();
	}

    @Override
    public List delete(Long productCategoryId) {

	    productTypeGroupMapper.deleteById(productCategoryId);
	    return getAll();
    }
}
