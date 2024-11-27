package org.example.ApplicationLogic;

import org.example.Entity.Portfolio;
import org.example.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final List<User> userList = new ArrayList<>();
    private User currentUser;

    @Override
    public List<User> getUserList() {
        return this.userList;
    }

    @Override
    public boolean checkDuplicate(String id) {
        for(User user : userList){
            if(id.equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(String id, String password) {
        userList.add(new User(id, password));
    }
    @Override
    public User getCurrentUser() {
        return currentUser;
    }
    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    @Override
    public void addPortfolio(Portfolio newPortfolio) {
        currentUser.getPortfolioList().add(newPortfolio);
    }

    @Override
    public boolean login(String id, String password) {
        for (User user : userList) {
            if (id.equals(user.getId()) && password.equals(user.getPassword())) {
                this.currentUser = user; // 로그인 성공 시 현재 사용자 설정
                return true;
            }
        }
        return false; // 로그인 실패
    }
}
