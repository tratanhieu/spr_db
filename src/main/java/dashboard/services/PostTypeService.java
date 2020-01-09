package dashboard.services;

import dashboard.dto.post.FormPostType;
import dashboard.dto.post.PostTypeDto;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostTypeService {

    List getAll();

    PostType getOne(Long postTypeId) throws ResourceNotFoundException;

    List create(FormPostType formPostType);

    List update(FormPostType formPostType) throws ResourceNotFoundException;;

    List delete(Long postTypeId);

    List updateStatusWithMultipleId(List<Long> ListId, EntityStatus status);
}