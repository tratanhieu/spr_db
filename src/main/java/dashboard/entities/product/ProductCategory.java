package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "product_category",
		uniqueConstraints = {
			@UniqueConstraint(name="UK_name", columnNames = "name"),
			@UniqueConstraint(name="UK_slugName", columnNames = "slug_name")
		}
)
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    private Long productCategoryId;

	@NotBlank(message = "{validation.name.notBlank}")
    @Size(min = 2, message = "{validation.minLength}")
    @Size(max = 50, message = "{validation.maxLength}")
    @Column(name = "name")
    private String name;

    @Column(name = "slug_name")
	@Size(min = 2, message = "{validation.minLength}")
	@Size(max = 50, message = "{validation.maxLength}")
    private String slugName;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<ProductTypeGroup> productTypeGroups;

    @Column(name = "status")
    @NotNull(message = "{validation.status.notBlank}")
	@Enumerated(EnumType.STRING)
    private EntityStatus status;

    public ProductCategory() {
    	super();
	}

	public ProductCategory(String name, String slugName, EntityStatus status) {
		this.name = name;
		this.slugName = slugName;
		this.status = status;
	}

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
		this.slugName = slugName;
	}

    public Set<ProductTypeGroup> getProductTypeGroups() {
        return productTypeGroups;
    }

    public void setProductTypeGroups(Set<ProductTypeGroup> productTypeGroups) {
        this.productTypeGroups = productTypeGroups;
    }

    public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus active) {
		this.status = active;
	}

	public boolean isEquals(ProductCategory productCategory) {
    	return this.name.equals(productCategory.name)
			&& this.slugName.equals(productCategory.slugName)
			&& this.status.equals((productCategory.status));
	}
}
