package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.FileIOUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.product.ProductBrandDto;
import dashboard.dto.product.ProductBrandForm;
import dashboard.entities.product.Product;
import dashboard.entities.product.ProductBrand;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.ProductBrandMapper;
import dashboard.repositories.ProductBrandRepository;
import dashboard.services.ProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EmbeddedId;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductBrandSerivceImpl implements ProductBrandService {

    @Autowired
    ProductBrandMapper productBrandMapper;
    @Override
    public List getAll() {
        return productBrandMapper.findAllActiveProductBrand();
    }

    @Override
    public List getAllActives() {
        return productBrandMapper.findAllActives();
    }

    @Override
    public ProductBrandDto getOne(Long productBrandId) throws ResourceNotFoundException {

        return productBrandMapper.findById(productBrandId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    public void create(ProductBrandForm productBrandForm) {

        FileIOUtils fileIOUtils = new FileIOUtils();
        try {
            if (productBrandForm.getProductBrandId() != null) {
                throw new ValidationException("product brand id not null");
            }

            // Make slug name
            if (ValidationUtils.isBlank(productBrandForm.getSlugName())) {
                productBrandForm.setSlugName(DataUtils.makeSlug(productBrandForm.getName()));
            }

             // Convert image form Base64 file to image file and log file
             Map mapUpload = fileIOUtils.createImageViaBase64Encode(productBrandForm.getImage()
                  , productBrandForm.getSlugName());

            // Set data for image
            productBrandForm.setImage((String) mapUpload.get(FileIOUtils.PATH));

            ProductBrand productBrand = new ProductBrand(productBrandForm);

            productBrandMapper.save(productBrand);
        } catch (Exception ex) {
            ex.printStackTrace();
            fileIOUtils.rollBackUploadedImages();
        }
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    public void update(ProductBrandForm productBrandForm) throws ResourceNotFoundException {

        FileIOUtils fileIOUtils = new FileIOUtils();
        ProductBrandDto productBrandDto;
        try {
            if (productBrandForm.getProductBrandId() == null) {
                throw new ValidationException("Product brand id cannot empty");
            }

            if (ValidationUtils.isBlank(productBrandForm.getSlugName())) {
                productBrandForm.setSlugName(DataUtils.makeSlug(productBrandForm.getName()));
            }

            try {
                productBrandDto = getOne(productBrandForm.getProductBrandId());
            } catch (Exception e) {
                throw new ResourceNotFoundException();
            }

            if (ValidationUtils.isBlank(productBrandDto.getImage())) {
                Map mapUpload =  fileIOUtils.createImageViaBase64Encode(productBrandForm.getImage()
                        , productBrandForm.getSlugName());
                productBrandForm.setImage((String) mapUpload.get(FileIOUtils.PATH));
            } else {

                fileIOUtils.removeImageFromURL(productBrandDto.getImage());
                Map mapUpload =  fileIOUtils.createImageViaBase64Encode(productBrandForm.getImage(),productBrandForm.getSlugName());
                productBrandForm.setImage((String) mapUpload.get(FileIOUtils.PATH));
            }

            ProductBrand productBrand = new ProductBrand(productBrandForm);
            productBrandMapper.update(productBrand);
        } catch (Exception ex) {
            ex.printStackTrace();
            fileIOUtils.rollBackUploadedImages();
        }
    }

    @Override
    public void delete(Long productBrandId){

        productBrandMapper.deleteById(productBrandId);

    }
}
