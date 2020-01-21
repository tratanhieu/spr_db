package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.commons.FileIOUtils;
import dashboard.commons.ValidationUtils;
import dashboard.dto.post.FormPost;
import dashboard.entities.embedded.PostTagIdentity;
import dashboard.entities.post.Post;
import dashboard.entities.post.PostTag;
import dashboard.entities.post.PostType;
import dashboard.entities.tag.Tag;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.PostRepository;
import dashboard.repositories.PostTagRepository;
import dashboard.repositories.TagRepository;
import dashboard.services.PostService;
import dashboard.services.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.xml.crypto.Data;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostServiceImpl implements PostService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Override
    public List getAll() {
        String sqlQueryPost = "SELECT " +
                "p.post_id, " +
                "p.name, " +
                "p.slug_name, " +
                "p.content, " +
                "p.description, " +
                "p.image, " +
                "p.create_date, " +
                "p.update_date, " +
                "p.delete_date, " +
                "p.publish_date, " +
                "p.status, " +
                "pt.name AS postTypeName, " +
                "CONCAT(u.first_name, ' ', u.middle_name, ' ', u.last_name) AS author, " +
                "(SELECT GROUP_CONCAT(t.slug_name, '#', t.name) FROM post_tag pt INNER JOIN tag t ON pt.slug_name = t.slug_name WHERE pt.post_id = p.post_id) AS tags " +
                "FROM post p " +
                "INNER JOIN user u ON p.user_id = u.user_id " +
                "INNER JOIN post_type pt ON p.post_type_id = pt.post_type_id " +
                "WHERE p.status <> 'DELETE' " +
                "ORDER BY p.create_date DESC";
        return em.createNativeQuery(sqlQueryPost, "listPostMapping").getResultList();
    }

    @Override
    public Post getOne(Long postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public List create(FormPost formPost) {
        FileIOUtils fileIOUtils = new FileIOUtils();
        try {
            Post post = new Post(formPost);
            if (post.getSlugName() == null) {
                post.setSlugName(DataUtils.makeSlug(post.getName()));
            }
            Map mapUpload = fileIOUtils.createImageViaBase64Encode(post.getImage(), post.getSlugName());
            post.setImage((String) mapUpload.get(FileIOUtils.PATH));
            // Create post content
            String content = formPost.getContent();
            content = fileIOUtils.prepareContentPost(content, post.getSlugName());
            post.setContent(content);
            // Re - validate
            ValidationUtils.validate(post);
            postRepository.save(post);
            tagService.createPostTags(post.getPostId(), formPost.getTags());
        } catch (Exception ex) {
            ex.printStackTrace();
            fileIOUtils.rollBackUploadedImages();
        } finally {
            em.close();
        }
        return getAll();
    }

    @Override
    public List update(FormPost formPost) {
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
