package dashboard.services.implement;

import dashboard.constants.FeatureConstants;
import dashboard.dto.user.UserGroupDto;
import dashboard.dto.user.UserGroupFeatureDto;
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

import java.util.*;
import java.util.stream.Collectors;

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
        UserGroupDto userGroupDto = userGroupMapper.findById(userGroupId).orElseThrow(ResourceNotFoundException::new);
        List<UserGroupFeatureDto> userGroupFeatureDtoList = userGroupDto.getUserGroupFeatures().stream().peek(item ->
            item.setFeatureName(FeatureConstants.MAP_FEATURE.get(item.getFeatureId()))).collect(Collectors.toList());
        userGroupDto.setUserGroupFeatures(userGroupFeatureDtoList);
        return userGroupDto;
    }

    @Override
    @Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor={Exception.class}
    )
    public void create(UserGroupForm userGroupForm) {
        UserGroup userGroup = new UserGroup(userGroupForm);
        List<UserGroupFeature> userGroupFeatureList = userGroupForm.getUserGroupFeatures();
        Map<String, String> featureKeys = new HashMap<>(FeatureConstants.MAP_FEATURE);
        long count = userGroupFeatureList.stream().filter(item -> {
            if (featureKeys.get(item.getUserFeatureId()) != null) {
                featureKeys.remove(item.getUserFeatureId());
                return true;
            }
            return false;
        }).count();

        if (count != FeatureConstants.MAP_FEATURE.size()) {
            throw new IllegalArgumentException("Format is not match Feature");
        }

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
