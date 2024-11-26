package org.example.Entity;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private String name;
    private final List<Asset> assetList;

    public Portfolio(String name) {
        this.name = name;
        this.assetList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Asset> getAssetList() {
        return assetList;
    }

    public Double getTotalEvaluationPrice() {
        Double totalPrice = 0.0;
        for(Asset asset : assetList){
            totalPrice += asset.getEvaluationPrice();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return name;
    }
}
