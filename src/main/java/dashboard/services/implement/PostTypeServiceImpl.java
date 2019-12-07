package dashboard.services.implement;

import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.PostTypeRepository;
import dashboard.services.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Service
public class PostTypeServiceImpl implements PostTypeService {

    @Autowired
    PostTypeRepository postTypeRepository;

    @Override
    public ListEntityResponse<PostType> getAllWithPagination(Pageable pageable, String search, EntityStatus status) {
        Page<PostType> result = postTypeRepository.findWithPageable(pageable, search, status);

        ListEntityResponse<PostType> postTypeResponse = new ListEntityResponse<>();

        postTypeResponse.setPage(result.getNumber() +1);
        postTypeResponse.setPageSize(result.getSize());
        postTypeResponse.setTotalPage(result.getTotalPages());
        postTypeResponse.setListData(result.getContent());

        return postTypeResponse;
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