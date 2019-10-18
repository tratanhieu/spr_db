package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.entities.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductTypeGroupRepository;
import dashboard.services.ProductTypeGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductTypeGroupSerivceImpl implements ProductTypeGroupService {
	
	@Autowired
	ProductTypeGroupRepository productTypeGroupRepository;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//    @Autowired
//    Validator validator;

	@Override
	public ListEntityResponse<ProductTypeGroup> getAllWithPagination(Pageable pageable) {
		Page<ProductTypeGroup> result = productTypeGroupRepository.findWithPageable(pageable);

		ListEntityResponse<ProductTypeGroup> productTypeGroupResponse = new ListEntityResponse<>();

        productTypeGroupResponse.setPage(result.getNumber() + 1);
        productTypeGroupResponse.setPageSize(result.getSize());
        productTypeGroupResponse.setTotalPage(result.getTotalPages());
        productTypeGroupResponse.setListData(result.getContent());

		return productTypeGroupResponse;
	}

    @Override
    public ProductTypeGroup getOne(Long productCategoryId) throws ResourceNotFoundException {
        ProductTypeGroup productTypeGroup = productTypeGroupRepository.findById(productCategoryId).orElse(null);

        if (productTypeGroup == null) {
            throw new ResourceNotFoundException();
        }

        return productTypeGroup;
    }

    @Override
	public void create(ProductTypeGroup productTypeGroup) {
        Set<ConstraintViolation<ProductTypeGroup>> violations = validator.validate(productTypeGroup);
        if (violations.size()  > 0) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
	    if (StringUtils.isEmpty(productTypeGroup.getSlugName())) {
            productTypeGroup.setSlugName(DataUtils.makeSlug(productTypeGroup.getName()));
        }
	    productTypeGroupRepository.save(productTypeGroup);
	}

	@Override
	public void update(ProductTypeGroup productTypeGroup) {
	    productTypeGroupRepository.save(productTypeGroup);
	}

    @Override
    public void delete(Long productCategoryId) throws ResourceNotFoundException {
        ProductTypeGroup productTypeGroup = productTypeGroupRepository.findById(productCategoryId).orElse(null);
        if (productTypeGroup == null) {
            throw new ResourceNotFoundException();
        }
        productTypeGroup.setStatus(EntityStatus.DELETED);
        productTypeGroup.setDeleteDate(DataUtils.getSystemDate());
        productTypeGroupRepository.save(productTypeGroup);
    }

    @Override
    public void updateStatusWithMultipleId(List<Long> listId, EntityStatus status) {
        int res = productTypeGroupRepository.updateStatusByListId(listId, status);
    }
}
