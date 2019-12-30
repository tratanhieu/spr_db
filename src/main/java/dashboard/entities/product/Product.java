package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;

import dashboard.entities.promotion.ProductPromotion;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
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
    private Long product_id;

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

    @OneToMany(mappedBy = "ProductPromotionIdentity.product_Id")
	private Set<ProductPromotion> productPromotions;

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
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
}
