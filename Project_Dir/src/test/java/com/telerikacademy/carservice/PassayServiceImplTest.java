package com.telerikacademy.carservice;

import com.telerikacademy.carservice.service.PassayServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class PassayServiceImplTest {
    PassayServiceImpl passayService = new PassayServiceImpl();

    @Test
    public void testGenerateRandomPassword_ShouldReturn_WhenRandomPasswordCreated() throws Exception {
        String result = passayService.generateRandomPassword();
        List<String> listToCheck = new ArrayList<>();
        listToCheck.add(result);
        Assert.assertEquals(1, listToCheck.size());
    }

    @Test
    public void testGenerateRandomPassword_ShouldReturn_WhenTwoRandomPasswordsCreated() throws Exception {
        String passwordOne = passayService.generateRandomPassword();
        String passwordTwo = passayService.generateRandomPassword();
        Assert.assertNotEquals(passwordOne, passwordTwo);
    }
}
