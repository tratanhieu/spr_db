package dashboard.services;

import dashboard.dto.user.UserGroupDto;
import dashboard.dto.user.UserGroupForm;
import dashboard.entities.user.UserFeature;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeature;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserGroupService {
    List getAll();

    UserGroupDto getOne(Long userGroupId) throws ResourceNotFoundException;

    void create(UserGroupForm userGroupForm);

    void update(UserGroupForm userGroupForm) throws ResourceNotFoundException;

    void delete(Long userGroupId);
}
