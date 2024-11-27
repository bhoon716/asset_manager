package org.example.ApplicationLogic;

import org.example.Entity.Portfolio;
import org.example.Entity.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    List<User> getUserList();

    boolean checkDuplicate(String id);

    void addUser(String id, String password);

    User getCurrentUser();
    void setCurrentUser(User currentUser);
    void addPortfolio(Portfolio newPortfolio);

    boolean login(String id, String password);
}
