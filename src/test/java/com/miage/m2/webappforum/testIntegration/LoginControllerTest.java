package com.miage.m2.webappforum.testIntegration;


import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.configuration.Language;
import com.miage.m2.webappforum.configuration.Permission;
import com.miage.m2.webappforum.configuration.Security;
import com.miage.m2.webappforum.control.LoginController;
import com.miage.m2.webappforum.repository.RoleRepository;
import com.miage.m2.webappforum.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LoginControllerTest {


    private MockMvc mvc;


    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        //this.mvc = webAppContextSetup(this.wac).build();
        this.mvc = standaloneSetup(new LoginController()).alwaysExpect(status().isOk()).build();
    }
    @Test
    public void testLoginRedirect() throws Exception {

        this.mvc.perform(get("/login")).andExpect(view().name("login/login"));


    }

}
