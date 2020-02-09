package dashboard.repositories;

import dashboard.dto.user.customer.CustomerDto;
import dashboard.dto.user.customer.CustomerForm;
import dashboard.dto.user.customer.RegisterForm;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface CustomerMapper {

    @Select(
        "SELECT " +
            "first_name AS firstName, " +
            "last_name AS lastName, " +
            "middle_name AS middleName, " +
            "dob, " +
            "email, " +
            "phone, " +
            "province AS provinceId, " +
            "district AS districtId, " +
            "ward AS wardId, " +
            "address, " +
            "status " +
        "FROM " +
            "customer " +
        "WHERE " +
            "user_id = #{userId}"
    )
    Optional<CustomerDto> getCustomerInfo(@Param("userId") Long userId);

    @Update(
        "UPDATE " +
            "customer " +
        "SET " +
            "first_name = #{firstName}, " +
            "last_name = #{lastName}, " +
            "middle_name = #{middleName}, " +
            "dob = #{dob}, " +
            "email = #{email}, " +
            "phone = #{phone}, " +
            "province = #{provinceId}, " +
            "district = #{districtId}, " +
            "ward = #{wardId}, " +
            "address = #{address} " +
        "WHERE " +
            "user_id = #{userId}"
    )
    void updateCustomerInfo(CustomerForm customerForm);

    @Select(
        "SELECT " +
            "password " +
        "FROM " +
            "customer " +
        "WHERE " +
            "userId = #{userId}"
    )
    String getCustomerPassword(@Param("userId")Long userId);

    @Update(
        "UPDATE " +
            "customer " +
        "SET " +
            "password = #{newPassword} " +
        "WHERE " +
            "userId = #{userId}")
    void changePassword(@Param("newPassword") String newPassword, @Param("userId") Long userId);

    @Insert("INSERT INTO customer(phone, password, status) VALUES(#{phone}, #{password}, 'INACTIVE' )")
    void registNewCustomer(RegisterForm registerForm);

    @Update("UPDATE customer SET status = 'ACTIVE' WHERE phone = #{phone}")
    void completeRegistCustomer(String phone);
}
