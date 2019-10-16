package dashboard.services.implement;

import dashboard.entities.ProductCategory;
import dashboard.entities.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductCategoryRepository;
import dashboard.repositories.ProductTypeGroupRepository;
import dashboard.services.ProductCategoryService;
import dashboard.services.ProductTypeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductTypeGroupSerivceImpl implements ProductTypeGroupService {
	
	@Autowired
	ProductTypeGroupRepository productTypeGroupRepository;
	
	@Override
	public ListEntityResponse<ProductTypeGroup> getAllWithPagination(Pageable pageable) {
//		Page<ProductTypeGroup> result = productTypeGroupRepository.findWithPageable(pageable);
//
//		ListEntityResponse<ProductTypeGroup> productTypeGroupResponse = new ListEntityResponse<>();
//
//        productTypeGroupResponse.setPage(result.getNumber() + 1);
//        productTypeGroupResponse.setPageSize(result.getSize());
//        productTypeGroupResponse.setTotalPage(result.getTotalPages());
//        productTypeGroupResponse.setListData(result.getContent());
//
//		return productTypeGroupResponse;
        ListEntityResponse<ProductTypeGroup> list = new ListEntityResponse<>();
        list.setListData((List<ProductTypeGroup>) productTypeGroupRepository.findWithPageable(pageable));
        return list;
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
//        productTypeGroup.setDeleteDate(new Date());
        productTypeGroupRepository.save(productTypeGroup);
    }

    @Override
    public void updateStatusWithMultipleId(List<Long> listId, EntityStatus status) {
        int res = productTypeGroupRepository.updateStatusByListId(listId, status);
    }
}
