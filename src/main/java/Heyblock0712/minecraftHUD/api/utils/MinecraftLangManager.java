package Heyblock0712.minecraftHUD.api.utils;

import Heyblock0712.minecraftHUD.MinecraftHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MinecraftLangManager {
    public static final Map<String, Map<String, String>> translations = new HashMap<>();

    private MinecraftLangManager() {}

    public static void loadMinecraftLangJsonFromUrl(String version, String minecraftLocale) {
        String url = "https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/" + version + "/assets/minecraft/lang/" + minecraftLocale + ".json";
        try {
            URI uri = new URI(url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                Gson gson = new Gson();
                Map<String, String> langMap = gson.fromJson(jsonResponse, new TypeToken<Map<String, String>>(){}.getType());
                translations.put(minecraftLocale, langMap);

                MinecraftHUD.getInstance().getLogger().info("已加載 " + minecraftLocale + " 翻譯文本");
            } else {
                MinecraftHUD.getInstance().getLogger().warning("無法加載 " + minecraftLocale + " 翻譯文本 - " + response.statusCode());
            }
        } catch (Exception e) {
            MinecraftHUD.getInstance().getLogger().severe("加載 " + minecraftLocale + " 翻譯文本時出現錯誤 - " + e.getMessage());
        }
    }

    public static String getTranslation(String languageCode, String key) {
        String value = formatEntityName(key);
        Map<String, String> langMap = translations.get(languageCode);
        if (langMap != null) {
            return langMap.getOrDefault(value, key);
        }
        loadMinecraftLangJsonFromUrl(MinecraftHUD.getInstance().getServer().getMinecraftVersion(), languageCode);
        return value;
    }

    public static String toMinecraftLocale(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry().toLowerCase();
        return language + "_" + country;
    }

    private static String formatEntityName(String name) {
        if (name == null) {
            return null;
        }
        String lowerCaseName = name.toLowerCase();
        return lowerCaseName.replace(" ", "_");
    }
}
