package dashboard.services;

import dashboard.dto.product.ProductTypeDto;
import dashboard.dto.product.ProductTypeForm;
import dashboard.entities.product.ProductType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductTypeService {

    List<ProductTypeDto> getAll();

    ProductTypeDto getOne(Long productTypeId) throws ResourceNotFoundException;

    void create(ProductTypeForm productTypeForm);

    void update(ProductTypeForm productTypeForm) throws ResourceNotFoundException;

    void delete(Long productTypeId) throws ResourceNotFoundException;

    int updateStatusByListId(List<Long> listId, EntityStatus entityStatus) throws ResourceNotFoundException;

}
