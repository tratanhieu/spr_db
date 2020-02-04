package dashboard.dto.user.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Map;

public class CustomerDto {

    @JsonIgnore
    private Long userId;

    private String firstName;

    private String lastName;

    private String middleName;

    private Date dob;

    private String email;

    private String phone;

    @JsonIgnore
    private String provinceId;

    @JsonIgnore
    private String districtId;

    @JsonIgnore
    private String wardId;

    private Map<String, String> province;

    private Map<String, String> district;

    private Map<String, String> ward;

    private String address;

    private String status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public Map<String, String> getProvince() {
        return province;
    }

    public void setProvince(Map<String, String> province) {
        this.province = province;
    }

    public Map<String, String> getDistrict() {
        return district;
    }

    public void setDistrict(Map<String, String> district) {
        this.district = district;
    }

    public Map<String, String> getWard() {
        return ward;
    }

    public void setWard(Map<String, String> ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
