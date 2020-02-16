package dashboard.repositories;

import dashboard.dto.product.ProductSupplierDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductSupplierMapper {

    @Select(value = "SELECT ps.product_supplier_id as productSupplierId, " +
                    "ps.name, " +
                    "ps.status " +
                    "FROM product_supplier ps")
    List<ProductSupplierDto> getALL();

}
