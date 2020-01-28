package dashboard.services.implement;

import dashboard.entities.embedded.UserGroupFeaturesIdentity;
import dashboard.entities.user.UserFeatures;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeatures;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.UserGroupFeaturesRepository;
import dashboard.repositories.UserGroupMapper;
import dashboard.repositories.UserGroupRepository;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserGroupMapper userGroupMapper;

    @Autowired
    UserGroupFeaturesRepository userGroupFeaturesRepository;

    @Override
    public List getAll() {
        return userGroupMapper.findAll();
    }

    @Override
    public UserGroup getOne(Long userGroupId) throws ResourceNotFoundException {
        return userGroupMapper.findById(userGroupId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void create(UserGroup userGroupParams) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(userGroupParams.getName());
        userGroup.setStatus(userGroupParams.getStatus());
        userGroupRepository.save(userGroup);
        List<UserGroupFeatures> userGroupFeatureList = new ArrayList<>();

        Set<UserGroupFeatures> userGroupFeatures = userGroupParams.getUserGroupFeatures();
        UserGroupFeaturesIdentity userGroupFeaturesIdentity;
        for (UserGroupFeatures userGroupFeature : userGroupFeatures) {
            userGroupFeaturesIdentity = new UserGroupFeaturesIdentity(
                    userGroup, new UserFeatures(userGroupFeature.getFeatureId())
            );
            userGroupFeature.setUserGroupFeaturesIdentity(userGroupFeaturesIdentity);
            userGroupFeatureList.add(userGroupFeature);
        }
        userGroupFeaturesRepository.saveAll(userGroupFeatureList);
    }

    @Override
    public void update(UserGroup userGroupParams) throws ResourceNotFoundException {
        UserGroup userGroupId = userGroupRepository.findById(userGroupParams.getUserGroupId()).orElse(null);

        if (userGroupId == null) {
            throw new ResourceNotFoundException();
        }

        UserGroup userGroup = new UserGroup();
        userGroup.setUserGroupId(userGroupParams.getUserGroupId());
        userGroup.setName(userGroupParams.getName());
        userGroup.setStatus(userGroupParams.getStatus());
        userGroupRepository.save(userGroup);
        List<UserGroupFeatures> userGroupFeatureList = new ArrayList<>();

        Set<UserGroupFeatures> userGroupFeatures = userGroupParams.getUserGroupFeatures();
        UserGroupFeaturesIdentity userGroupFeaturesIdentity;
        for (UserGroupFeatures userGroupFeature : userGroupFeatures) {
            userGroupFeaturesIdentity = new UserGroupFeaturesIdentity(
                    userGroup, new UserFeatures(userGroupFeature.getFeatureId())
            );
            userGroupFeature.setUserGroupFeaturesIdentity(userGroupFeaturesIdentity);
            userGroupFeatureList.add(userGroupFeature);
        }
        userGroupFeaturesRepository.saveAll(userGroupFeatureList);

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
        userGroupRepository.deleteUserGroupFeatures(userGroupId);
        userGroupRepository.removeUserGroupFromUser(userGroupId);

        return 1;
    }
}
