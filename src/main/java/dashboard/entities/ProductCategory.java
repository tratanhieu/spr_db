package dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @NotBlank
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus active) {
		this.status = active;
	}
}
