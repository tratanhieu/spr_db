package dashboard.services.implement;

import dashboard.dto.user.UserDto;
import dashboard.dto.user.UserForm;
import dashboard.entities.user.User;
import dashboard.enums.EntityStatus;
import dashboard.enums.UserStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.UserGroupMapper;
import dashboard.repositories.UserMapper;
import dashboard.repositories.UserRepository;
import dashboard.services.ProvinceService;
import dashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProvinceService provinceService;

    @Override
    public List getAll() {
        return userMapper.findAll();
    }

    @Override
    public Map<String, Object> getCreate() {
        Map<String, Object> map = new HashMap<>();
        map.put("userGroupList", userMapper.findAllActiveUserGroup());
        return map;
    }

    @Override
    public UserDto getOne(Long userId) throws ResourceNotFoundException {
        return userMapper.findById(userId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Map getUserProfile(Long userId) throws ResourceNotFoundException {
        UserDto user = userMapper.findById(userId).orElseThrow(ResourceNotFoundException::new);
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("provinceList", provinceService.listProvince());
        return map;
    }

    @Override
    public void create(UserForm userForm) {
        String encodedPassword = passwordEncoder.encode("123");
        userForm.setPassword(encodedPassword);
        userMapper.save(userForm);
    }

    @Override
    public void update(UserForm userForm) {
        userMapper.updateById(userForm);
    }

    @Override
    public void updateProfile(UserForm userForm) {
        userMapper.updateById(userForm);
    }

    @Override
    public void updatePassword(Long userId, String password) {

    }

    @Override
    public void active(Long userId, EntityStatus status) {
        userMapper.activeUser(userId, status);
    }

    @Override
    public void delete(Long userId) {
        userMapper.deleteById(userId);
    }
}
