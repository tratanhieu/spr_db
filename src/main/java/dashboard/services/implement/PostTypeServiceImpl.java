package dashboard.services.implement;

import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.PostTypeRepository;
import dashboard.services.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
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
                "WHERE pt.status <> 'DELETE'";
        return em.createNativeQuery(sqlQuery, "listPostTypeMapping").getResultList();
    }

    @Override
    public PostType getOne(Long postTypeId) throws ResourceNotFoundException {
        PostType postType = postTypeRepository.findById(postTypeId).orElse(null);

        if (postType == null) {
            throw new ResourceNotFoundException();
        }

        return postType;
    }

    @Override
    public int create(@Valid PostType postType) {
        postTypeRepository.save(postType);
        return 1;
    }

    @Override
    public int update(PostType postType) {
        postTypeRepository.save(postType);
        return 1;
    }

    @Override
    public int delete(Long postTypeId) throws ResourceNotFoundException {

        PostType postType = postTypeRepository.findById(postTypeId).orElse(null);

        if (postType == null) {
            throw  new ResourceNotFoundException();
        }

        postType.setDeleteDate(new Date());
        postType.setStatus(EntityStatus.DELETED);
        postTypeRepository.save(postType);

        return 1;
    }

    @Override
    public int updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) throws ResourceNotFoundException {
        int res = postTypeRepository.updateStatusByListId(ListId, status);
        return res;
    }
}