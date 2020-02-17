package dashboard.repositories;

import dashboard.dto.product.ProductSupplierDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

}
