package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.dto.post.PostTypeForm;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.PostTypeMapper;
import dashboard.repositories.PostTypeRepository;
import dashboard.services.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@Service
public class PostTypeServiceImpl implements PostTypeService {

    @Autowired
    private PostTypeRepository postTypeRepository;

    @Autowired
    private PostTypeMapper postTypeMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List getAll() {
        return postTypeMapper.findAllActivePostType();
    }

    @Override
    public PostType getOne(Long postTypeId) throws ResourceNotFoundException {
        return postTypeMapper.findById(postTypeId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List create(PostTypeForm postTypeForm) {
        if (postTypeForm.getPostTypeId() != null) {
            throw new ValidationException("Post type id not null");
        }
        if (postTypeForm.getSlugName() == null) {
            postTypeForm.setSlugName(DataUtils.makeSlug(postTypeForm.getName()));
        }
        PostType postType = new PostType(postTypeForm);
        postType.setCreateDate(new Date());
        postTypeMapper.save(postType);
        return this.getAll();
    }

    @Override
    public List update(PostTypeForm postTypeForm) throws ResourceNotFoundException {
        PostType postType = new PostType(postTypeForm);
        postTypeMapper.update(postType);
        return this.getAll();
    }

    @Override
    public List delete(Long postTypeId) {
        postTypeMapper.deleteById(postTypeId);
        return this.getAll();
    }

    @Override
    public List updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) {
        postTypeRepository.updateStatusByListId(ListId, status);
        return this.getAll();
    }
}