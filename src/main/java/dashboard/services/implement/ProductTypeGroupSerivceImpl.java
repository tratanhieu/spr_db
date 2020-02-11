package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductTypeGroupDto;
import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.entities.product.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductTypeGroupRepository;
import dashboard.repositories.impl.ProductTypeGroupMapper;
import dashboard.services.ProductTypeGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class ProductTypeGroupSerivceImpl implements ProductTypeGroupService {
	
	@Autowired
	ProductTypeGroupRepository productTypeGroupRepository;

	@Autowired
    ProductTypeGroupMapper productTypeGroupMapper;

	@Override
	public List<ProductTypeGroupDto> getAll() {

		return productTypeGroupMapper.findAllActiveProductTypeGroup();
	}

    @Override
    public ProductTypeGroupDto getOne(Long productCategoryId) throws ResourceNotFoundException {
        return productTypeGroupMapper.findById(productCategoryId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
	public List create(ProductTypeGroupForm productTypeGroupForm) {

	    if (productTypeGroupForm.getProductTypeGroupId() != null) {
	        throw new ValidationException("Product type group id is not null");
        }

	    if (ValidationUtils.isBlank(productTypeGroupForm.getSlugName())) {

	        productTypeGroupForm.setSlugName(DataUtils.makeSlug(productTypeGroupForm.getName()));
        }

	    productTypeGroupMapper.save(productTypeGroupForm);

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

        productTypeGroupMapper.update(productTypeGroupForm);

        return getAll();
	}

    @Override
    public List delete(Long productCategoryId) throws ResourceNotFoundException {

	    productTypeGroupMapper.deleteById(productCategoryId);
	    return getAll();
    }

    @Override
    public void updateStatusWithMultipleId(List<Long> listId, EntityStatus status) {
        int res = productTypeGroupRepository.updateStatusByListId(listId, status);
    }
}
