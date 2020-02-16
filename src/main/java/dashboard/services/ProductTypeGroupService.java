package dashboard.services;

import dashboard.dto.product.ProductTypeGroupDto;
import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.entities.product.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductTypeGroupService {

    List getAll();

    List getAllActiveByProductCategoryId(Long productCategoryId);

    ProductTypeGroupDto getOne(Long productTypeGroupId) throws ResourceNotFoundException;

    List getCreate() throws ResourceNotFoundException;

    List create(ProductTypeGroupForm productTypeGroupForm);

    List update(ProductTypeGroupForm productTypeGroupForm);

    List delete(Long productTypeGroupId);
}
