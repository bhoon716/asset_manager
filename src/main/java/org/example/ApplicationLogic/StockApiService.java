package org.example.ApplicationLogic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StockApiService implements AssetApiService {

    private static final String BASE_URL = "https://financialmodelingprep.com/api/v3/quote-short/";
    private static final String API_KEY = "QeQqFFHgy2R6keFPHezAdxNJaeZGVmpM";

    @Override
    public double getCurrentPrice(String symbol) {
        String url = getBaseUrl(symbol);

        try {
            // HTTP GET 요청
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // 응답 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // JSON 파싱
            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject stockData = jsonArray.getJSONObject(0);
                return stockData.getDouble("price"); // 주식 가격 반환
            }

            throw new IllegalArgumentException("Symbol not found or invalid response: " + symbol);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch current price for symbol: " + symbol);
        }
    }

    private String getBaseUrl(String symbol) {
        return BASE_URL + symbol + "?apikey=" + API_KEY;
    }
}
