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
                "ps.status " +
            "FROM product_supplier ps"
    )
    List<ProductSupplierDto> getALL();

    @Select(
            "SELECT ps.product_supplier_id as productSupplierId, " +
                "ps.name, " +
                "ps.status " +
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
                "psb.status " +
                "FROM product_supplier_branch psb " +
                "WHERE psb.product_supplier_id = #{productSupplierId}"
    )
    List<ProductSupplierBranchDto> findAllProductSupplierBranchByProductSupplierId(@Param("productSupplierId") Long productSupplierId);

    @Insert(
            "INSERT INTO product_supplier(name,status) values(#{name},#{status})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "productSupplierId")
    void save(ProductSupplier productSupplier);

    @Update(
            "UPDATE product_supplier " +
                    "SET " +
                    "name = #{name}, " +
                    "status = #{status} " +
                    "WHERE product_supplier_id = #{productSupplierId}"
    )
    void update(ProductSupplier productSupplier);

    @Update(
            "UPDATE " +
                "product_supplier " +
            "SET status = 'STOP' " +
            "WHERE product_supplier_id = #{productSupplierId}"
    )
    void deleteById(@Param("productSupplierId") Long productSupplierId);

    @Select(
            "SELECT TRUE FROM product_supplier WHERE name = #{name} "
    )
    boolean checkExitsNameForInsert(@Param("name") String name);

    @Select(
            "SELECT TRUE FROM product_supplier WHERE name = #{name} AND product_supplier_id = #{productSupplierId}"
    )
    boolean checkExitsNameForUpdate(@Param("name") String name,@Param("productSupplierId") Long productSupplierId);

}
