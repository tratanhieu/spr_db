package dashboard.dto.product;

import dashboard.enums.EntityStatus;
import dashboard.enums.ProductStatus;

public class ProductDto {
    private Long productId;
    private String name;
    private String slugName;
    private Long productNameId;
    private String image;
    private String description;
    private String content;
    private int unitQuantity;
    private String unitName;
    private String packageName;
    private ProductStatus status;
}
