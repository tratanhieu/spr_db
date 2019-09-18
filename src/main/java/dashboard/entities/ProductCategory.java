package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dashboard.commons.StringUtils;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "product_category")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleleDate"}, 
        allowGetters = true)
public class ProductCategory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    private Long productCategoryId;

    @NotNull(message = "Name is not null")
    @Column(name = "name", unique = true)
    private String name;
    
    @NotNull(message = "Slug name is not null")
    @Column(name = "slug_name", unique = true)
    private String slugName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
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
		this.slugName = (this.name != null && this.slugName == null) ?
						StringUtils.makeSlug(name) : this.slugName;;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus active) {
		this.status = active;
	}
}
