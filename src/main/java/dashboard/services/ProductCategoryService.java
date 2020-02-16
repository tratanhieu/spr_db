package dashboard.services;

import dashboard.dto.product.ProductCategoryDto;
import dashboard.dto.product.ProductCategoryForm;
import dashboard.entities.product.ProductCategory;
import dashboard.generics.ListEntityResponse;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCategoryService {

    List getAll();

    List getAllActives();

    ProductCategoryDto getOne(Long productCategoryId) throws ResourceNotFoundException;

    List create(ProductCategoryForm productCategoryForm);

    List update(ProductCategoryForm productCategoryForm);

    List delete(Long productCategoryId);
}