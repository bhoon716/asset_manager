package org.example.ApplicationLogic;

import org.example.Entity.Asset;
import org.example.Entity.AssetType;
import org.example.Entity.Portfolio;

import java.util.List;

public interface PortfolioService {

    List<Portfolio> getPortfolioList();

    boolean checkDuplicate(String name);

    void addPortfolio(String name);

    void deletePortfolio(Portfolio portfolio);

    Portfolio getCurrentPortfolio();

    void setCurrentPortfolio(Portfolio currentPortfolio);

    void addAsset(Asset newAsset);

    void changeAsset(int index, Asset newAsset);

    void deleteAsset(int selectedRow);

    List<Object[]> getPortfolioDataList();

    Asset getDuplicatedAsset(AssetType assetType, String symbol);
}
