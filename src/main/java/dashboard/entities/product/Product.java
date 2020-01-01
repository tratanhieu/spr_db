package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;

import dashboard.entities.promotion.Promotion;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"},
		allowGetters = true)
public class Product extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
	@JsonProperty("product_id")
    private Long productId;

    @NotBlank
    @Column(name = "name", unique = true)
	@JsonProperty("name")
    private String name;

    @NotBlank
	@Column(name = "content")
	@JsonProperty("content")
    private String content;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
	@JsonIgnore
	private Set<ProductTag> productTags;

    @ManyToOne
	@JoinColumn(name = "promotion_id")
	private Promotion promotion;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<ProductTag> getProductTags() {
		return productTags;
	}

	public void setProductTags(Set<ProductTag> productTags) {
		this.productTags = productTags;
	}

	@JsonProperty("promotion")
	public Map<String, String> getPromotion() {
		Map map = new HashMap<String,String>();
		map.put("promotionId", String.valueOf(promotion.getPromotionId()));
		map.put("promotionName", promotion.getName());
		map.put("startDate", promotion.getFromDate());
		map.put("endDate", promotion.getToDate());
		return map;
	}

	@JsonProperty("promotion")
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
}
