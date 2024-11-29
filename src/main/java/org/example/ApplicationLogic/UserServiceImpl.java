package org.example.ApplicationLogic;

import org.example.Entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private final Map<String, User> userMap = new HashMap<>();
    private User currentUser;

    @Override
    public Map<String, User> getUserMap() {
        return userMap;
    }

    @Override
    public boolean checkDuplicate(String id) {
        // 사용자 ID 중복 확인
        return userMap.containsKey(id);
    }

    @Override
    public void addUser(String id, String password) {
        if (checkDuplicate(id)) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID 입니다: " + id);
        }
        userMap.put(id, new User(id, password));
    }

    @Override
    public User getCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("현재 로그인된 사용자가 없습니다.");
        }
        return currentUser;
    }

    @Override
    public void setCurrentUser(User currentUser) {
        if (currentUser == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
        }
        this.currentUser = currentUser;
    }

    @Override
    public boolean login(String id, String password) {
        User user = userMap.get(id);
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        }
        this.currentUser = user; // 로그인 성공 시 현재 사용자 설정
        return true;
    }
}
