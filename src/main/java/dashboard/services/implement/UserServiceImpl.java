package dashboard.services.implement;

import dashboard.entities.user.User;
import dashboard.enums.UserStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.UserRepository;
import dashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public ListEntityResponse<User> getAllWithPagination(Pageable pageable) {
        Page<User> result = userRepository.findWithPageable(pageable);

        ListEntityResponse<User> userResponse = new ListEntityResponse<>();

        userResponse.setPage(result.getNumber() + 1);
        userResponse.setPageSize(result.getSize());
        userResponse.setTotalPage(result.getTotalPages());
        userResponse.setListData(result.getContent());

        return userResponse;

    }

    @Override
    public User getOne(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return user;
    }

    @Override
    public int create(User user) {
        userRepository.save(user);
        return 1;
    }

    @Override
    public int update(User user) throws ResourceNotFoundException {
        User userId = userRepository.findById(user.getUserId()).orElse(null);

        if (userId == null) {
            throw new ResourceNotFoundException();
        }

        userRepository.save(user);

        return 1;
    }

    @Override
    public int delete(Long userId) throws ResourceNotFoundException {
        User userIdToDelete = userRepository.findById(userId).orElse(null);

        if (userIdToDelete == null) {
            throw new ResourceNotFoundException();
        }

        userIdToDelete.setStatus(UserStatus.DELETED);
        userIdToDelete.setDeleteDate(new Date());
        userRepository.save(userIdToDelete);

        return 1;
    }
}
