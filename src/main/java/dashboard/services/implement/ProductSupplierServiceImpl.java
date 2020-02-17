package dashboard.services.implement;

import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.ProductSupplierMapper;
import dashboard.services.ProductSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSupplierServiceImpl implements ProductSupplierService {

    @Autowired
    ProductSupplierMapper productSupplierMapper;

    @Override
    public List<ProductSupplierDto> getAll() {
        return productSupplierMapper.getALL();
    }

    @Override
    public ProductSupplierDto getOne(Long productSupplierId) throws ResourceNotFoundException {
        return productSupplierMapper.findById(productSupplierId).orElseThrow(ResourceNotFoundException::new);
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
