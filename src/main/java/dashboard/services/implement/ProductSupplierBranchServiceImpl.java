package dashboard.services.implement;

import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.entities.product.ProductSupplierBranch;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductSupplierBranchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSupplierBranchServiceImpl implements ProductSupplierBranchService {
    @Override
    public List<ProductSupplierBranch> getALL() {
        return null;
    }

    @Override
    public ProductSupplierBranchDto getOne(Long productSupplierBranchId) {
        return null;
    }

    @Override
    public void create(ProductSupplierForm productSupplierForm) throws ResourceNotFoundException {

    }

    @Override
    public void update(ProductSupplierForm productSupplierForm) throws ResourceNotFoundException {

    }

    @Override
    public void delete(Long productSupplierBranchId) {

    }
}
