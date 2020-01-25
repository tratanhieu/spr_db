package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.post.FormPostType;
import dashboard.entities.post.Post;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.PostTypeMapper;
import dashboard.repositories.PostTypeRepository;
import dashboard.services.PostTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.Valid;
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
    public List create(FormPostType formPostType) {
        if (formPostType.getPostTypeId() != null) {
            throw new ValidationException("Post type id not null");
        }
        if (formPostType.getSlugName() == null) {
            formPostType.setSlugName(DataUtils.makeSlug(formPostType.getName()));
        }
        PostType postType = new PostType(formPostType);
        postType.setCreateDate(new Date());
        postTypeMapper.save(postType);
        return this.getAll();
    }

    @Override
    public List update(FormPostType formPostType) throws ResourceNotFoundException {
        PostType postType = new PostType(formPostType);
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