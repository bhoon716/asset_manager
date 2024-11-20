package org.example.ApplicationLogic.AssetApiService;

import org.example.Entity.AssetDto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class StockApiService implements AssetApiService {

    private static final String BASE_URL = "https://financialmodelingprep.com/api/v3/historical-price-full/";
    private static final String API_KEY = "QeQqFFHgy2R6keFPHezAdxNJaeZGVmpM";

    @Override
    public AssetDto getAssetDto(String symbol) {
        String url = buildUrl(symbol);
        String jsonResponse = fetchDataFromApi(url);

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            System.err.println("API 응답이 비어 있습니다.");
            return null;
        }

        return parseJsonToAssetDto(jsonResponse);
    }

    private String buildUrl(String symbol) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String date = yesterday.toString(); // yyyy-MM-dd 형식

        return String.format("%s%s?from=%s&to=%s&apikey=%s",
                BASE_URL, symbol, date, date, API_KEY);
    }

    private String fetchDataFromApi(String url) {
        try {
            URL requestUrl = new URL(url);
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(requestUrl.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            System.out.println("주식 정보 패치 완료");
            return response.toString();
        } catch (MalformedURLException e) {
            System.err.println("잘못된 URL 입니다: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("API 요청 중 오류 발생: " + e.getMessage());
        }

        return null;
    }

    private AssetDto parseJsonToAssetDto(String jsonResponse) {
        // JSON 데이터 파싱
        JSONObject jsonObject = new JSONObject(jsonResponse);

        String symbol = jsonObject.getString("symbol");
        JSONArray historicalArray = jsonObject.getJSONArray("historical");
        if (historicalArray.isEmpty()) {
            System.err.println("Historical 데이터가 없습니다.");
            return null;
        }

        JSONObject historicalData = historicalArray.getJSONObject(0);

        // AssetDto 객체 생성 및 데이터 설정
        AssetDto assetDto = new AssetDto();
        assetDto.setSymbol(symbol);
        assetDto.setDate(historicalData.getString("date"));
        assetDto.setOpen(historicalData.getDouble("open"));
        assetDto.setHigh(historicalData.getDouble("high"));
        assetDto.setLow(historicalData.getDouble("low"));
        assetDto.setClose(historicalData.getDouble("close"));
        assetDto.setAdjClose(historicalData.getDouble("adjClose"));
        assetDto.setVolume(historicalData.getLong("volume"));
        assetDto.setChange(historicalData.getDouble("change"));
        assetDto.setChangePercent(historicalData.getDouble("changePercent"));

        return assetDto;
    }
}
