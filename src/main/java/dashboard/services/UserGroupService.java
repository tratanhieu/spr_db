package dashboard.services;

import dashboard.entities.user.UserGroup;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

public interface UserGroupService {

    ListEntityResponse<UserGroup> getAllWithPagination(Pageable pageable);

    UserGroup getOne(Long userGroupId) throws ResourceNotFoundException;

}
