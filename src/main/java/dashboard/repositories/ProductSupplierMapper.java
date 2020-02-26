package dashboard.repositories;

import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierDto;
import dashboard.entities.product.ProductSupplier;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductSupplierMapper {

    @Select(
            "SELECT ps.product_supplier_id as productSupplierId, " +
                "ps.name, " +
                "ps.status, " +
                "ps.create_date AS createDate, " +
                "ps.update_date AS updateDate, " +
                "ps.delete_date AS deleteDate " +
            "FROM product_supplier ps"
    )
    List<ProductSupplierDto> getALL();

    @Select(
            "SELECT ps.product_supplier_id as productSupplierId, " +
                "ps.name, " +
                "ps.status," +
                "ps.create_date AS createDate, " +
                "ps.update_date AS updateDate, " +
                "ps.delete_date AS deleteDate " +
            "FROM product_supplier ps " +
            "WHERE ps.product_supplier_id = #{productSupplierId}"
    )
    Optional<ProductSupplierDto> findById(@Param("productSupplierId") Long productSupplierId);

    @Select(
            "SELECT " +
                "psb.product_supplier_branch_id as productSupplierBranchId,"+
                "(SELECT ps.name FROM product_supplier ps WHERE ps.product_supplier_id = psb.product_supplier_id) AS productSupplierName," +
                "psb.name," +
                "psb.email," +
                "psb.phone," +
                "psb.fax," +
                "psb.province_id AS provinceId," +
                "psb.district_id AS districtId," +
                "psb.ward_id AS wardId," +
                "psb.address," +
                "psb.status, " +
                "psb.create_date AS createDate, " +
                "psb.update_date AS updateDate, " +
                "psb.delete_date AS deleteDate " +
                "FROM product_supplier_branch psb " +
                "WHERE psb.product_supplier_id = #{productSupplierId}"
    )
    List<ProductSupplierBranchDto> findAllProductSupplierBranchByProductSupplierId(@Param("productSupplierId") Long productSupplierId);

    @Insert(
            "INSERT INTO product_supplier(name,status,create_date) values(#{name},#{status},NOW())"
    )
    @Options(useGeneratedKeys = true, keyProperty = "productSupplierId")
    void save(ProductSupplier productSupplier);

    @Update(
            "UPDATE product_supplier " +
                    "SET " +
                    "name = #{name}, " +
                    "status = #{status} " +
                    "update_date = NOW() " +
                    "WHERE product_supplier_id = #{productSupplierId}"
    )
    void update(ProductSupplier productSupplier);

    @Update(
            "UPDATE " +
                "product_supplier " +
            "SET status = 'STOP', " +
                "delete_date = NOW() " +
            "WHERE product_supplier_id = #{productSupplierId}"
    )
    void deleteById(@Param("productSupplierId") Long productSupplierId);

    @Select(
            "SELECT EXISTS(SELECT 1  FROM product_supplier ps WHERE ps.name = #{name} LIMIT 1)"
    )
    int checkExitsNameForInsert(@Param("name") String name);

    @Select(
            "SELECT EXISTS(SELECT 1  FROM product_supplier ps WHERE ps.name = #{name} AND ps.product_supplier_id != #{productSupplierId} LIMIT 1)"
    )
    int checkExitsNameForUpdate(@Param("name") String name,@Param("productSupplierId") Long productSupplierId);

}
