package dashboard.services;

import dashboard.entities.user.User;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    ListEntityResponse<User> getAllWithPagination(Pageable pageable);

    User getOne(Long userId) throws ResourceNotFoundException;

    int create(User user);

    int update(User user) throws ResourceNotFoundException;

    int delete(Long userId) throws ResourceNotFoundException;
}