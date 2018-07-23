package lt.bt.security.security.service;

import lt.bt.security.security.entity.User;
import lt.bt.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    public Boolean isAuthenticated() {
        return getAuthentication() != null && getAuthentication().isAuthenticated();
    }

    public String getUsername() {
        Authentication authentication = getAuthentication();
        return getAuthentication().getName();
    }

    public List<String > getRole() {
        return getAuthentication().getAuthorities()
                .stream()
                .map(authority -> ((GrantedAuthority) authority).getAuthority().toString())
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        return repository.save(user);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
