package dashboard.services.implement;

import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductTypeDto;
import dashboard.dto.product.ProductTypeForm;
import dashboard.entities.product.ProductType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductTypeMapper;
import dashboard.repositories.ProductTypeRepository;
import dashboard.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductTypeMapper productTypeMapper;


    @Override
    public List<ProductTypeDto> getAll() {
      return productTypeMapper.findAllActiveProductType();
    }

    @Override
    public ProductTypeDto getOne(Long productTypeId) throws ResourceNotFoundException {
        return productTypeMapper.findById(productTypeId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    public void create(ProductTypeForm productTypeForm) {

        if (productTypeForm.getProductTypeId() != null) {
            throw new ValidationException("Product type id is not null");
        }

        if (ValidationUtils.isBlank(productTypeForm.getSlugName())) {
            productTypeForm.setSlugName(productTypeForm.getName());
        }

        productTypeMapper.save(productTypeForm);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    public void update(ProductTypeForm productTypeForm) throws ResourceNotFoundException {

        if (productTypeForm.getProductTypeId() == null) {
            throw new ValidationException("Product type id is null");
        }

        if (ValidationUtils.isBlank(productTypeForm.getSlugName())) {
            productTypeForm.setSlugName(productTypeForm.getName());
        }

        productTypeMapper.update(productTypeForm);

    }

    @Override
    public void delete(Long productTypeId) throws ResourceNotFoundException {

        productTypeMapper.deleteById(productTypeId);
    }

    @Override
    public int updateStatusByListId(List<Long> listId, EntityStatus entityStatus) throws ResourceNotFoundException {
        int result = productTypeRepository.updateStatusByListId(entityStatus, listId);
        return result;
    }
}
