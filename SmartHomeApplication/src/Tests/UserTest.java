package Tests;

import Clas.User;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void getUsernameTest() {
        User user = new User("test", "test", "test", "test", "test");
        Assert.assertEquals(user.getUsername(), "test");
    }

    @Test
    public void getNameTest() {
        User user = new User("test", "test", "test", "test", "test");
        Assert.assertEquals(user.getName(), "test");
    }

    @Test
    public void getSurnameTest() {
        User user = new User("test", "test", "test", "test", "test");
        Assert.assertEquals(user.getSurname(), "test");
    }

    @Test
    public void getEmailTest() {
        User user = new User("test", "test", "test", "test", "test");
        Assert.assertEquals(user.getEmail(), "test");
    }

    @Test
    public void getPasswordTest() {
        User user = new User("test", "test", "test", "test", "test");
        Assert.assertEquals(user.getPassword(), "test");
    }

}
