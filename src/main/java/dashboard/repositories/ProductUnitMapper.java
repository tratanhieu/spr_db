package dashboard.repositories;

import dashboard.dto.product.ProductTypeDto;
import dashboard.entities.product.ProductUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductUnitMapper {
    @Select(
        "SELECT " +
            "product_unit_id AS productUnitId, " +
            "name " +
        "FROM product_unit " +
        "ORDER BY name ASC"
    )
    List<ProductUnit> findAll();
}
