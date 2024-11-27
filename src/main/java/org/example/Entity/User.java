package org.example.Entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String id;
    private final String password;
    private final List<Portfolio> portfolioList;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.portfolioList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }
}
