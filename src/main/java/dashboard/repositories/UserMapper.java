package dashboard.repositories;

import dashboard.dto.user.UserDto;
import dashboard.dto.user.UserForm;
import dashboard.dto.user.UserGroupDto;
import dashboard.dto.user.UserGroupFeatureDto;
import dashboard.entities.tag.Tag;
import dashboard.entities.user.User;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeature;
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

    @Delete(
        "DELETE FROM user WHERE user_id = #{userId}"
    )
    void deleteById(@Param("userId") Long userId);
}
