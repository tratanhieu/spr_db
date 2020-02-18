package dashboard.services;

import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.entities.product.ProductSupplierBranch;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.util.List;

public interface ProductSupplierBranchService {

    List<ProductSupplierBranch> getALL();

    ProductSupplierBranchDto getOne(Long productSupplierBranchId);

    void create(ProductSupplierForm productSupplierForm) throws ResourceNotFoundException;

    void update(ProductSupplierForm productSupplierForm) throws ResourceNotFoundException;

    void delete(Long productSupplierBranchId);
}
