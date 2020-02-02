package dashboard.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.dto.BaseEntityDto;
import dashboard.enums.EntityStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

public class ProductBrandDto extends BaseEntityDto {

    private BigInteger productBrandId;

    private String name;

    private String slugName;

    private String image;

    private EntityStatus status;

    private BigInteger totalProduct;

    public BigInteger getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(BigInteger productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public BigInteger getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(BigInteger totalProduct) {
        this.totalProduct = totalProduct;
    }
}
