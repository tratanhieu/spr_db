package dashboard.services;

import dashboard.entities.user.UserGroup;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserGroupService {

    List getAll();

    UserGroup getOne(Long userGroupId) throws ResourceNotFoundException;

    void create(UserGroup userGroup);

    void update(UserGroup userGroup) throws ResourceNotFoundException;

    int delete(Long userGroupId) throws ResourceNotFoundException;

}
