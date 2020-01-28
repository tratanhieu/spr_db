package dashboard.repositories;

import dashboard.dto.post.PostTypeDto;
import dashboard.entities.post.PostType;
import dashboard.entities.tag.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("SELECT tag.name, tag.slug_name AS slugName FROM tag")
    List<Tag> findAllTag();

    @Insert(
        "<script>" +
            "INSERT INTO tag(slug_name, name) VALUES " +
            "<foreach item='tag' index='index' collection='tagList' open='(' separator='),('  close=')'>" +
                "#{tag.slugName}, #{tag.name}" +
            "</foreach>" +
            "ON DUPLICATE KEY UPDATE name = VALUES(name)" +
        "</script>"
    )
    void saveAll(@Param("tagList") List<Tag> tagList);

}
