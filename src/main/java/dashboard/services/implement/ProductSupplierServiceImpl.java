package dashboard.services.implement;

import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierBranchForm;
import dashboard.dto.product.ProductSupplierDto;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.entities.product.ProductSupplier;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
import dashboard.repositories.ProductSupplierBranchMapper;
import dashboard.repositories.ProductSupplierMapper;
import dashboard.services.ProductSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ProductSupplierServiceImpl implements ProductSupplierService {

    @Autowired
    ProductSupplierMapper productSupplierMapper;

    @Autowired
    ProductSupplierBranchMapper productSupplierBranchMapper;

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

                productSupplierBranchDto.setWardName(
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
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor={Exception.class}
    )
    public void create(ProductSupplierForm productSupplierForm) {

        if(productSupplierForm.getProductSupplierId() != null ) {
            throw new ValidationException("productSupplierId","is not null");
        }

        if(productSupplierForm.getProductSupplierBranch().length == 0) {
            throw new ValidationException("listProductSupplierBrand","is null");
        }

        if(productSupplierMapper.checkExitsNameForInsert(productSupplierForm.getName())) {

            throw new ValidationException("name","is exits");
        }

        ProductSupplier productSupplier = new  ProductSupplier(productSupplierForm);
        productSupplierMapper.save(productSupplier);

        int i =  0;
        ProductSupplierBranchForm [] productSupplierBranchForms = productSupplierForm.getProductSupplierBranch();
        int length = productSupplierBranchForms.length;

        for (i = 0; i < length; i++) {

            if(productSupplierBranchMapper.checkExitsNameForInsert(productSupplierBranchForms[i].getName())) {

                throw new ValidationException("productSupplierName", "is exist");
            }

            if(!ValidationUtils.iEmail(productSupplierBranchForms[i].getEmail())){
                throw new ValidationException("email","is not correct format");
            }

            if(!ValidationUtils.isPhone(productSupplierBranchForms[i].getPhone())){
                throw new ValidationException("phone","is not correct format");
            }

            if(!ValidationUtils.isFax(productSupplierBranchForms[i].getFax())){
                throw new ValidationException("fax","is not correct format");
            }


            productSupplierBranchForms[i].setProductSupplierId(productSupplier.getProductSupplierId());
            productSupplierBranchMapper.save(productSupplierBranchForms[i]);
        }

    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor={Exception.class}
    )
    public void update(ProductSupplierForm productSupplierForm) {

        if(productSupplierForm.getProductSupplierId() == null ) {
            throw new ValidationException("productSupplierId","is null");
        }

        if(productSupplierForm.getProductSupplierBranch().length == 0) {
            throw new ValidationException("listProductSupplierBrand","is null");
        }

        if(productSupplierMapper.checkExitsNameForUpdate(productSupplierForm.getName(), productSupplierForm.getProductSupplierId())) {
            throw new ValidationException("name","is exits");
        }

        List<ProductSupplierBranchForm> listItemUpdate = new ArrayList<>();
        List<ProductSupplierBranchDto> listItemDelete = new ArrayList<>();
        List<ProductSupplierBranchForm> listInputItem = Arrays.asList(productSupplierForm.getProductSupplierBranch());
        List<ProductSupplierBranchDto> listDBItem = productSupplierMapper.findAllProductSupplierBranchByProductSupplierId(productSupplierForm.getProductSupplierId());

        AtomicBoolean deleteFlg = new AtomicBoolean(false);

        listDBItem.forEach(dbItem -> {
              deleteFlg.set(false);

            listInputItem.forEach(inputItem -> {

                if(productSupplierBranchMapper.checkExitsNameForUpdate(inputItem.getName(),inputItem.getProductSupplierBranchId())){
                    throw new ValidationException("productSupplierName", "is exist");
                }

                if(!ValidationUtils.iEmail(inputItem.getEmail())){
                    throw new ValidationException("email","is not correct format");
                }

                if(!ValidationUtils.isPhone(inputItem.getPhone())){
                    throw new ValidationException("phone","is not correct format");
                }

                if(!ValidationUtils.isFax(inputItem.getFax())){
                    throw new ValidationException("fax","is not correct format");
                }

                if (inputItem.getProductSupplierBranchId() == null) {
                    productSupplierBranchMapper.save(inputItem);
                    deleteFlg.set(true);
                }

                if (inputItem.getProductSupplierBranchId() == dbItem.getProductSupplierBranchId()) {
                    listItemUpdate.add(inputItem);
                    deleteFlg.set(true);
                }

            });

            if(!deleteFlg.get()) {
                listItemDelete.add(dbItem);
            }
        });

        listItemUpdate.forEach(itemUpdate -> {
            itemUpdate.setProductSupplierId(productSupplierForm.getProductSupplierId());
            productSupplierBranchMapper.update(itemUpdate);
        });

        listItemDelete.forEach(itemDelete -> {
            productSupplierBranchMapper.delete(itemDelete.getProductSupplierBranchId());
        });

    }

    @Override
    public void delete(Long productSupplierId) throws ResourceNotFoundException {
        productSupplierMapper.deleteById(productSupplierId);
        productSupplierBranchMapper.updateStatusWhenDeleteProductSupplier(productSupplierId);
    }
}
