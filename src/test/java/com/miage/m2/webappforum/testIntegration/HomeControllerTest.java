package com.miage.m2.webappforum.testIntegration;


import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.configuration.Language;
import com.miage.m2.webappforum.configuration.Permission;
import com.miage.m2.webappforum.configuration.Security;
import com.miage.m2.webappforum.control.HomeController;
import com.miage.m2.webappforum.control.LoginController;
import com.miage.m2.webappforum.repository.RoleRepository;
import com.miage.m2.webappforum.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class HomeControllerTest {


    private MockMvc mvc;


    @Before
    public void setUp() {

        this.mvc = standaloneSetup(new HomeController()).alwaysExpect(status().isOk()).build();
    }
    @Test
    public void testHomeRedirect() throws Exception {

        this.mvc.perform(get("/")).andExpect(view().name("index"));


    }

}
