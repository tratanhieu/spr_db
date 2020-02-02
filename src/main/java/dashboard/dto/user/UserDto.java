package dashboard.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import dashboard.dto.BaseEntityDto;
import dashboard.enums.EntityStatus;

import java.util.Date;

public class UserDto extends BaseEntityDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String middleName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fullName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    private String phone;

    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long provinceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long districtId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long wardId;

    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date lastLogin;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserGroupDto userGroup;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userGroupId;

    private String userGroupName;

    private EntityStatus status;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserGroupDto getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupDto userGroup) {
        this.userGroup = userGroup;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
