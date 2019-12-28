package dashboard.services;

import dashboard.entities.user.UserFeatures;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

public interface UserFeaturesService {

    ListEntityResponse<UserFeatures> getAllWithPagination(Pageable pageable);

    int create(UserFeatures userGroup);

    int update(UserFeatures userGroup) throws ResourceNotFoundException;

    int delete(String userFeatureId) throws ResourceNotFoundException;
}
