package dashboard.services.implement;

import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.ProductSupplierMapper;
import dashboard.services.ProductSupplierService;
import dashboard.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        ProductSupplierDto response = productSupplierMapper.findById(productSupplierId).orElseThrow(ResourceNotFoundException::new);
        List<ProductSupplierBranchDto> productSupplierBranchDtoList =
                productSupplierMapper.findAllProductSupplierBranchByProductSupplierId(productSupplierId);

        int i = 0;
        int length = productSupplierBranchDtoList.size();
        ProductSupplierBranchDto productSupplierBranchDto;
        ProvinceServiceImpl province = new ProvinceServiceImpl();

        try {
            for (i = 0; i < length; i++) {

                productSupplierBranchDto = productSupplierBranchDtoList.get(i);

                productSupplierBranchDto.setProvinceName(
                    province.getProvince(productSupplierBranchDto.getProvinceId())
                );

                productSupplierBranchDto.setDistrictName(
                    province.getDistrict(productSupplierBranchDto.getProvinceId(),
                                productSupplierBranchDto.getDistrictId())
                );

                productSupplierBranchDtoList.get(i).setWardName(
                    province.getWard( productSupplierBranchDto.getProvinceId(),
                            productSupplierBranchDto.getDistrictId(),
                            productSupplierBranchDto.getWardId())
                );

                productSupplierBranchDtoList.set(i, productSupplierBranchDto);
            }
        } catch (Exception e){}

        response.setProductSupplierBranchList(productSupplierBranchDtoList);
        return response;
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
