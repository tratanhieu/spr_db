package dashboard.repositories;

import dashboard.dto.user.customer.CustomerDto;
import dashboard.dto.user.customer.CustomerForm;
import dashboard.dto.user.customer.CustomerOTPDto;
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
            "province_id AS provinceId, " +
            "district_id AS districtId, " +
            "ward_id AS wardId, " +
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
            "province_id = #{provinceId}, " +
            "district_id = #{districtId}, " +
            "ward_id = #{wardId}, " +
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
            "user_id = #{userId}"
    )
    String getCustomerPassword(@Param("userId")Long userId);

    @Update(
        "UPDATE " +
            "customer " +
        "SET " +
            "password = #{newPassword} " +
        "WHERE " +
            "user_id = #{userId}")
    void changePassword(@Param("newPassword") String newPassword, @Param("userId") Long userId);

    @Insert("INSERT INTO customer(phone, password, status) VALUES(#{phone}, #{password}, 'INACTIVE' )")
    void registNewCustomer(RegisterForm registerForm);

    @Insert("INSERT INTO otp_store(phone, otp_code) VALUES(#{phone}, #{otpCode}")
    void addOTP(String phone, String otpCode);

    @Select("SELECT phone, otp_code FROM otp_store WHERE phone = #{phone}")
    CustomerOTPDto getOTP(String phone);

    @Update("UPDATE customer SET status = 'ACTIVE' WHERE phone = #{phone}")
    void completeRegistCustomer(String phone);

    @Delete("DELETE FROM otp_store WHERE phone = #{phone}")
    void deleteOTP(String phone);

    @Select(
            "SELECT phone FROM customer WHERE phone = #{phone}"
    )
    String getCustomerPhoneByPhone(String phone);

    @Update(
            "UPDATE " +
                    "customer " +
                    "SET " +
                    "password = #{newPassword} " +
                    "WHERE " +
                    "phone = #{phone}")
    void changePassword(@Param("newPassword") String newPassword, @Param("phone") String phone);
}
