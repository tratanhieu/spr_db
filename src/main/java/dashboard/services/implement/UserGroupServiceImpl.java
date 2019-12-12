package dashboard.services.implement;

import dashboard.entities.user.UserGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.UserGroupRepository;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Override
    public ListEntityResponse<UserGroup> getAllWithPagination(Pageable pageable) {
        Page<UserGroup> result = userGroupRepository.findWithPageable(pageable);

        ListEntityResponse<UserGroup> userGroupResponse = new ListEntityResponse<>();

        userGroupResponse.setPage(result.getNumber() + 1);
        userGroupResponse.setPageSize(result.getSize());
        userGroupResponse.setTotalPage(result.getTotalPages());
        userGroupResponse.setListData(result.getContent());

        return userGroupResponse;
    }

    @Override
    public UserGroup getOne(Long userGroupId) throws ResourceNotFoundException {
        UserGroup userGroup = userGroupRepository.findById(userGroupId).orElse(null);

        if (userGroup == null) {
            throw new ResourceNotFoundException();
        }
        return userGroup;
    }

    @Override
    public int create(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
        return 1;
    }

    @Override
    public int update(UserGroup userGroup) throws ResourceNotFoundException {
        UserGroup userGroupId = userGroupRepository.findById(userGroup.getUserGroupId()).orElse(null);

        if (userGroupId == null) {
            throw new ResourceNotFoundException();
        }

        userGroupRepository.save(userGroup);

        return 1;
    }

    @Override
    public int delete(Long userGroupId) throws ResourceNotFoundException {
        UserGroup userGroupIdToDelete = userGroupRepository.findById(userGroupId).orElse(null);

        if (userGroupIdToDelete == null) {
            throw new ResourceNotFoundException();
        }

        userGroupIdToDelete.setStatus(EntityStatus.DELETED);
        userGroupIdToDelete.setDeleteDate(new Date());
        userGroupRepository.save(userGroupIdToDelete);

        return 1;
    }
}
