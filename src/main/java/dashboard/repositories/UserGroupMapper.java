package dashboard.repositories;

import dashboard.entities.tag.Tag;
import dashboard.entities.user.UserGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserGroupMapper {
    @Select(
        "SELECT " +
            "user_group_id AS userGroupId, " +
            "name, " +
            "create_date AS createDate, " +
            "update_date AS updateDate, " +
            "status " +
        "FROM user_group " +
        "ORDER BY create_date DESC"
    )
    List<UserGroup> findAll();

    @Select(
        "SELECT " +
        "user_group_id AS userGroupId, " +
        "name, " +
        "status " +
        "FROM user_group " +
        "WHERE user_group_id = #{userGroupId}"
    )
    Optional<UserGroup> findById(@Param("userGroupId") Long userGroupId);

    @Update(
        "UPDATE user_group " +
        "SET " +
            "name = #{name}, " +
            "status = #{status} " +
        "WHERE user_group_id = #{userGroupId}"
    )
    void updateById(UserGroup userGroup);

    @Update(
        "DELETE FROM user_group WHERE user_group_id = #{userGroupId}"
    )
    void deleteById(UserGroup userGroup);

}
