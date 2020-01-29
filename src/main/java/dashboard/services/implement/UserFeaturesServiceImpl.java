package dashboard.services.implement;

import dashboard.entities.user.UserFeature;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.UserFeaturesRepository;
import dashboard.services.UserFeaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserFeaturesServiceImpl implements UserFeaturesService {

    @Autowired
    UserFeaturesRepository userFeaturesRepository;

    @Override
    public ListEntityResponse<UserFeature> getAllWithPagination(Pageable pageable) {
        Page<UserFeature> result = userFeaturesRepository.findWithPageable(pageable);

        ListEntityResponse<UserFeature> userFeaturesResponse = new ListEntityResponse<>();

        userFeaturesResponse.setPage(result.getNumber() + 1);
        userFeaturesResponse.setPageSize(result.getSize());
        userFeaturesResponse.setTotalPage(result.getTotalPages());
        userFeaturesResponse.setListData(result.getContent());

        return userFeaturesResponse;
    }

    @Override
    public int create(UserFeature userFeature) {
        userFeaturesRepository.save(userFeature);
        return 1;
    }

    @Override
    public int update(UserFeature userFeature) throws ResourceNotFoundException {
        UserFeature userFeatureId = userFeaturesRepository.findById(userFeature.getFeaturesId()).orElse(null);

        if (userFeatureId == null) {
            throw new ResourceNotFoundException();
        }

        userFeaturesRepository.save(userFeature);

        return 1;
    }

    @Override
    public int delete(String userFeatureId) throws ResourceNotFoundException {
        UserFeature userFeatureIdToDelete = userFeaturesRepository.findById(userFeatureId).orElse(null);

        if (userFeatureIdToDelete == null) {
            throw new ResourceNotFoundException();
        }

        userFeatureIdToDelete.setStatus(EntityStatus.DELETED);
        userFeatureIdToDelete.setDeleteDate(new Date());
        userFeaturesRepository.save(userFeatureIdToDelete);

        return 1;
    }
}
