package dashboard.repositories;

import dashboard.dto.user.customer.CustomerDto;
import dashboard.dto.user.customer.CustomerForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
        "UPDATE customer SET " +
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
}
