package dashboard.repositories;

import dashboard.entities.post.PostType;
import dashboard.entities.user.UserGroupFeature;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserGroupFeatureMapper {
    @Insert(
        "INSERT INTO user_group_feature(" +
            "user_group_id," +
            "user_feature_id, " +
            "create_permission, " +
            "update_permission, " +
            "read_permission, " +
            "delete_permission, " +
        ") VALUE(" +
            "#{userGroupId}, "+
            "#{userFeatureId}, " +
            "#{read}, " +
            "#{create}, " +
            "#{update}, " +
            "#{delete} " +
        ")"
    )
    void save(UserGroupFeature userGroupFeature);

    @Update(
            "UPDATE post_type " +
                    "SET " +
                    "name = #{name}, " +
                    "slug_name = #{slugName}, " +
                    "update_date = NOW(), " +
                    "status = #{status} " +
                    "WHERE post_type_id = #{postTypeId}"
    )
    void update(UserGroupFeature userGroupFeature);

    @Insert(
        "<script>" +
            "INSERT INTO user_group_feature(" +
                "user_group_id," +
                "user_feature_id, " +
                "read_permission, " +
                "create_permission, " +
                "update_permission, " +
                "delete_permission " +
            ") VALUES " +
            "<foreach item='userGroupFeature' index='index' collection='userGroupFeatureList' open='(' separator='),('  close=')'>" +
                "#{userGroupId}, " +
                "#{userGroupFeature.userFeatureId}, " +
                "#{userGroupFeature.read}, " +
                "#{userGroupFeature.create}, " +
                "#{userGroupFeature.update}, " +
                "#{userGroupFeature.delete} " +
            "</foreach>" +
            "ON DUPLICATE KEY UPDATE " +
                "read_permission = VALUES(read_permission), " +
                "create_permission = VALUES(create_permission), " +
                "update_permission = VALUES(update_permission), " +
                "delete_permission = VALUES(delete_permission)" +
        "</script>"
    )
    void saveAll(
        @Param("userGroupId") Long userGroupId,
        @Param("userGroupFeatureList") List<UserGroupFeature> userGroupFeatureList
    );

    @Delete(
        "DELETE FROM user_group_feature WHERE user_group_id = #{userGroupId}"
    )
    void deleteByUserGroupId(Long userGroupId);
}
