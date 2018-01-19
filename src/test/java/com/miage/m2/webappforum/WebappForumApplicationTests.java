package com.miage.m2.webappforum;

import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import com.sun.glass.ui.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebappForumApplication.class)
@DataJpaTest
public class WebappForumApplicationTests {

/*
	private UserService userService = new UserService();

	@Autowired
	private UtilisateurRepository ur;


	private Utilisateur user;

	@Before
	public void onSetUp() throws Exception {
		// add principal object to SecurityContextHolder
		Utilisateur user = new Utilisateur();
		user.setPseudo("test");
		user.setEmail("test@mail.com");
		user.setPassword("test");
		user.setId("123456");
		user.setInscription(new Date());
		this.user = user;
		ur.save(user);

		Authentication auth =  new UsernamePasswordAuthenticationToken(user,null);
		SecurityContextHolder.getContext().setAuthentication(auth);

	}


	@Test
	public void testExample() throws Exception {
		Utilisateur loggedUser = userService.getLoggedUser();
		assertEquals(loggedUser,user);

	}
	*/
}
