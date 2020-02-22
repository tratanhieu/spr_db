package dashboard.repositories;


import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierBranchForm;
import dashboard.dto.product.ProductSupplierForm;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductSupplierBranchMapper {

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
                    "FROM product_supplier_branch psb "
    )
    List<ProductSupplierBranchDto> findALL();

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
                    "WHERE psb.product_supplier_branch_id = #{productSupplierBranchId}"
    )
    Optional<ProductSupplierBranchDto> findById(@Param("productSupplierBranchId") Long productSupplierBranchId );


    @Insert(
            "INSERT INTO " +
                "product_supplier_branch( " +
                    "product_supplier_id," +
                    "name," +
                    "email," +
                    "phone," +
                    "fax," +
                    "province_id," +
                    "district_id," +
                    "ward_id," +
                    "address," +
                    "status)" +
                "values(" +
                    "#{productSupplierId}," +
                    "#{name}," +
                    "#{email}," +
                    "#{phone}," +
                    "#{fax}," +
                    "#{provinceId}," +
                    "#{districtId}," +
                    "#{wardId}," +
                    "#{address}," +
                    "#{status})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "productSupplierBranchId")
    void save(ProductSupplierBranchForm productSupplierBranchForm);

    @Update(
        "UPDATE " +
            "product_supplier_branch " +
        "SET " +
            "name = #{name}," +
            "email = #{email}," +
            "phone = #{phone}," +
            "fax = #{fax}," +
            "province_id = #{provinceId}," +
            "district_id = #{districtId}," +
            "ward_id = #{wardId}," +
            "address = #{address}," +
            "status = #{status} " +
        "WHERE product_supplier_branch_id = #{productSupplierBranchId}"
    )
    void update(ProductSupplierBranchForm productSupplierBranchForm);

    @Update(
            "UPDATE " +
                "product_supplier_branch " +
            "SET " +
                "status =  'STOP' " +
            "WHERE product_supplier_id = #{productSupplierId}"
    )
    void updateStatusWhenDeleteProductSupplier(@Param("productSupplierId") Long productSupplierId);

    @Update(
            "UPDATE " +
                    "product_supplier_branch " +
                    "SET " +
                    "status =  'STOP' " +
                    "WHERE product_supplier_branch_id = #{productSupplierBranchId}"
    )
    void delete(@Param("productSupplierBranchId") Long productSupplierBranchId);

    @Select(
            "SELECT TRUE FROM product_supplier_branch WHERE name = #{name} "
    )
    boolean checkExitsNameForInsert(@Param("name") String name);

    @Select(
            "SELECT TRUE FROM product_supplier_branch WHERE name = #{name} AND product_supplier_branch_id = #{productSupplierBranchId}"
    )
    boolean checkExitsNameForUpdate(@Param("name") String name,@Param("productSupplierBranchId") Long productSupplierBranchId);


}
