package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Abraham", "Lincoln", (byte) 56);
        userService.saveUser("Al", "Capone", (byte) 48);
        userService.saveUser("Conan", "Doyle", (byte) 71);
        userService.saveUser("Jack", "London", (byte) 40);


        for (User user : userService.getAllUsers()) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}