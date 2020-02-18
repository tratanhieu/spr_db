package dashboard.repositories;

import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierDto;
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
                "psb.address " +
                "FROM product_supplier_branch psb " +
                "WHERE psb.product_supplier_id = #{productSupplierId}"
    )
    List<ProductSupplierBranchDto> findAllProductSupplierBranchByProductSupplierId(@Param("productSupplierId") Long productSupplierId);

}
