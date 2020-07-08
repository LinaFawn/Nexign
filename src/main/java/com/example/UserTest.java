package com.example;

import com.example.entity.User;
import com.example.model.UserModel;
import com.example.repo.UserRepo;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(UserService.class)
public class UserTest {
    @MockBean
    private UserRepo repository;

    @Autowired
    private UserService service;

    @Test
    public void whenAddUserPassedBusiedUsername_thenThrowsException() {
        when(repository.existsByName(anyString())).thenReturn(true);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        try {
            UserModel userModel = new UserModel();
            userModel.setName("aoaoaoa");
            userModel.setEmail("sdkdkkd");
            service.addUser(userModel);
            fail("exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass(), "wrong exception thrown");
            assertEquals("username is already busy", e.getMessage(), "wrong exception message");
        }

        verify(repository, atMost(1)).existsByName(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenAddUserPassedBusiedEmail_thenThrowsException() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.existsByEmail(anyString())).thenReturn(true);

        try {
            UserModel userModel = new UserModel();
            userModel.setName("aoaoaoa");
            userModel.setEmail("sdkdkkd");
            service.addUser(userModel);
            fail("exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass(), "wrong exception thrown");
            assertEquals("email is already busy", e.getMessage(), "wrong exception message");
        }

        verify(repository, atMost(1)).existsByName(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenAddUserPassedFreeUsernameAndEmail_thenReturnNewEntityId() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.saveAndFlush(eq(new User("oaoaoa", "mmmm"))))
                .thenReturn(new User("oaoaoa", "mmmm"));

        UserModel userModel = new UserModel();
        userModel.setName("aoaoaoa");
        userModel.setEmail("sdkdkkd");
        assertEquals(3301, service.addUser(userModel), "wrong id returned");

        verify(repository, atMost(1)).existsByName(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
        verify(repository, atMost(1))
                .saveAndFlush(eq(new User("oaoaoa", "mmmm")));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenGetUserUserDoesntExist_thenThrowsException() {
        when(repository.existsById(anyInt())).thenReturn(false);

        try {
            service.getUserById(13);
            fail("exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass(), "wrong exception thrown");
            assertEquals("user doesn't exist", e.getMessage(), "wrong exception message");
        }

        verify(repository, atMost(1)).existsById(eq(13));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenGetUserUserExists_thenReturnsUserEntity() {
        when(repository.existsById(anyInt())).thenReturn(true);

        final User entity = new User(13, " ", "");
        when(repository.findById(228)).thenReturn(entity);

        final UserModel result = service.getUserById(13);
        assertSame(entity, result, "wrong entity returned");
        assertEquals(new User(13, "", ""), result, "entity was modified");

        verify(repository, atMost(1)).existsById(eq(13));
        verify(repository, atMost(1)).findById(eq(13));
        verifyNoMoreInteractions(repository);

    }

}