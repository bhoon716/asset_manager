package org.example.ApplicationLogic;

import org.json.JSONObject;

public interface AssetApiService {

    /**
     * 심볼에 해당하는 현재 가격을 가져옵니다.
     * @param symbol 자산의 심볼
     * @return 현재 가격
     */
    double getCurrentPrice(String symbol);

    /**
     * 심볼에 해당하는 자산의 세부 정보를 가져옵니다.
     * @param symbol 자산의 심볼
     * @return 자산 세부 정보
     */
    JSONObject getAssetInfo(String symbol);
}
