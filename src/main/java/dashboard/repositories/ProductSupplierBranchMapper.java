package dashboard.repositories;


import dashboard.dto.product.ProductSupplierBranchDto;
import dashboard.dto.product.ProductSupplierBranchForm;
import dashboard.dto.product.ProductSupplierForm;
import dashboard.entities.product.ProductSupplierBranch;
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
                    "psb.status, " +
                    "psb.create_date AS createDate, " +
                    "psb.update_date AS updateDate, " +
                    "psb.delete_date AS deleteDate " +
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
                    "psb.status, " +
                    "psb.create_date AS createDate, " +
                    "psb.update_date AS updateDate, " +
                    "psb.delete_date AS deleteDate " +
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
                    "status," +
                    "create_date)" +
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
                    "#{status}," +
                    "NOW())"
    )
    @Options(useGeneratedKeys = true, keyProperty = "productSupplierBranchId")
    void save(ProductSupplierBranch productSupplierBranch);

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
            "status = #{status}, " +
            "update_date = NOW() " +
        "WHERE product_supplier_branch_id = #{productSupplierBranchId}"
    )
    void update(ProductSupplierBranchForm productSupplierBranchForm);

    @Update(
            "UPDATE " +
                "product_supplier_branch " +
            "SET " +
                "status =  'STOP', " +
                "update_date = NOW()" +
            "WHERE product_supplier_id = #{productSupplierId}"
    )
    void updateStatusWhenDeleteProductSupplier(@Param("productSupplierId") Long productSupplierId);

    @Update(
            "UPDATE " +
                    "product_supplier_branch " +
                    "SET " +
                    "status =  'STOP', " +
                    "delete_date = NOW()"+
                    "WHERE product_supplier_branch_id = #{productSupplierBranchId}"
    )
    void delete(@Param("productSupplierBranchId") Long productSupplierBranchId);

    @Select(
            "SELECT EXISTS(SELECT 1  FROM product_supplier_branch psb WHERE psb.name = #{name} LIMIT 1)"
    )
    int checkExitsNameForInsert(@Param("name") String name);

    @Select(
            "SELECT EXISTS(SELECT 1  FROM product_supplier_branch psb WHERE psb.name = #{name}  AND psb.product_supplier_branch_id != #{productSupplierBranchId} LIMIT 1)"
    )
    int checkExitsNameForUpdate(@Param("name") String name,@Param("productSupplierBranchId") Long productSupplierBranchId);


}
