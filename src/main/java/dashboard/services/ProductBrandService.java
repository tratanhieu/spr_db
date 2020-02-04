package dashboard.services;

import dashboard.dto.product.ProductBrandDto;
import dashboard.dto.product.ProductBrandForm;

import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.util.List;

public interface ProductBrandService {

    List<ProductBrandDto> getAllWithPagination();

    ProductBrandDto getOne(Long productBrandId) throws ResourceNotFoundException;

    List create(ProductBrandForm productBrandForm);

    List update(ProductBrandForm productBrandForm);

    int delete(Long productBrandId) throws ResourceNotFoundException;

    int updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) throws ResourceNotFoundException;
}
