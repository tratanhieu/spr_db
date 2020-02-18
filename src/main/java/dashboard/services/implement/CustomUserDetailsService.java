package dashboard.services.implement;

import dashboard.entities.user.CustomUserDetails;
import dashboard.entities.user.Customer;
import dashboard.entities.user.User;
import dashboard.repositories.CustomerMapper;
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
    private CustomerMapper customerMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Customer customer = customerMapper.findByPhone(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(customer);
    }

    public UserDetails loadUserByUserId(Long userId) {
        Customer customer = customerMapper.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException(userId.toString()));
        return new CustomUserDetails(customer);
    }
}
