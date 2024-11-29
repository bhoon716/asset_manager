package org.example;

import org.example.ApplicationLogic.*;
import org.example.Control.MainControl;

public class Main {

    public static void main(String[] args) {
        StockApiService stockApiService = new StockApiService();
        CryptoApiService cryptoApiService = new CryptoApiService();
        AssetApiServiceFactory assetApiServiceFactory = new AssetApiServiceFactory(stockApiService, cryptoApiService);
        NewsApiService newsApiService = new NewsApiServiceImpl();
        AssetFactory assetFactory = new AssetFactory();
        PortfolioServiceImpl portfolioService = new PortfolioServiceImpl(assetFactory);
        UserServiceImpl userService = new UserServiceImpl();
        MainControl mainControl = new MainControl(assetApiServiceFactory, portfolioService, newsApiService, userService);
        mainControl.showLoginBoundary();
    }
}