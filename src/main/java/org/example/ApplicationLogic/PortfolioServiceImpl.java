package org.example.ApplicationLogic;

import org.example.Entity.Asset;
import org.example.Entity.AssetType;
import org.example.Entity.Portfolio;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class PortfolioServiceImpl implements PortfolioService {

    private List<Portfolio> portfolioList;
    private Portfolio currentPortfolio;

    @Override
    public List<Portfolio> getPortfolioList() {
        return this.portfolioList;
    }

    @Override
    public boolean checkDuplicate(String name) {
        for (Portfolio portfolio : portfolioList) {
            if (name.equals(portfolio.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setPortfolioList(List<Portfolio> portfolioList) {this.portfolioList = portfolioList; }

    @Override
    public void addPortfolio(String name) {
        portfolioList.add(new Portfolio(name));
    }

    @Override
    public void deletePortfolio(Portfolio portfolio) {
        portfolioList.remove(portfolio);
    }

    @Override
    public Portfolio getCurrentPortfolio() {
        return currentPortfolio;
    }

    @Override
    public void setCurrentPortfolio(Portfolio currentPortfolio) {
        this.currentPortfolio = currentPortfolio;
    }

    @Override
    public void addAsset(Asset newAsset) {
        currentPortfolio.getAssetList().add(newAsset);
    }

    @Override
    public void changeAsset(int index, Asset newAsset) {
        currentPortfolio.getAssetList().set(index, newAsset);
    }

    @Override
    public void deleteAsset(int selectedRow) {
        currentPortfolio.getAssetList().remove(selectedRow);
    }

    @Override
    public List<Object[]> getPortfolioDataList() {
        List<Object[]> dataList = new ArrayList<>();
        for (Asset asset : currentPortfolio.getAssetList()) {
            dataList.add(asset.toStringArray());
        }
        return dataList;
    }

    @Override
    public Asset getDuplicatedAsset(AssetType assetType, String symbol) {
        for (Asset asset : currentPortfolio.getAssetList()) {
            if (symbol.equals(asset.getSymbol()) && assetType.equals(asset.getAssetType())) {
                return asset;
            }
        }
        return null;
    }
}
