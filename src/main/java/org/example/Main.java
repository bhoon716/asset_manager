package org.example;

import org.example.ApplicationLogic.AssetFactory;
import org.example.ApplicationLogic.NewsApiService;
import org.example.ApplicationLogic.PortfolioService;
import org.example.Control.MainControl;

public class Main {

    public static void main(String[] args) {
        AssetFactory assetFactory = new AssetFactory();
        NewsApiService newsApiService = new NewsApiService();
        PortfolioService portfolioService = new PortfolioService();

        MainControl mainControl = new MainControl(assetFactory, newsApiService, portfolioService);
        mainControl.showMainBoundary();
    }
}