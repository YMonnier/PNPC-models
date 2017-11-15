package fr.pnpc.project.models;

import fr.pnpc.project.models.ejb.UserManager;
import fr.pnpc.project.models.exceptions.*;
import fr.pnpc.project.models.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.ejb.EJB;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserManagerTest {

    @EJB
    private UserManager userManager;

    private User user;

    private final String DEFAULT_EMAIL = "test@test.com";
    private final String DEFAULT_NICKNAME = "JohnWick";
    private final String DEFAULT_PHONE_NUMBER = "0651576906";
    private final String DEFAULT_PASSWORD = "SUPER PASSWORD";

    @BeforeEach
    public void init() {
        this.user = new User.Builder()
                .setEmail(DEFAULT_EMAIL)
                .setNickname(DEFAULT_NICKNAME)
                .setPhoneNumber(DEFAULT_PHONE_NUMBER)
                .setPassword(DEFAULT_PASSWORD)
                .build();
    }

    /*
    @Test
    public void registerTest(){
        try {
            when(this.userManager.register(user)).thenReturn(user);
            assertNotNull(user);
        } catch (NotValidException e) {
            e.printStackTrace();
        } catch (NullObjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest(){
        try {
            when(this.userManager.login(DEFAULT_NICKNAME, DEFAULT_PASSWORD)).thenReturn(user);
        } catch (NotValidEmailException e) {
            e.printStackTrace();
        } catch (NotValidPassword notValidPassword) {
            notValidPassword.printStackTrace();
        } catch (UserNotExistException e) {
            e.printStackTrace();
        }
        assertNotNull(user);
    }
    */



}
