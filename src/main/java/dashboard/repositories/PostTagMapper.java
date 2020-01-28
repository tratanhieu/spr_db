package dashboard.repositories;

import dashboard.dto.post.PostTypeDto;
import dashboard.entities.post.PostType;
import dashboard.entities.tag.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostTagMapper {
    @Insert(
        "INSERT INTO post_tag (post_id, slug_name) " +
        "VALUE(" +
            "#{postId}, " +
            "#{slugName}" +
        ")"
    )
    Long save(PostType postType);

    @Insert(
        "<script>" +
            "INSERT INTO post_tag (post_id, slug_name) VALUES " +
            "<foreach item='tag' index='index' collection='tagList' open='(' separator='),('  close=')'>" +
            "#{postId}, #{tag.slugName}" +
            "</foreach>" +
            "ON DUPLICATE KEY UPDATE slug_name = VALUES(slug_name)" +
        "</script>"
    )
    void saveAll(@Param("postId") Long postId, @Param("tagList") List<Tag> tagList);

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

    @Select(
        "DELETE FROM post_type WHERE post_type_id = #{postTypeId}"
    )
    void deleteById(@Param("postTypeId") Long postTypeId);
}
