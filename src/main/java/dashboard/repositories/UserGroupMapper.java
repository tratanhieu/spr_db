package dashboard.repositories;

import dashboard.dto.user.UserGroupDto;
import dashboard.dto.user.UserGroupFeatureDto;
import dashboard.entities.tag.Tag;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeature;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserGroupMapper {
    @Select(
        "SELECT " +
            "user_group_id AS userGroupId, " +
            "name, " +
            "(SELECT COUNT(*) FROM user WHERE user_group_id = user_group.user_group_id) AS totalUser, " +
            "create_date AS createDate, " +
            "update_date AS updateDate, " +
            "status " +
        "FROM user_group " +
        "ORDER BY create_date DESC"
    )
    List<UserGroupDto> findAll();

    @Select(
        "SELECT " +
            "user_group_id, " +
            "name, " +
            "status " +
        "FROM user_group " +
        "WHERE user_group_id = #{userGroupId}"
    )
    @ResultType(UserGroup.class)
    @Results(value = {
        @Result(property = "userGroupId", column = "user_group_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "status", column = "status"),
        @Result(
            property = "userGroupFeatures",
            column = "user_group_id",
            javaType = List.class,
            many = @Many(select = "findUserGroupFeatures")
        )
    })
    Optional<UserGroupDto> findById(@Param("userGroupId") Long userGroupId);

    @Select(
        "SELECT " +
            "user_feature_id, " +
            "read_permission, " +
            "create_permission, " +
            "update_permission, " +
            "delete_permission " +
        "FROM user_group_feature " +
        "WHERE user_group_id = #{userGroupId}"
    )
    @Results(id = "findUserGroupFeatures", value = {
        @Result(property = "featureId", column = "user_feature_id"),
        @Result(property = "read", column = "read_permission"),
        @Result(property = "create", column = "create_permission"),
        @Result(property = "update", column = "update_permission"),
        @Result(property = "delete", column = "delete_permission")
    })
    List<UserGroupFeatureDto> findUserGroupFeatures(@Param("userGroupId") Long userGroupId);

    @Insert(
        "INSERT INTO user_group(name, create_date, update_date, status) " +
        "VALUE(#{name}, NOW(), NOW(), #{status})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "userGroupId")
    void save(UserGroup userGroup);

    @Update(
        "UPDATE user_group " +
        "SET " +
            "name = #{name}, " +
            "update_date = NOW(), " +
            "status = #{status} " +
        "WHERE user_group_id = #{userGroupId}"
    )
    void updateById(UserGroup userGroup);

    @Delete(
        "DELETE FROM user_group WHERE user_group_id = #{userGroupId}"
    )
    void deleteById(Long userGroupId);
}
