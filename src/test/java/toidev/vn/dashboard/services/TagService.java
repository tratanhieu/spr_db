package toidev.vn.dashboard.services;

import dashboard.entities.tag.Tag;
import dashboard.repositories.TagMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TagService.class)
public class TagService {
    @MockBean
    TagMapper tagMapper;

    @Autowired
    private TagService tagService;

    @Test
    public void testInsertMultiple() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("anh-ne", "anh nè"));
        tags.add(new Tag("em-ne", "em nè"));
        tagMapper.saveAll(tags);
        tagMapper.saveAll(tags);
//        Assert.assertEquals(void, );
    }

}