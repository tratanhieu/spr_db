package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.commons.StringUtils;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "product_category")
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
	@JsonProperty("product_category_id")
    private Long productCategoryId;

    @NotNull(message = "Tên Danh mục Sản phẩm không được bỏ trống")
    @Size(min = 2, message = "Độ dài tối thiểu là 2 ký tự")
    @Size(max = 50, message = "Độ dài tối đa là 50 ký tự")
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "slug_name", unique = true)
	@JsonProperty("slug_name")
    private String slugName;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProductTypeGroup> productTypeGroups;

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
}
