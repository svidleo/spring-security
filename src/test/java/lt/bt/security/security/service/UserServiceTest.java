package lt.bt.security.security.service;

import lt.bt.security.security.entity.Role;
import lt.bt.security.security.entity.User;
import lt.bt.security.security.entity.dto.UserDto;
import lt.bt.security.security.repository.RoleRepository;
import lt.bt.security.security.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldReturnCurrentUser() {
        User user = new User();
        user.setUsername("Username");
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "jonas";
            }
        };

        SecurityContext securityContext = new SecurityContext() {
            private Authentication authentication;
            @Override
            public Authentication getAuthentication() {
                return this.authentication;
            }

            @Override
            public void setAuthentication(Authentication authentication) {
                this.authentication = authentication;
            }
        };

        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername("jonas"))
                .thenReturn(user);

        UserDto userDto = service.getCurrentUser();

        assertEquals("Username", userDto.getUsername());
    }

    @Test(expected = Exception.class)
    public void shouldReject() throws Exception {
        service.reject();
    }
    @Test
    public void newArrayListHaveNoElement() {
        assertEquals(0, new ArrayList().size());
    }

    @Test
    public void encryptPassword() {
        User user = new User();

        user.setPassword("supersecure");
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        when(passwordEncoder.encode(user.getPassword())).thenReturn("hash");

        User newUser = service.createUser(user);

        verify(userRepository).save(argument.capture());

        assertEquals("hashsdf", argument.getValue().getPassword());
    }

    @Test
    public void applyUserRole() {
        User user = new User();


        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);

        User applyed = service.applyRoles(user);

        Role response = applyed.getRoles().iterator().next();

        assertEquals("ROLE_USER",
                applyed.getRoles().iterator().next().getName());
    }

    @Test
    public void invokeSaveMethod() {
        User user = new User();

        service.createUser(user);

        User otherUser = new User();

        verify(userRepository).save(any(User.class));
    }

}
