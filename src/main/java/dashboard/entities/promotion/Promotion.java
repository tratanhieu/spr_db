package dashboard.entities.promotion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.entities.product.Product;
import dashboard.enums.PromotionStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "promotion",
        uniqueConstraints = {
            @UniqueConstraint(name = "UK_name", columnNames = "name")
        })
@EntityListeners(AuditingEntityListener.class)
public class Promotion extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promotion_id")
    @JsonProperty("promotionId")
    private Long promotionId;

    @NotBlank(message = "name is not blank")
    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "percent")
    @JsonProperty("percent")
    private int percent;

    @Column(name = "from_date")
    @JsonProperty("fromDate")
    private Date fromDate;

    @Column(name = "to_date")
    @JsonProperty("toDate")
    private Date toDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    private Set<PromotionCode> promotionCodes;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    private Set<Product> products;

    @Transient
    private Boolean allProduct;

    @Transient
    private Long[] listProductId;

    @Transient
    private PromotionCode[] promotionCodeArray;

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public PromotionStatus getStatus() {
        return status;
    }

    public void setStatus(PromotionStatus status) {
        this.status = status;
    }


    @JsonProperty("promotionCodes")
    public Set<PromotionCode> getPromotionCodes() {
        return promotionCodes;
    }

    @JsonIgnore
    public void setPromotionCodes(Set<PromotionCode> promotionCodes) {
        this.promotionCodes = promotionCodes;
    }

    @JsonProperty("products")
    public Set<Product> getProducts() {
        return products;
    }

    @JsonIgnore
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @JsonIgnore
    public Boolean getAllProduct() {
        return allProduct;
    }

    @JsonProperty("allProduct")
    public void setAllProduct(Boolean allProduct) {
        this.allProduct = allProduct;
    }

    @JsonIgnore
    public Long[] getListProductId() {
        return listProductId;
    }

    @JsonProperty("listProductId")
    public void setListProductId(Long[] listProductId) {
        this.listProductId = listProductId;
    }

    @JsonIgnore
    public PromotionCode[] getPromotionCodeArray() {
        return promotionCodeArray;
    }

    @JsonProperty("promotionCodeArray")
    public void setPromotionCodeArray(PromotionCode[] promotionCodeArray) {
        this.promotionCodeArray = promotionCodeArray;
    }

    public Boolean isEquals(Promotion promotion) {
        return this.name.equals(promotion.getName())
                && this.percent == promotion.percent
                && this.fromDate.equals(promotion.getFromDate())
                && this.toDate.equals(promotion.getToDate())
                && this.status.equals(promotion.getStatus());
    }

}
