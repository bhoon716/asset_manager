package org.example.Entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final String password;
    private final List<Portfolio> portfolioList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.portfolioList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }
}
