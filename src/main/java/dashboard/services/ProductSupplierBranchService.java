package dashboard.services;

import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierBranchForm;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.entities.product.ProductSupplierBranch;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.util.List;

public interface ProductSupplierBranchService {

    List<ProductSupplierBranchDto> getALL();

    ProductSupplierBranchDto getOne(Long productSupplierBranchId) throws ResourceNotFoundException;

    List getCreate();

    void create(ProductSupplierBranchForm productSupplierBranchForm) throws ResourceNotFoundException;

    void update(ProductSupplierBranchForm productSupplierBranchForm) throws ResourceNotFoundException;

    void delete(Long productSupplierBranchId);
}
