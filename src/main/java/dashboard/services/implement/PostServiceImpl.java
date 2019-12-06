package dashboard.services.implement;

import dashboard.entities.post.Post;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.PostRepository;
import dashboard.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public ListEntityResponse<Post> getAllWithPagination(Pageable pageable) {
        try {
            Page<Post> result = postRepository.findWithPageable(pageable);

            ListEntityResponse<Post> postRespone = new ListEntityResponse<>();

            postRespone.setPage(result.getNumber() + 1);
            postRespone.setPageSize(result.getSize());
            postRespone.setTotalPage(result.getTotalPages());
            postRespone.setListData(result.getContent());

            return postRespone;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
return null;

    }

    @Override
    public Post getOne(Long postId) throws ResourceNotFoundException {

        Post postRespone = postRepository.findById(postId).orElse(null);

        if (postRespone == null) {
            throw new ResourceNotFoundException();
        }

        return postRespone;
    }

    @Override
    public void create(Post post) {

        postRepository.save(post);
    }

    @Override
    public void update(Post post) {

        postRepository.save(post);
    }

    @Override
    public void delete(Long postId) throws ResourceNotFoundException {

        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException();
        }

        post.setDeleteDate(new Date());
        post.setStatus(EntityStatus.DELETED);
        postRepository.save(post);
    }

    @Override
    public int updateStatusWithMultipleId(List<Long> postId, EntityStatus status) throws ResourceNotFoundException {
        int result =  postRepository.updateStatusByListId(postId, status);
        return result;
    }
}
