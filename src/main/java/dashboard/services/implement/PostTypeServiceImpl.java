package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.post.FormPostType;
import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.PostTypeRepository;
import dashboard.services.PostTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Service
public class PostTypeServiceImpl implements PostTypeService {

    @Autowired
    PostTypeRepository postTypeRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public List getAll() {
        String sqlQuery = "SELECT " +
                "pt.*, " +
                "(SELECT " +
                    "COUNT(p.post_id) " +
                "FROM post p " +
                    "WHERE p.post_type_id = pt.post_type_id) AS totalPost " +
                "FROM post_type pt " +
                "WHERE pt.status <> 'DELETE' " +
                "ORDER BY pt.create_date DESC";
        return em.createNativeQuery(sqlQuery, "listPostTypeMapping").getResultList();
    }

    @Override
    public PostType getOne(Long postTypeId) throws ResourceNotFoundException {
        return postTypeRepository.findById(postTypeId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List create(FormPostType formPostType) {
        if (formPostType.getSlugName() == null) {
            formPostType.setSlugName(DataUtils.makeSlug(formPostType.getName()));
        }
        PostType postType = new PostType();
        BeanUtils.copyProperties(formPostType, postType);
        postTypeRepository.save(postType);
        return this.getAll();
    }

    @Override
    public List update(FormPostType formPostType) throws ResourceNotFoundException {
        PostType postType = postTypeRepository.findById(formPostType.getPostTypeId())
                .orElseThrow(ResourceNotFoundException::new);
        BeanUtils.copyProperties(formPostType, postType);
        postTypeRepository.save(postType);
        return this.getAll();
    }

    @Override
    public List delete(Long postTypeId) {
        postTypeRepository.deleteById(postTypeId);
        return this.getAll();
    }

    @Override
    public List updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) {
        postTypeRepository.updateStatusByListId(ListId, status);
        return this.getAll();
    }
}