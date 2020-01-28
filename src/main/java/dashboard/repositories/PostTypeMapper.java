package dashboard.repositories;

import dashboard.dto.post.PostTypeDto;
import dashboard.entities.post.Post;
import dashboard.entities.post.PostType;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface PostTypeMapper {
    @Select(
        "SELECT " +
            "post_type_id AS postTypeId, " +
            "name, " +
            "slug_name AS slugName, " +
            "(SELECT COUNT(p.post_id) " +
                "FROM post p " +
                "WHERE p.post_type_id = pt.post_type_id" +
            ") AS totalPost, " +
            "create_date AS createDate, " +
            "update_date AS updateDate, " +
            "status " +
        "FROM post_type pt " +
        "WHERE pt.status <> 'DELETE' " +
        "ORDER BY pt.create_date DESC"
    )
    List<PostTypeDto> findAllActivePostType();

    @Select(
        "SELECT " +
            "post_type_id AS postTypeId, " +
            "name, " +
            "slug_name AS slugName " +
        "FROM post_type " +
        "WHERE status = 'ACTIVE'"
    )
    List<PostTypeDto> findAllActivePostTypeForSelect();

    @Select(
        "SELECT " +
            "post_type_id AS postTypeId, " +
            "name, " +
            "slug_name AS slugName, " +
            "create_date AS createDate, " +
            "update_date AS updateDate, " +
            "status " +
        "FROM post_type " +
        "WHERE post_type_id = #{postTypeId}"
    )
    Optional<PostType> findById(@Param("postTypeId") Long postTypeId);

    @Insert(
        "INSERT INTO post_type (name, slug_name, create_date, update_date, status) " +
        "VALUE(" +
            "#{name}, " +
            "#{slugName}, " +
            "#{createDate}, " +
            "#{updateDate}, " +
            "#{status}" +
        ")"
    )
    @SelectKey(
        statement = "SELECT LAST_INSERT_ID() AS postTypeId",
        keyProperty = "postTypeId",
        before = true,
        resultType = Long.class
    )
    Long save(PostType postType);

    @Update(
        "UPDATE post_type " +
        "SET " +
            "name = #{name}, " +
            "slug_name = #{slugName}, " +
            "update_date = NOW(), " +
            "status = #{status} " +
        "WHERE post_type_id = #{postTypeId}"
    )
    void update(PostType postType);

    @Insert(
        "<script>" +
        "INSERT ALL " +
        "<foreach item='postType' index='index' collection='postTypeList'>" +
            "INTO post_type(name, slug_name, create_date, update_date, status) " +
            "VALUES (#{postType.name}, #{postType.slugName}, #{postType.createDate}, #{postType.updateDate}, #{postType.status})" +
        "</foreach>" +
        "</script>"
    )
    void saveAll(@Param("postTypeList") List<PostType> postTypeList);

    @Select(
        "DELETE FROM post_type WHERE post_type_id = #{postTypeId}"
    )
    void deleteById(@Param("postTypeId") Long postTypeId);
}
