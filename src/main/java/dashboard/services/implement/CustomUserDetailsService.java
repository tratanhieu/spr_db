package dashboard.services.implement;

import dashboard.entities.user.CustomUserDetails;
import dashboard.entities.user.User;
import dashboard.repositories.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userMapper.findByPhone(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserByUserId(Long userId) {
        User user = userMapper.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException(userId.toString()));
        return new CustomUserDetails(user);
    }
}
