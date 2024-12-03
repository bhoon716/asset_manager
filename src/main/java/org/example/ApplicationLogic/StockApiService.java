package org.example.ApplicationLogic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class StockApiService implements AssetApiService {

    private static final String BASE_URL = "https://financialmodelingprep.com/api/v3/quote/";
    private static final String API_KEY = "QeQqFFHgy2R6keFPHezAdxNJaeZGVmpM";

    @Override
    public double getCurrentPrice(String symbol) {
        return fetchFromApi(symbol)
                .map(jsonObject -> jsonObject.optDouble("price", Double.NaN))
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 코드: " + symbol));
    }

    @Override
    public JSONObject getAssetInfo(String symbol) {
        return fetchFromApi(symbol)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 코드: " + symbol));
    }

    private Optional<JSONObject> fetchFromApi(String symbol) {
        try {
            String url = getBaseUrl(symbol);
            JSONArray jsonArray = fetchJsonArrayFromApi(url);
            if (!jsonArray.isEmpty()) {
                return Optional.of(jsonArray.getJSONObject(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private JSONArray fetchJsonArrayFromApi(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return new JSONArray(response.toString());
        }
    }

    private String getBaseUrl(String symbol) {
        return BASE_URL + symbol + "?apikey=" + API_KEY;
    }
}
