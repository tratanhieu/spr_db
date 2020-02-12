package dashboard.services.implement;

import dashboard.commons.FileIOUtils;
import dashboard.dto.user.UserDto;
import dashboard.dto.user.UserForm;
import dashboard.entities.user.User;
import dashboard.enums.EntityStatus;
import dashboard.enums.UserStatus;
import dashboard.exceptions.customs.InvalidException;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

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
    public void create(UserForm userForm) {
        String encodedPassword = passwordEncoder.encode("123");
        userForm.setPassword(encodedPassword);
        userMapper.save(userForm);
    }

    @Override
    public Map getUserProfile(Long userId) throws ResourceNotFoundException, IOException {
        UserDto user = userMapper.findById(userId).orElseThrow(ResourceNotFoundException::new);
        this.checkUserStatus(user.getStatus());
        Map<String, Object> map = new HashMap<>();
        map.put("userProfile", user);
        map.put("provinceList", provinceService.listProvince());
        map.put("districtList", provinceService.listDistrict(user.getProvinceId()));
        map.put("wardList", provinceService.listWard(
            user.getProvinceId(),
            user.getDistrictId()
        ));
        return map;
    }

    @Override
    public void update(UserForm userForm) {
        userMapper.updateById(userForm);
    }

    @Override
    public void updateProfile(UserForm userForm) throws ResourceNotFoundException, IOException {
        UserDto user =  getOne(userForm.getUserId());
        FileIOUtils fileIOUtils = new FileIOUtils();
        String avatarPath = "";
        this.checkUserStatus(user.getStatus());
        if (!userForm.getAvatar().contains("http")) {
            avatarPath = fileIOUtils.createImageViaBase64EncodeWithoutSystemPath(
                userForm.getAvatar(),user.getPhone() + "-" + UUID.randomUUID()
            );
            userForm.setAvatar(avatarPath);
        }
        userMapper.updateProfileByUserId(userForm);
        if (!StringUtils.isEmpty(avatarPath)) {
            fileIOUtils.removeImageFromURL(user.getAvatar());
        }
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String password)
            throws ValidationException, ResourceNotFoundException{
        User user = userMapper.selectPassword(userId).orElseThrow(ResourceNotFoundException::new);
        // Check match old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ValidationException("oldPassword", "Old password not match");
        }
        this.checkUserStatus(user.getStatus());
        userMapper.updatePasswordByUserId(userId, passwordEncoder.encode(password));
    }

    @Override
    public void active(Long userId, EntityStatus status) {
        userMapper.activeUser(userId, status);
    }

    @Override
    public void delete(Long userId) {
        userMapper.deleteById(userId);
    }

    private void checkUserStatus(UserStatus status) throws InvalidException{
        if (!status.equals(UserStatus.ACTIVE)) {
            throw new InvalidException("User not valid");
        }
    }
}
