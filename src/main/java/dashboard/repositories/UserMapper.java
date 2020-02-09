package dashboard.repositories;

import dashboard.dto.user.*;
import dashboard.entities.tag.Tag;
import dashboard.entities.user.User;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeature;
import dashboard.enums.EntityStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select(
        "SELECT " +
            "user.user_id AS userId, " +
            "CONCAT(user.first_name, ' ', user.middle_name, ' ', user.last_name) AS fullName, " +
            "user.phone, " +
            "user.email, " +
            "user_group.user_group_id AS userGroupId, " +
            "user_group.name AS userGroupName, " +
            "user.create_date AS createDate, " +
            "user.update_date AS updateDate, " +
            "user.status " +
        "FROM user " +
        "INNER JOIN user_group " +
        "ON user.user_group_id = user_group.user_group_id " +
        "ORDER BY user.create_date DESC"
    )
    List<UserDto> findAll();

    @Select(
        "SELECT user_group_id AS userGroupId, name AS name FROM user_group WHERE status = 'ACTIVE'"
    )
    List<UserGroupDto> findAllActiveUserGroup();

    @Select(
        "SELECT " +
            "user_id AS userId, " +
            "first_name AS firstName, " +
            "middle_name AS middleName, " +
            "last_name AS lastName, " +
            "phone, " +
            "email, " +
            "avatar, " +
            "province_id AS provinceId, " +
            "district_id AS districtId, " +
            "ward_id AS wardId, " +
            "address AS address, " +
            "user_group_id AS userGroupId, " +
            "status " +
        "FROM user " +
        "WHERE user_id = #{userId}"
    )
    Optional<UserDto> findById(@Param("userId") Long userId);

    @Select(
        "SELECT " +
            "user_id AS userId, " +
            "first_name AS firstName, " +
            "middle_name AS middleName, " +
            "last_name AS lastName, " +
            "phone, " +
            "email, " +
            "password, " +
            "user_group_id AS userGroupId, " +
            "status " +
        "FROM user " +
        "WHERE user_id = #{userId}"
    )
    Optional<User> findByUserId(@Param("userId") Long userId);

    @Select(
        "SELECT " +
            "user_id AS userId, " +
            "first_name AS firstName, " +
            "middle_name AS middleName, " +
            "last_name AS lastName, " +
            "phone, " +
            "email, " +
            "avatar, " +
            "password, " +
            "user_group_id AS userGroupId, " +
            "status " +
        "FROM user " +
        "WHERE phone = #{phone}"
    )
    Optional<User> findByPhone(@Param("phone") String phone);

    @Insert(
        "INSERT INTO user(" +
            "first_name, middle_name, last_name, " +
            "phone, email, " +
            "password, " +
            "user_group_id, " +
            "create_date, update_date, " +
            "status" +
        ") " +
        "VALUE(" +
            "#{firstName}, #{middleName}, #{lastName}, " +
            "#{phone}, #{email}, " +
            "#{password}, " +
            "#{userGroupId}, " +
            "NOW(), NOW(), " +
            "#{status}" +
        ")"
    )
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void save(UserForm userForm);

    @Update(
        "UPDATE user " +
        "SET " +
            "user_group_id = #{userGroupId}, " +
            "update_date = NOW(), " +
            "status = #{status} " +
        "WHERE user_id = #{userId}"
    )
    void updateById(UserForm userForm);

    @Update(
        "UPDATE user " +
        "SET " +
            "first_name = #{firstName}, " +
            "middle_name = #{middleName}, " +
            "last_name = #{lastName}, " +
            "email = #{email}, " +
            "avatar = #{avatar}, " +
            "province_id = #{provinceId}, " +
            "district_id = #{districtId}, " +
            "ward_id = #{wardId}, " +
            "address = #{address}, " +
            "update_date = NOW() " +
        "WHERE user_id = #{userId}"
    )
    void updateProfileByUserId(UserForm userForm);

    @Update(
        "UPDATE user " +
        "SET " +
            "password = #{password}, " +
            "update_date = NOW() " +
        "WHERE user_id = #{userId}"
    )
    void updatePasswordByUserId(@Param("userId") Long userId, @Param("password") String password);

    @Update(
        "UPDATE user " +
        "SET " +
            "token = NULL, " +
            "status = #{status}, " +
            "update_date = NOW() " +
        "WHERE user_id = #{userId}"
    )
    void activeUser(@Param("userId") Long userId, @Param("status") EntityStatus status);

    @Delete(
        "DELETE FROM user WHERE user_id = #{userId}"
    )
    void deleteById(@Param("userId") Long userId);
}
