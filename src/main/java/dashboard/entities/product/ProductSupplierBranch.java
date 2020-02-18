package dashboard.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.enums.EntityStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

public class ProductSupplierBranch extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long productSupplierBranchId;

    private String name;

    private String email;

    private String phone;

    private String fax;

    private String provinceId;

    private String districtId;

    private String wardId;

    private String address;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getProductSupplierBranchId() {
        return productSupplierBranchId;
    }

    public void setProductSupplierBranchId(Long productSupplierBranchId) {
        this.productSupplierBranchId = productSupplierBranchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
