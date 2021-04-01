package com.mediscreen.user.ti;

import com.mediscreen.user.dao.UserDao;
import com.mediscreen.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;


    //constantes de test
    String usernameConst = "username";
    String passwordConst = "password";
    String incorrectPasswordConst = "incorrectpassword";
    String inexistingUsernameConst = "new_username";


    /*---------------------------------------- Signin-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signin_giveUsernameAndCorrectPassword_connectionIsAgree() throws Exception {
        //Given


        //WHEN //THEN return token
        mockMvc.perform(get("/signin?username="+usernameConst+"&pwd="+passwordConst))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signin_giveUsernameAndPasswordIncorrectPassword_connectionIsRefuse() throws Exception {
        //Given


        //WHEN //THEN return token
        mockMvc.perform(get("/signin?username="+usernameConst+"&pwd="+incorrectPasswordConst))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    /*---------------------------------------- SignUp-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signup_giveInexistingUsernameAndCorrectPassword_updateIsDone() throws Exception {
        //Given


        //WHEN //THEN return token
        mockMvc.perform(post("/signup?username="+inexistingUsernameConst+"&pwd="+passwordConst))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signup_giveExistingUsernameAndCorrectPassword_errorIsReturn() throws Exception {
        //Given


        //WHEN //THEN return token
        mockMvc.perform(post("/signup?username="+usernameConst+"&pwd="+passwordConst))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /*---------------------------------------- Update -------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateUser_giveExistingUsername_updatePasswordIsDone() throws Exception {
        //Given
        mockMvc.perform(post("/signup?username="+inexistingUsernameConst+"&pwd="+passwordConst));

        //WHEN //THEN return token
        mockMvc.perform(put("/user?username="+inexistingUsernameConst+"&pwd="+incorrectPasswordConst))
                .andDo(print())
                .andExpect(status().isAccepted());
    }
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateUser_giveInexistingUsername_errorIsDone() throws Exception {
        //Given


        //WHEN //THEN return token
        mockMvc.perform(put("/user?username="+inexistingUsernameConst+"&pwd="+incorrectPasswordConst))
                .andDo(print())
                .andExpect(status().isNotModified());
    }
    /*---------------------------------------- Delete -------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteUser_giveExistingUsername_deleteIsDone() throws Exception {
        //Given
        mockMvc.perform(post("/signup?username="+inexistingUsernameConst+"&pwd="+passwordConst));

        //WHEN //THEN return token
        mockMvc.perform(delete("/user?username="+inexistingUsernameConst))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteUser_giveInexistingUsername_errorIsReturn() throws Exception {
        //Given

        //WHEN //THEN return token
        mockMvc.perform(delete("/user?username="+inexistingUsernameConst))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
}
