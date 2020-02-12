package dashboard.services;

import dashboard.dto.product.ProductBrandDto;
import dashboard.dto.product.ProductBrandForm;

import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.util.List;

public interface ProductBrandService {

    List<ProductBrandDto> getAll();

    ProductBrandDto getOne(Long productBrandId) throws ResourceNotFoundException;

    void create(ProductBrandForm productBrandForm);

    void  update(ProductBrandForm productBrandForm) throws ResourceNotFoundException;

    void  delete(Long productBrandId);

    int updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) throws ResourceNotFoundException;
}
