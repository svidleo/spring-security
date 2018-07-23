package lt.bt.security.security.service;

import lt.bt.security.security.entity.User;
import lt.bt.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true,
                true, user.getRoles().iterator().next().getAuthorities());
    }
}
