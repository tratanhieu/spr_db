package dashboard.services.implement;

import dashboard.dto.user.UserGroupDto;
import dashboard.dto.user.UserGroupForm;
import dashboard.entities.embedded.UserGroupFeaturesIdentity;
import dashboard.entities.user.UserFeature;
import dashboard.entities.user.UserGroup;
import dashboard.entities.user.UserGroupFeature;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.repositories.UserGroupFeatureMapper;
import dashboard.repositories.UserGroupFeaturesRepository;
import dashboard.repositories.UserGroupMapper;
import dashboard.repositories.UserGroupRepository;
import dashboard.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupMapper userGroupMapper;

    @Autowired
    UserGroupFeatureMapper userGroupFeatureMapper;

    @Override
    public List getAll() {
        return userGroupMapper.findAll();
    }

    @Override
    public UserGroupDto getOne(Long userGroupId) throws ResourceNotFoundException {
        return userGroupMapper.findById(userGroupId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor={Exception.class}
    )
    public void create(UserGroupForm userGroupForm) {
        UserGroup userGroup = new UserGroup(userGroupForm);
        List<UserGroupFeature> userGroupFeatureList = userGroupForm.getUserGroupFeatures();
        userGroupMapper.save(userGroup);
        userGroupFeatureMapper.saveAll(userGroup.getUserGroupId(), userGroupFeatureList);
    }

    @Override
    @Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor={Exception.class}
    )
    public void update(UserGroupForm userGroupForm) throws ResourceNotFoundException {
        userGroupMapper.findById(userGroupForm.getUserGroupId()).orElseThrow(ResourceNotFoundException::new);
        UserGroup userGroup = new UserGroup(userGroupForm);
        List<UserGroupFeature> userGroupFeatureList = userGroupForm.getUserGroupFeatures();
        userGroupMapper.updateById(userGroup);
        userGroupFeatureMapper.saveAll(userGroup.getUserGroupId(), userGroupFeatureList);
    }

    @Override
    @Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor={Exception.class}
    )
    public void delete(Long userGroupId) {
        userGroupMapper.deleteById(userGroupId);
        userGroupFeatureMapper.deleteByUserGroupId(userGroupId);
    }
}
