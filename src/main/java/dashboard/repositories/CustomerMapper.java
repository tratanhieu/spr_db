package dashboard.repositories;

import dashboard.dto.user.customer.CustomerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    CustomerDto getCustomerInfo(@Param("userId") Long userId);
}
