package com.mediscreen.user.tu;

import com.mediscreen.user.config.security.JwtTokenProvider;
import com.mediscreen.user.dao.UserDao;
import com.mediscreen.user.model.User;
import com.mediscreen.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @MockBean
    private UserDao userDaoMock;
    @MockBean
    private AuthenticationManager authenticationManagerMock;
    @MockBean
    private JwtTokenProvider jwtTokenProviderMock;
    @MockBean
    private PasswordEncoder passwordEncoderMock;

    private User user;
    //constantes de test
    String usernameConst = "username";
    String passwordConst = "password";
    String tokenConst = "myToken";
    String encryptPasswordConst = "$2a$12$scj6PvgZYRLahntmwOmm/.PnXJjHYK2SpsgsWb6fFbZBr5nWpbmJ6";

    @BeforeEach
    public void setUpEach() {

        user = new User();
        user.setUsername(usernameConst);
        user.setPwd(passwordConst);
    }
    /*------------------------ signin ---------------------------------*/
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void signin_correctUsernameAndPwd_tockenIsReturn(){
        //GIVEN
        Authentication t = null;
        Mockito.when(userDaoMock.findByUsername((anyString()))).thenReturn(user);
        Mockito.when(authenticationManagerMock.authenticate(any())).thenReturn(null);
        Mockito.when(jwtTokenProviderMock.createToken(any(),any())).thenReturn(tokenConst);

       //WHEN
        String result = userService.signin(user);

        //THEN
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(tokenConst);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void signin_incorrectUsernameAndPwd_errorReturn(){
        //GIVEN
        Mockito.when(userDaoMock.findByUsername((anyString()))).thenReturn(user);

        //WHEN
        String result = userService.signin(user);

        //THEN
        assertThat(result).isNull();
    }

    /*------------------------ SignUP ---------------------------------*/
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addUser_createAnInexistingUSer_theJWTTokenIsReturn(){
        //GIVEN
        Mockito.when(userDaoMock.existsByUsername(anyString())).thenReturn(false);
        Mockito.when(passwordEncoderMock.encode(anyString())).thenReturn(encryptPasswordConst);
        Mockito.when(userDaoMock.save(any(User.class))).thenReturn(user);
        Mockito.when(jwtTokenProviderMock.createToken(anyString(),any(List.class))).thenReturn(tokenConst);
        //WHEN
        String result = userService.addUser(user);
        //THEN
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(tokenConst);
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addUser_createAnExistingUSer_errorIsReturn(){
        //GIVEN
        Mockito.when(userDaoMock.existsByUsername(anyString())).thenReturn(true);
        Mockito.when(passwordEncoderMock.encode(anyString())).thenReturn(encryptPasswordConst);
        Mockito.when(userDaoMock.save(any(User.class))).thenReturn(user);
        Mockito.when(jwtTokenProviderMock.createToken(anyString(),any(List.class))).thenReturn(tokenConst);
        //WHEN
        String result = userService.addUser(user);
        //THEN
        assertThat(result).isNull();

    }

    /*------------------------ Update ---------------------------------*/
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateUser_UpdateAnExistingUSer_userIsReturn(){
        //GIVEN
        Mockito.when(userDaoMock.findByUsername(anyString())).thenReturn(user);
        Mockito.when(passwordEncoderMock.encode(anyString())).thenReturn(encryptPasswordConst);
        Mockito.when(userDaoMock.save(any(User.class))).thenReturn(user);

        //WHEN
        User result = userService.updateUser(user);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateUser_UpdateAnInexistingUSer_nullIsReturn(){
        //GIVEN
        Mockito.when(userDaoMock.findByUsername(anyString())).thenReturn(null);
        Mockito.when(passwordEncoderMock.encode(anyString())).thenReturn(encryptPasswordConst);
        Mockito.when(userDaoMock.save(any(User.class))).thenReturn(user);

        //WHEN
        User result = userService.updateUser(user);
        //THEN
        assertThat(result).isNull();
       }

    /*------------------------ Delete ---------------------------------*/
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteUser_UpdateAnExistingUSer_userIsReturn(){
        //GIVEN
        Mockito.when(userDaoMock.findByUsername(anyString())).thenReturn(user);
        Mockito.when(passwordEncoderMock.encode(anyString())).thenReturn(encryptPasswordConst);

        //WHEN
        User result = userService.deleteUser(user);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteUser_UpdateAnInexistingUSer_nullIsReturn(){
        //GIVEN
        Mockito.when(userDaoMock.findByUsername(anyString())).thenReturn(null);
        Mockito.when(passwordEncoderMock.encode(anyString())).thenReturn(encryptPasswordConst);

        //WHEN
        User result = userService.deleteUser(user);
        //THEN
        assertThat(result).isNull();
    }
}
