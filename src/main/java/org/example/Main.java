package org.example;

import org.example.ApplicationLogic.*;
import org.example.Control.MainControl;

public class Main {

    public static void main(String[] args) {
        AssetFactory assetFactory = new AssetFactory();
        NewsApiService newsApiService = new NewsApiServiceImpl();
        PortfolioServiceImpl portfolioService = new PortfolioServiceImpl();
        UserServiceImpl userService = new UserServiceImpl();
        MainControl mainControl = new MainControl(assetFactory, newsApiService, portfolioService, userService);
        mainControl.showLoginBoundary();
    }
}