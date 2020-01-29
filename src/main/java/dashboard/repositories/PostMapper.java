package dashboard.repositories;

import dashboard.dto.post.PostForm;
import dashboard.dto.post.PostDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select(
        "SELECT " +
            "p.post_id AS postId, " +
            "p.name, " +
            "p.slug_name AS slugName, " +
            "p.content, " +
            "p.description, " +
            "p.image, " +
            "p.create_date AS createDate, " +
            "p.update_date AS updateDate, " +
            "p.publish_date AS publishDate, " +
            "p.status, " +
            "pt.name AS postTypeName, " +
            "CONCAT(u.first_name, ' ', u.middle_name, ' ', u.last_name) AS author " +
        "FROM post p " +
        "INNER JOIN user u ON p.user_id = u.user_id " +
        "INNER JOIN post_type pt ON p.post_type_id = pt.post_type_id " +
        "WHERE p.status <> 'DELETE' " +
        "ORDER BY p.create_date DESC"
    )
    List<PostDto> findAll();

    @Insert(
        "INSERT INTO post (" +
            "name, " +
            "slug_name, " +
            "image, " +
            "description, " +
            "content, " +
            "post_type_id, " +
            "user_id, " +
            "publish_date, " +
            "create_date, " +
            "update_date, " +
            "status" +
        ") " +
            "VALUE(" +
            "#{name}, " +
            "#{slugName}, " +
            "#{image}, " +
            "#{description}, " +
            "#{content}, " +
            "#{postTypeId}, " +
            "#{userId}, " +
            "#{publishDate}, " +
            "NOW(), " +
            "NOW(), " +
            "#{status}" +
        ")"
    )
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    void save(PostForm postForm);
}
