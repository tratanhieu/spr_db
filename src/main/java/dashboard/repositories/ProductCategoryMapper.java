package dashboard.repositories;

import dashboard.dto.product.ProductBrandDto;
import dashboard.dto.product.ProductCategoryDto;
import dashboard.entities.product.ProductBrand;
import dashboard.entities.product.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductCategoryMapper {

    @Select(
            "SELECT " +
                    "product_category_id AS productCategoryId, " +
                    "name, " +
                    "slug_name AS slugName, " +
                    "create_date AS createDate, " +
                    "update_date AS updateDate, " +
                    "status " +
                    "FROM product_category pc " +
                    "WHERE pc.status <> 'DELETE' " +
                    "ORDER BY pc.create_date DESC"
    )
    List<ProductCategoryDto> findAllActiveProductCategory();

    @Select(
            "SELECT " +
                    "product_category_id AS productCategoryId, " +
                    "name, " +
                    "slug_name AS slugName, " +
                    "create_date AS createDate, " +
                    "update_date AS updateDate, " +
                    "status " +
                    "FROM product_category " +
                    "WHERE product_category_id = #{productCategoryId}"
    )
    Optional<ProductCategoryDto> findById(@Param("productCategoryId") Long productCategoryId);

    @Insert(
            "INSERT INTO product_category (name, slug_name, create_date,status) " +
                    "VALUE(" +
                    "#{name}, " +
                    "#{slugName}, " +
                    "NOW()," +
                    "#{status}" +
                    ")"
    )
    @SelectKey(
            statement = "SELECT LAST_INSERT_ID() AS productCategoryId",
            keyProperty = "productCategoryId",
            before = true,
            resultType = Long.class
    )
    Long save(ProductCategory productCategory);

    @Update(
            "UPDATE product_category " +
                    "SET " +
                    "name = #{name}, " +
                    "slug_name = #{slugName}, " +
                    "update_date = NOW(), " +
                    "status = #{status} " +
                    "WHERE product_category_id = #{productCategoryId}"
    )
    void update(ProductCategory productCategory);

    @Select(
            "DELETE FROM product_category WHERE product_category_id = #{productCategoryId}"
    )
    void deleteById(@Param("productCategoryId") Long productCategoryId);

    @Select("SELECT EXISTS(SELECT 1 FROM product_category WHERE product_category.product_category_id = #{id} LIMIT 1)")
    int checkExistKey(@Param("id") Long id);

}
