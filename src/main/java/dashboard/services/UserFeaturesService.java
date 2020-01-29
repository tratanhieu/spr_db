package dashboard.services;

import dashboard.entities.user.UserFeature;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

public interface UserFeaturesService {

    ListEntityResponse<UserFeature> getAllWithPagination(Pageable pageable);

    int create(UserFeature userGroup);

    int update(UserFeature userGroup) throws ResourceNotFoundException;

    int delete(String userFeatureId) throws ResourceNotFoundException;
}
