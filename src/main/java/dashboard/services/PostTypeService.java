package dashboard.services;

import dashboard.dto.post.PostTypeForm;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.util.List;

public interface PostTypeService {

    List getAll();

    PostType getOne(Long postTypeId) throws ResourceNotFoundException;

    List create(PostTypeForm postTypeForm);

    List update(PostTypeForm postTypeForm) throws ResourceNotFoundException;;

    List delete(Long postTypeId);

    List updateStatusWithMultipleId(List<Long> ListId, EntityStatus status);
}