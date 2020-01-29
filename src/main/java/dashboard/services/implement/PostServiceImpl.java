package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.FileIOUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.post.PostForm;
import dashboard.dto.post.PostTypeDto;
import dashboard.entities.post.Post;
import dashboard.entities.tag.Tag;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.*;
import dashboard.services.PostService;
import dashboard.services.PostTypeService;
import dashboard.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostTypeRepository postTypeRepository;

    @Autowired
    TagService tagService;

    @Autowired
    PostTypeService postTypeService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostMapper postMapper;

    @Autowired
    PostTypeMapper postTypeMapper;

    @Autowired
    TagMapper tagMapper;

    @Override
    public List getAll() {
        return postMapper.findAll();
    }

    @Override
    public Post getOne(Long postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Map getCreate() {
        List<PostTypeDto> postTypes = postTypeMapper.findAllActivePostTypeForSelect();
        List<Tag> tags = tagMapper.findAllTag();
        Map<String, Object> map = new HashMap<>();
        map.put("postTypeList", postTypes);
        map.put("tagList", tags);
        return map;
    }

    @Override
    @Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = {Exception.class}
    )
    public List create(PostForm postForm) {
        FileIOUtils fileIOUtils = new FileIOUtils();
        try {
//            Post post = new Post(formPost);
            if (postForm.getSlugName() == null) {
                postForm.setSlugName(DataUtils.makeSlug(postForm.getName()));
            }
            Map mapUpload = fileIOUtils.createImageViaBase64Encode(postForm.getImage(), postForm.getSlugName());
            postForm.setImage((String) mapUpload.get(FileIOUtils.PATH));
            // Create post content
            String content = postForm.getContent();
            content = fileIOUtils.prepareContentPost(content, postForm.getSlugName());
            postForm.setContent(content);
            postForm.setUserId(1L);
            // Re - validate
            ValidationUtils.validate(postForm);
            // Save
            postMapper.save(postForm);
            tagService.createPostTags(postForm.getPostId(), postForm.getTags());
        } catch (Exception ex) {
            ex.printStackTrace();
            fileIOUtils.rollBackUploadedImages();
        }
        return getAll();
    }

    @Override
    public List update(PostForm postForm) {
//
//        // update post
//        postRepository.save(post);
//
//        boolean  indexOfTag;
//        String tagName;
//        String slugName;
//        PostTagIdentity postTagIdentity;
//        Tag tag;
//        List<PostTag> postTagDelete = new ArrayList<>();
//        List<String> tags = Arrays.asList(post.getTags());
//        List<PostTag> postTags = new ArrayList<>();
//        Iterator<Tag> rstTag = postTagRepository.getAllByPostId(post.getPostId()).iterator();
//        int i;
//        int length = tags.size();
//
//        //filter new and old tag
//        while (rstTag.hasNext()) {
//            tagName = rstTag.next().getName();
//            indexOfTag = tags.contains(tagName);
//            if (indexOfTag ) {
//                rstTag.remove();
//                tags.remove(tags.indexOf(tagName));
//            }
//        }
//
//        // set value for list postTags
//        for(i = 0; i < length; i++){
//
//            //create tag
//            slugName = DataUtils.makeSlug(tags.get(i));
//            tag = new Tag(slugName,tags.get(i));
//            tagRepository.save(tag);
//
//            //create postTag
//            postTagIdentity = new PostTagIdentity(post, tag);
//            PostTag postTag = new PostTag();
//            postTag.setPostTagIdentity(postTagIdentity);
//
//            //set value for list postTags
//            postTags.add(postTag);
//        }
//
//        // Insert new postTag
//        postTagRepository.saveAll(postTags);
//
//        while (rstTag.hasNext()){
//
//            postTagIdentity = new PostTagIdentity(post, rstTag.next());
//            PostTag postT  = new PostTag();
//            postT.setPostTagIdentity(postTagIdentity);
//
//            postTagDelete.add(postT);
//
//        }
//
//        postTagRepository.deleteAll(postTagDelete);
//
//
//
        return getAll();
    }

    @Override
    public List delete(Long postId) throws ResourceNotFoundException {
//
//        Post post = postRepository.findById(postId).orElse(null);
//
//        if (post == null) {
//            throw new ResourceNotFoundException();
//        }
//
//        post.setDeleteDate(new Date());
//        post.setStatus(EntityStatus.DELETED);
//        postRepository.save(post);
        return getAll();
    }

    @Override
    public List updateStatusWithMultipleId(List<Long> postId, EntityStatus status) throws ResourceNotFoundException {
//        int result =  postRepository.updateStatusByListId(postId, status);
//        return result;
        return getAll();
    }
}
