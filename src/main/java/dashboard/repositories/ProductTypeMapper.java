package dashboard.repositories;


import dashboard.dto.product.ProductTypeDto;
import dashboard.dto.product.ProductTypeForm;
import dashboard.dto.product.ProductTypeGroupDto;
import dashboard.dto.product.ProductTypeGroupForm;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductTypeMapper {

    @Select(
            "SELECT " +
                    "pt.product_type_id AS productTypeId, " +
                    "pt.name, " +
                    "pt.slug_name AS slugName, " +
                    "ptg.name AS productTypeGroupName," +
                    "pt.create_date AS createDate, " +
                    "pt.update_date AS updateDate, " +
                    "pt.status " +
                    "FROM product_type pt " +
                    "INNER JOIN product_type_group ptg " +
                    "ON pt.product_type_group_id = ptg.product_type_group_id " +
                    "WHERE pt.status <> 'DELETE' " +
                    "ORDER BY pt.create_date DESC"
    )
    List<ProductTypeDto> findAllActiveProductType();

    @Select(
            "SELECT " +
                    "product_type_id AS productTypeId, " +
                    "pt.name, " +
                    "pt.slug_name AS slugName, " +
                    "ptg.name AS productTypeGroupIdName, " +
                    "pt.create_date AS createDate, " +
                    "pt.update_date AS updateDate, " +
                    "pt.status " +
                    "FROM product_type pt " +
                    "INNER JOIN product_type_group ptg "  +
                    "ON pt.product_type_group_id = ptg.product_type_group_id " +
                    "WHERE product_type_id = #{productTypeId}"
    )
    Optional<ProductTypeDto> findById(@Param("productTypeId") Long productTypeId);

    @Insert(
            "INSERT INTO product_type (name, slug_name, product_type_group_id, create_date,status) " +
                    "VALUE(" +
                    "#{name}, " +
                    "#{slugName}, " +
                    "#{productTypeGroupId}," +
                    "NOW()," +
                    "#{status}" +
                    ")"
    )
    @Options(useGeneratedKeys = true, keyProperty = "productTypeId")
    Long save(ProductTypeForm productTypeForm);

    @Update(
            "UPDATE product_type " +
                    "SET " +
                    "name = #{name}, " +
                    "slug_name = #{slugName}, " +
                    "product_type_group_id = #{productTypeGroupId}, " +
                    "update_date = NOW(), " +
                    "status = #{status} " +
                    "WHERE product_type_id = #{productTypeId}"
    )
    void update(ProductTypeForm productTypeForm);

    @Select(
            "DELETE FROM product_type WHERE product_type_id= #{productTypeId}"
    )
    void deleteById(@Param("productTypeId") Long productTypeId);
}
