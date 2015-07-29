package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@WebAppConfiguration
public class HelloTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void hello() throws Exception {
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(
                (Principal) () -> "foo", "1234");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new SecurityContext() {
                    @Override
                    public Authentication getAuthentication() {
                        return principal;
                    }

                    @Override
                    public void setAuthentication(Authentication authentication) {

                    }
                }
        );
        ResultActions perform = mockMvc.perform(get("/test2?target=1").session(session));
        perform.andExpect(status().isOk());
    }

    @Test
    public void caseTest() throws Exception {
        // Spring 4.2.x
        // https://jira.spring.io/browse/SPR-13286
        ResultActions perform = mockMvc.perform(get("/Case/aBc"));
        perform.andExpect(MockMvcResultMatchers.content().string("aBc"));

        ResultActions perform2 = mockMvc.perform(get("/Case/aBc?a=1"));
        perform2.andExpect(MockMvcResultMatchers.content().string("1"));

        ResultActions perform3 = mockMvc.perform(get("/Case/aBc?A=1"));
        perform3.andExpect(MockMvcResultMatchers.content().string("aBc"));
    }
}
