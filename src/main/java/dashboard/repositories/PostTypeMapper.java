package dashboard.repositories;

import dashboard.dto.post.PostTypeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostTypeMapper {
    @Select("SELECT name, slug_name AS slugName FROM post_type WHERE status = 'ACTIVE'")
    List<PostTypeDto> findAllActivePostType();
}
