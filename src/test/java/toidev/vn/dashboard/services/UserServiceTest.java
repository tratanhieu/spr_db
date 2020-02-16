package toidev.vn.dashboard.services;

import dashboard.dto.user.UserDto;
import dashboard.entities.tag.Tag;
import dashboard.entities.user.User;
import dashboard.repositories.TagMapper;
import dashboard.repositories.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TagService.class)
public class UserServiceTest {
    @MockBean
    UserMapper userMapper;

    @Test
    public void getUserByPhone() {
        User user = userMapper.findByPhone("0123456789").orElse(null);
        System.out.println(user);
        if (user != null) {
            Assert.assertEquals("0123456789", user.getPhone());
        }
    }
}
