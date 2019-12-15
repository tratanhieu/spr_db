package dashboard.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dashboard.entities.base.BaseEntity;
import dashboard.entities.post.Post;
import dashboard.enums.UserStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user",
        uniqueConstraints = {
        @UniqueConstraint(name="UK_phone", columnNames = "phone"),
                @UniqueConstraint(name="UK_email", columnNames = "email")
})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "updateDate", "deleteDate"},
        allowGetters = true)
public class User extends BaseEntity implements Serializable {

    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long userId;

    @Column(name = "first_name")
    @NotNull(message = "First name is not null")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "middle_name")
    @JsonProperty("middleName")
    private String middleName;

    @Column(name = "last_name")
    @NotNull(message = "Last name is not null")
    @JsonProperty("lastName")
    private String lastName;

    @Column(name = "phone")
    @NotNull(message = "Phone is not null")
    @JsonProperty("phone")
    private String phone;

    @Column(name = "email")
    @NotNull(message = "Email is not null")
    @JsonProperty("email")
    private String email;

    @Column(name = "province_id")
    @NotNull(message = "Province ID is not null")
    @JsonProperty("provinceId")
    private Long provinceId;

    @Column(name = "district_id")
    @NotNull(message = "District ID is not null")
    @JsonProperty("districtId")
    private Long districtId;

    @Column(name = "ward_id")
    @NotNull(message = "Ward ID is not null")
    @JsonProperty("wardId")
    private Long wardId;

    @Column(name = "address")
    @NotNull(message = "Address is not null")
    @JsonProperty("address")
    private String address;

    @Column(name = "password")
    @NotNull(message = "Password is not null")
    @JsonProperty("password")
    private String password;

    @Column(name = "token")
    @JsonProperty("token")
    private String token;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "status")
    @NotNull(message = "{validation.status.notBlank}")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "user_group_id")
    @NotNull(message = "User group id is not null")
    @JsonProperty("userGroupId")
    private Long userGroupId;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Post> posts;

    public User() {
        super();
    }

    public User(String firstName, String middleName, String lastName, String phone, String email,
                Long provinceId, Long districtId, Long wardId, String address, String password,
                String token, UserStatus status, Long userGroupId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
        this.password = password;
        this.token = token;
        this.status = status;
        this.userGroupId = userGroupId;
    }

    public User(Long userId, String firstName, String middleName, String lastName, String phone, String email,
                Long provinceId, Long districtId, Long wardId, String address, String password,
                String token, UserStatus status, Long userGroupId) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
        this.password = password;
        this.token = token;
        this.status = status;
        this.userGroupId = userGroupId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

}
