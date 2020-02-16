package dashboard.services.implement;

import dashboard.dto.product.ProductCategoryDto;
import dashboard.entities.product.ProductCategory;
import dashboard.entities.product.ProductTypeGroup;
import dashboard.entities.product.ProductUnit;
import dashboard.repositories.*;
import dashboard.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductTypeGroupMapper productTypeGroupMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private ProductBrandMapper productBrandMapper;

    @Autowired
    private ProductUnitMapper productUnitMapper;

    public Map getCreate() {
        Map<String, Object> map = new HashMap<>();

        List<ProductCategory> productCategoryList = productCategoryMapper.findAllActiveProductCategory();
        Long productCategoryId = productCategoryList.get(0).getProductCategoryId();
        // Get list Product Type Group
        List<ProductTypeGroup> productTypeGroupList = productTypeGroupMapper.findActiveByProductCategoryId(
            productCategoryId
        );
        // Get product type group id
        Long productTypeGroupId = 0L;
        if (productTypeGroupList.size() > 0) {
            productTypeGroupId = productTypeGroupList.get(0).getProductTypeGroupId();
        }

        List<ProductUnit> productUnitList = productUnitMapper.findAll();

        map.put("productCategoryList", productCategoryList);
        map.put("productTypeGroupList", productTypeGroupList);
        map.put("productTypeList", productTypeMapper.findAllActiveByCategoryAndGroupType(
            productCategoryId,
            productTypeGroupId
        ));
        map.put("productBrandList", productBrandMapper.findAllActives());
        map.put("productUnitList", productUnitList);

        return map;
    }
}
