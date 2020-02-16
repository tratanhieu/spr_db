package dashboard.services;

import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.dto.product.ProductTypeGroupDto;
import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductSupplierService {

    List<ProductSupplierDto> getAll();

    ProductSupplierDto getOne(Long productSupplierId) throws ResourceNotFoundException;

    List getCreate() throws ResourceNotFoundException;

    List create(ProductSupplierForm productSupplierForm );

    List update(ProductSupplierForm productSupplierForm );

    List delete(Long productSupplier) throws ResourceNotFoundException;

}
