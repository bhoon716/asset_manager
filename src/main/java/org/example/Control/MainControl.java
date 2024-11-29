package org.example.Control;

import org.example.ApplicationLogic.AssetApiServiceFactory;
import org.example.ApplicationLogic.NewsApiService;
import org.example.ApplicationLogic.PortfolioService;
import org.example.ApplicationLogic.UserService;
import org.example.Boundary.*;
import org.example.Entity.*;
import org.json.JSONObject;

import java.util.List;

public class MainControl {

    private final AssetApiServiceFactory assetApiServiceFactory;
    private final PortfolioService portfolioService;
    private final NewsApiService newsApiService;
    private final UserService userService;

    public MainControl(
            AssetApiServiceFactory assetApiServiceFactory,
            PortfolioService portfolioService,
            NewsApiService newsApiService,
            UserService userService
    ) {
        this.assetApiServiceFactory = assetApiServiceFactory;
        this.portfolioService = portfolioService;
        this.newsApiService = newsApiService;
        this.userService = userService;
    }

    // User Initialization and Portfolio Setup
    public void initializeUserPortfolio() {
        portfolioService.setPortfolioList(userService.getCurrentUser().getPortfolioList());
    }

    // Boundary Display Methods
    public void showMainBoundary() {
        initializeUserPortfolio();
        new MainBoundary(this);
    }

    public void showPortfolioListBoundary() {
        new PortfolioListBoundary(this);
    }

    public void showPortfolioBoundary(Portfolio portfolio) {
        portfolioService.setCurrentPortfolio(portfolio);
        new PortfolioBoundary(this);
    }

    public void showLoginBoundary() {
        new LoginBoundary(this);
    }

    public void showRegisterBoundary() {
        new RegisterBoundary(this);
    }

    public void showAddAssetDialog() {
        AssetDialog assetDialog = new AssetDialog(null, this, "자산 추가");
        assetDialog.setVisible(true);
    }

    public void showEditAssetDialog(int selectedRow) {
        AssetDialog assetDialog = new AssetDialog(null, this, "자산 수정");
        assetDialog.setEditMode(selectedRow);
        assetDialog.setVisible(true);
    }

    public void showAssetInfoBoundary() {
        new AssetSearchBoundary(this);
    }

    // Portfolio Operations
    public List<Portfolio> getPortfolioList() {
        return portfolioService.getPortfolioList();
    }

    public void addPortfolio(String name) {
        portfolioService.addPortfolio(name);
    }

    public void deletePortfolio(Portfolio portfolio) {
        portfolioService.deletePortfolio(portfolio);
    }

    public boolean checkDuplicatedPortfolioName(String name) {
        return portfolioService.checkDuplicate(name);
    }

    public double getCurrentPortfolioEvaluationPrice() {
        Portfolio currentPortfolio = portfolioService.getCurrentPortfolio();
        if (currentPortfolio == null) {
            throw new IllegalStateException("현재 선택된 포트폴리오가 없습니다.");
        }
        return currentPortfolio.getTotalEvaluationPrice();
    }

    public double getCurrentPortfolioProfitLoss() {
        Portfolio currentPortfolio = portfolioService.getCurrentPortfolio();
        if (currentPortfolio == null) {
            throw new IllegalStateException("현재 선택된 포트폴리오가 없습니다.");
        }
        return currentPortfolio.getTotalProfitLoss();
    }

    public double getCurrentPortfolioProfitLossRate() {
        Portfolio currentPortfolio = portfolioService.getCurrentPortfolio();
        if (currentPortfolio == null) {
            throw new IllegalStateException("현재 선택된 포트폴리오가 없습니다.");
        }
        return currentPortfolio.getTotalProfitLossRate();
    }

    public Object[][] getAssetTableData() {
        return portfolioService.getPortfolioDataList().toArray(new Object[0][]);
    }

    // Asset Operations
    public void addAsset(AssetType assetType, String symbol, double purchasePrice, double quantity) {
        portfolioService.addAsset(assetType, symbol, purchasePrice, quantity);
        fetchAssetPriceAndUpdate(symbol, assetType);
    }

    public void editAsset(int selectedRow, AssetType assetType, String symbol, double purchasePrice, double quantity) {
        portfolioService.editAsset(selectedRow, assetType, symbol, purchasePrice, quantity);
    }

    public void deleteAsset(int selectedRow) {
        portfolioService.deleteAsset(selectedRow);
    }

    public Asset getAssetByIndex(int index) {
        Portfolio currentPortfolio = portfolioService.getCurrentPortfolio();
        if (currentPortfolio == null) {
            throw new IllegalStateException("현재 선택된 포트폴리오가 없습니다.");
        }
        return currentPortfolio.getAssetList().get(index);
    }

    public void fetchAllCurrentPrice() {
        Portfolio currentPortfolio = portfolioService.getCurrentPortfolio();
        if (currentPortfolio == null) {
            throw new IllegalStateException("현재 선택된 포트폴리오가 없습니다.");
        }
        currentPortfolio.getAssetList().stream()
                .filter(asset -> asset instanceof Tradable)
                .forEach(asset -> fetchAssetPriceAndUpdate(asset.getSymbol(), asset.getAssetType()));
    }

    private void fetchAssetPriceAndUpdate(String symbol, AssetType assetType) {
        try {
            double currentPrice = assetApiServiceFactory.getApiService(assetType).getCurrentPrice(symbol);
            Asset asset = portfolioService.getDuplicatedAsset(assetType, symbol);
            if (asset instanceof Tradable tradable) {
                tradable.setCurrentPrice(currentPrice);
            }
        } catch (Exception e) {
            System.err.println("현재 가격 업데이트 실패: " + e.getMessage());
        }
    }

    // Asset Search
    public JSONObject searchAsset(AssetType type, String symbol) {
        return assetApiServiceFactory.getApiService(type).getAssetInfo(symbol);
    }

    // News Operations
    public List<News> fetchNewsHeadlines(int page, int size) {
        return newsApiService.getNewsList(page, size);
    }

    // User Operations
    public boolean login(String id, String password) {
        return userService.login(id, password);
    }

    public void addUser(String id, String password) {
        userService.addUser(id, password);
    }

    public boolean checkDuplicatedUserId(String id) {
        return userService.checkDuplicate(id);
    }

    public User getUser() {
        return userService.getCurrentUser();
    }

    public void logout() {
        userService.setCurrentUser(null);
        showLoginBoundary();
    }
}
