package dashboard.dto.product;

import dashboard.dto.BaseEntityDto;
import dashboard.enums.EntityStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ProductSupplierBranchDto extends BaseEntityDto {


    private Long productSupplierBranchId;

    private String productSupplierName;

    private String name;

    private String email;

    private String phone;

    private String fax;

    private String provinceId;

    private String provinceName;

    private String districtId;

    private String districtName;

    private String wardId;

    private String wardName;

    private String address;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public Long getProductSupplierBranchId() {
        return productSupplierBranchId;
    }

    public void setProductSupplierBranchId(Long productSupplierBranchId) {
        this.productSupplierBranchId = productSupplierBranchId;
    }

    public String getProductSupplierName() {
        return productSupplierName;
    }

    public void setProductSupplierName(String productSupplierName) {
        this.productSupplierName = productSupplierName;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
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
