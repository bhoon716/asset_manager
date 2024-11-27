package org.example.ApplicationLogic;

import org.example.Entity.Portfolio;
import org.example.Entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, User> getUserMap();

    boolean checkDuplicate(String id);

    void addUser(String id, String password);

    User getCurrentUser();
    String getUserId();
    void setCurrentUser(User currentUser);
    List<Portfolio> getPortfolioListDataList();

    boolean login(String id, String password);
}
