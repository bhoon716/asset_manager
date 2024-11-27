package org.example.ApplicationLogic;

import org.example.Entity.Portfolio;
import org.example.Entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private final Map<String, User> userMap = new HashMap<>();
    private User currentUser;

    @Override
    public Map<String, User> getUserMap() {
        return this.userMap;
    }

    @Override
    public boolean checkDuplicate(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public void addUser(String id, String password) {
        userMap.put(id, new User(id, password));
    }
    @Override
    public User getCurrentUser() {
        return currentUser;
    }
    @Override
    public String getUserId() {return currentUser.getId();}
    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


    @Override
    public List<Portfolio> getPortfolioListDataList() {
        return currentUser.getPortfolioList();
    }
    @Override
    public boolean login(String id, String password) {
        User user = userMap.get(id); // Map에서 사용자 검색
        if (user != null && user.getPassword().equals(password)) {
            this.currentUser = user; // 로그인 성공 시 현재 사용자 설정
            return true;
        }
        return false; // 로그인 실패
    }
}
