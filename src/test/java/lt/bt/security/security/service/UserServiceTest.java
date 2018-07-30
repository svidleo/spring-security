package lt.bt.security.security.service;

import lt.bt.security.security.entity.User;
import lt.bt.security.security.entity.dto.UserDto;
import lt.bt.security.security.repository.UserRepository;
import org.hibernate.validator.constraints.Mod10Check;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldReturnCurrentUser() {
        User user = new User();
        user.setUsername("Username");
        when(userRepository.findByUsername("user"))
                .thenReturn(user);

        UserDto userDto = service.getCurrentUser("user");

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
}
