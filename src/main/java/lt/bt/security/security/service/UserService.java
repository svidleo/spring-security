package lt.bt.security.security.service;

import lt.bt.security.security.entity.Role;
import lt.bt.security.security.entity.User;
import lt.bt.security.security.entity.dto.UserDto;
import lt.bt.security.security.repository.RoleRepository;
import lt.bt.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

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

        Role role = roleRepository.findByName("ROLE_USER");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        user.setRoles(roleSet);

        return repository.save(user);
    }

    public UserDto getCurrentUser(String name) {
        User user = repository.findByUsername(name);
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());

        return userDto;
    }

    public void reject() throws Exception {
        throw new Exception("SOme");
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
