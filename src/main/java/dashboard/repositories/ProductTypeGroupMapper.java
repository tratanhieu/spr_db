package dashboard.repositories;

import dashboard.dto.product.ProductBrandDto;
import dashboard.dto.product.ProductTypeGroupDto;
import dashboard.dto.product.ProductTypeGroupForm;
import dashboard.entities.product.ProductBrand;
import dashboard.entities.product.ProductTypeGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductTypeGroupMapper {

    @Select(
            "SELECT " +
                    "ptg.product_type_group_id AS productTypeGroupId, " +
                    "ptg.name, " +
                    "ptg.slug_name AS slugName, " +
                    "pc.name AS productCategoryName," +
                    "ptg.create_date AS createDate, " +
                    "ptg.update_date AS updateDate, " +
                    "ptg.status " +
                    "FROM product_type_group ptg " +
                    "INNER JOIN product_category pc " +
                    "ON ptg.product_category_id = pc.product_category_id " +
                    "WHERE ptg.status <> 'DELETE' " +
                    "ORDER BY ptg.create_date DESC"
    )
    List<ProductTypeGroupDto> findAllActiveProductTypeGroup();

    @Select(
            "SELECT " +
                    "product_type_group_id AS productTypeGroupId, " +
                    "ptg.name, " +
                    "ptg.slug_name AS slugName, " +
                    "pc.name AS productCategoryName, " +
                    "ptg.create_date AS createDate, " +
                    "ptg.update_date AS updateDate, " +
                    "ptg.status " +
                    "FROM product_type_group ptg " +
                    "INNER JOIN product_category pc "  +
                    "ON ptg.product_category_id = pc.product_category_id " +
                    "WHERE product_type_group_id = #{productTypeGroupId}"
    )
    Optional<ProductTypeGroupDto> findById(@Param("productTypeGroupId") Long productTypeGroupId);

    @Insert(
            "INSERT INTO product_type_group (name, slug_name, product_category_id, create_date,status) " +
                    "VALUE(" +
                    "#{name}, " +
                    "#{slugName}, " +
                    "#{productCategoryId}," +
                    "NOW()," +
                    "#{status}" +
                    ")"
    )
    @Options(useGeneratedKeys = true, keyProperty = "productTypeGroupId")
    Long save(ProductTypeGroupForm productTypeGroupForm);

    @Update(
            "UPDATE product_brand " +
                    "SET " +
                    "name = #{name}, " +
                    "slug_name = #{slugName}, " +
                    "product_category_id = #{productCategoryId}, " +
                    "update_date = NOW(), " +
                    "status = #{status} " +
                    "WHERE product_type_group_id = #{productTypeGroupId}"
    )
    void update(ProductTypeGroupForm productTypeGroupForm);

    @Select(
            "DELETE FROM product_type_group WHERE product_type_group_id= #{productTypeGroupID}"
    )
    void deleteById(@Param("productTypeGroupId") Long productTypeGroupId);
}
