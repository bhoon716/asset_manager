package org.example;

import org.example.ApplicationLogic.AssetFactory;
import org.example.ApplicationLogic.NewsApiService;
import org.example.ApplicationLogic.NewsApiServiceImpl;
import org.example.ApplicationLogic.PortfolioServiceImpl;
import org.example.Control.MainControl;

public class Main {

    public static void main(String[] args) {
        AssetFactory assetFactory = new AssetFactory();
        NewsApiService newsApiService = new NewsApiServiceImpl();
        PortfolioServiceImpl portfolioService = new PortfolioServiceImpl();

        MainControl mainControl = new MainControl(assetFactory, newsApiService, portfolioService);
        mainControl.showMainBoundary();
    }
}