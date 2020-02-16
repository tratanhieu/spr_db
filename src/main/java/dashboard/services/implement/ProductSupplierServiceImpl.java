package dashboard.services.implement;

import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.ProductSupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSupplierServiceImpl implements ProductSupplierService {
    @Override
    public List<ProductSupplierDto> getAll() {
        return null;
    }

    @Override
    public ProductSupplierDto getOne(Long productSupplierId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List getCreate() throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List create(ProductSupplierForm productSupplierForm) {
        return null;
    }

    @Override
    public List update(ProductSupplierForm productSupplierForm) {
        return null;
    }

    @Override
    public List delete(Long productSupplier) throws ResourceNotFoundException {
        return null;
    }
}
