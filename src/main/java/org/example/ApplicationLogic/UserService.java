package org.example.ApplicationLogic;

import org.example.Entity.Portfolio;
import org.example.Entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, User> getUserMap();

    boolean checkDuplicate(String id);

    void addUser(String id, String password);

    User getCurrentUser();

    void setCurrentUser(User currentUser);

    boolean login(String id, String password);
}
