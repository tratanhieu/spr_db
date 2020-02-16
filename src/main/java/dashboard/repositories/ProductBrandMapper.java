package dashboard.repositories;

import dashboard.dto.post.PostTypeDto;

import dashboard.dto.product.ProductBrandDto;
import dashboard.entities.post.PostType;
import dashboard.entities.product.ProductBrand;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductBrandMapper {

    @Select(
            "SELECT " +
                    "pbr.product_brand_id AS productBrandId, " +
                    "pbr.name, " +
                    "pbr.slug_name AS slugName, " +
                    "pbr.image, " +
                    "(SELECT COUNT(*) FROM product WHERE product_brand_id = pbr.product_brand_id) AS totalProduct, "+
                    "pbr.create_date AS createDate, " +
                    "pbr.update_date AS updateDate, " +
                    "pbr.status " +
                    "FROM product_brand pbr " +
                    "WHERE pbr.status <> 'DELETE' " +
                    "ORDER BY pbr.create_date DESC"
    )
    List<ProductBrandDto> findAllActiveProductBrand();

    @Select(
            "SELECT " +
                    "pbr.product_brand_id AS productBrandId, " +
                    "pbr.name, " +
                    "pbr.slug_name AS slugName, " +
                    "pbr.image, " +
                    "(SELECT COUNT(*) FROM product WHERE product_brand_id = pbr.product_brand_id) AS totalProduct, "+
                    "pbr.create_date AS createDate, " +
                    "pbr.update_date AS updateDate, " +
                    "pbr.status " +
                    "FROM product_brand pbr " +
                    "WHERE pbr.product_brand_id = #{productBrandId} "
    )
    Optional<ProductBrandDto> findById(@Param("productBrandId") Long productBrandId);

    @Insert(
            "INSERT INTO product_brand (name, slug_name, image, create_date,status) " +
                    "VALUE(" +
                    "#{name}, " +
                    "#{slugName}, " +
                    "#{image}," +
                    "NOW()," +
                    "#{status}" +
                    ")"
    )
    @Options(useGeneratedKeys = true, keyProperty = "productBrandId")
    Long save(ProductBrand productBrand);

    @Update(
            "UPDATE product_brand " +
                    "SET " +
                    "name = #{name}, " +
                    "slug_name = #{slugName}, " +
                    "image = #{image}, " +
                    "update_date = NOW(), " +
                    "status = #{status} " +
                    "WHERE product_brand_id = #{productBrandId}"
    )
    void update(ProductBrand productBrand);

    @Select(
            "DELETE FROM product_brand WHERE product_brand_id= #{productBrandId}"
    )
    void deleteById(@Param("productBrandId") Long productBrandId);
}
