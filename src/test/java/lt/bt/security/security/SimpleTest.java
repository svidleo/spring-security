package lt.bt.security.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class SimpleTest {


    @Test
    public void newArrayListHaveNoElement() {
        assertEquals(0, new ArrayList().size());
    }
}
