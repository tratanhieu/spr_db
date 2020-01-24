package dashboard.repositories;

import dashboard.dto.post.PostTypeDto;
import dashboard.entities.tag.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("SELECT tag.name, tag.slug_name AS slugName FROM tag")
    List<Tag> findAllTag();
}
