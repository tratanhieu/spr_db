package dashboard.services;

import dashboard.dto.user.UserDto;
import dashboard.dto.user.UserForm;
import dashboard.entities.user.User;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    List getAll();

    Map<String, Object> getCreate();

    UserDto getOne(Long userId) throws ResourceNotFoundException;

    void create(UserForm userForm);

    void update(UserForm userForm);

    void updateProfile(UserForm userForm);

    void updatePassword(Long userId, String password);

    void active(Long userId, EntityStatus status);

    void delete(Long userId);
}
