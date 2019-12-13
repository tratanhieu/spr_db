package dashboard.services.implement;

import dashboard.entities.user.UserFeatures;
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
    public ListEntityResponse<UserFeatures> getAllWithPagination(Pageable pageable) {
        Page<UserFeatures> result = userFeaturesRepository.findWithPageable(pageable);

        ListEntityResponse<UserFeatures> userFeaturesResponse = new ListEntityResponse<>();

        userFeaturesResponse.setPage(result.getNumber() + 1);
        userFeaturesResponse.setPageSize(result.getSize());
        userFeaturesResponse.setTotalPage(result.getTotalPages());
        userFeaturesResponse.setListData(result.getContent());

        return userFeaturesResponse;
    }

    @Override
    public int create(UserFeatures userFeatures) {
        userFeaturesRepository.save(userFeatures);
        return 1;
    }

    @Override
    public int update(UserFeatures userFeatures) throws ResourceNotFoundException {
        UserFeatures userFeaturesId = userFeaturesRepository.findById(userFeatures.getFeaturesId()).orElse(null);

        if (userFeaturesId == null) {
            throw new ResourceNotFoundException();
        }

        userFeaturesRepository.save(userFeatures);

        return 1;
    }

    @Override
    public int delete(String userFeatureId) throws ResourceNotFoundException {
        UserFeatures userFeaturesIdToDelete = userFeaturesRepository.findById(userFeatureId).orElse(null);

        if (userFeaturesIdToDelete == null) {
            throw new ResourceNotFoundException();
        }

        userFeaturesIdToDelete.setStatus(EntityStatus.DELETED);
        userFeaturesIdToDelete.setDeleteDate(new Date());
        userFeaturesRepository.save(userFeaturesIdToDelete);

        return 1;
    }
}
