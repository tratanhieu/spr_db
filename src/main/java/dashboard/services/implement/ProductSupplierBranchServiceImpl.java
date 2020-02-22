package dashboard.services.implement;

import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierBranchForm;
import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.entities.product.Product;
import dashboard.entities.product.ProductSupplier;
import dashboard.entities.product.ProductSupplierBranch;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
import dashboard.repositories.ProductSupplierBranchMapper;
import dashboard.repositories.ProductSupplierMapper;
import dashboard.services.ProductSupplierBranchService;
import dashboard.services.ProductSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductSupplierBranchServiceImpl implements ProductSupplierBranchService {

    @Autowired
    ProductSupplierMapper productSupplierMapper;

    @Autowired
    ProductSupplierBranchMapper productSupplierBranchMapper;

    @Override
    public List<ProductSupplierBranchDto> getALL() {
        List<ProductSupplierBranchDto> productSupplierBranchDtoList = productSupplierBranchMapper.findALL();

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

                productSupplierBranchDto.setWardName(
                        province.getWard( productSupplierBranchDto.getProvinceId(),
                                productSupplierBranchDto.getDistrictId(),
                                productSupplierBranchDto.getWardId())
                );

                productSupplierBranchDtoList.set(i, productSupplierBranchDto);
            }
        } catch (Exception e){}

        return productSupplierBranchDtoList;
    }

    @Override
    public ProductSupplierBranchDto getOne(Long productSupplierBranchId) throws ResourceNotFoundException {

        ProductSupplierBranchDto productSupplierBranchDto = productSupplierBranchMapper.findById(productSupplierBranchId).orElseThrow(ResourceNotFoundException::new);

        ProvinceServiceImpl province = new ProvinceServiceImpl();

        try {
                productSupplierBranchDto.setProvinceName(
                        province.getProvince(productSupplierBranchDto.getProvinceId())
                );

                productSupplierBranchDto.setDistrictName(
                        province.getDistrict(productSupplierBranchDto.getProvinceId(),
                                productSupplierBranchDto.getDistrictId())
                );

            productSupplierBranchDto.setWardName(
                        province.getWard( productSupplierBranchDto.getProvinceId(),
                                productSupplierBranchDto.getDistrictId(),
                                productSupplierBranchDto.getWardId())
                );

        } catch (Exception e){

        }
        return productSupplierBranchDto;
    }

    @Override

    public List<ProductSupplierDto> getCreate() {
        return productSupplierMapper.getALL();
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor={Exception.class}
    )
    public void create(ProductSupplierBranchForm productSupplierBranchForm) throws ResourceNotFoundException {

        if (productSupplierBranchForm.getProductSupplierBranchId() != null) {
            throw new ValidationException("productSupplierBranchId","is not null");
        }
        if(!ValidationUtils.iEmail(productSupplierBranchForm.getEmail())){
            throw new ValidationException("email","is not correct format");
        }

        if(!ValidationUtils.isPhone(productSupplierBranchForm.getPhone())){
            throw new ValidationException("phone","is not correct format");
        }

        if(!ValidationUtils.isFax(productSupplierBranchForm.getFax())){
            throw new ValidationException("fax","is not correct format");
        }

        productSupplierBranchMapper.save(productSupplierBranchForm);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor={Exception.class}
    )
    public void update(ProductSupplierBranchForm productSupplierBranchForm) throws ResourceNotFoundException {

        if (productSupplierBranchForm.getProductSupplierBranchId() == null) {
            throw new ValidationException("productSupplierBranchId","is null");
        }

        if (productSupplierBranchForm.getProductSupplierBranchId() != null) {
            throw new ValidationException("productSupplierBranchId","is not null");
        }
        if(!ValidationUtils.iEmail(productSupplierBranchForm.getEmail())){
            throw new ValidationException("email","is not correct format");
        }

        if(!ValidationUtils.isPhone(productSupplierBranchForm.getPhone())){
            throw new ValidationException("phone","is not correct format");
        }

        if(!ValidationUtils.isFax(productSupplierBranchForm.getFax())){
            throw new ValidationException("fax","is not correct format");
        }

        productSupplierBranchMapper.update(productSupplierBranchForm);
    }

    @Override
    public void delete(Long productSupplierBranchId) {
        productSupplierBranchMapper.delete(productSupplierBranchId);
    }
}
