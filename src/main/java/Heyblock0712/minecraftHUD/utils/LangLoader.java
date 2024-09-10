package Heyblock0712.minecraftHUD.utils;

import Heyblock0712.minecraftHUD.MinecraftHUD;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class LangLoader {
    private static final Map<String, String> langs = new HashMap<>();

    private static final String[] PREFIXES = {
            "block.minecraft",
            "entity.minecraft"
    };

    private static boolean startsWithAnyPrefix(String key, String[] prefixes) {
        for (String prefix : prefixes) {
            if (key.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static String extractSimplifiedName(String fullKey) {
        // 将 key 分割成部分
        String[] parts = fullKey.split("\\.");

        if (parts.length > 1 && isNumeric(parts[parts.length - 1])) {
            return parts[parts.length - 2] + "." + parts[parts.length - 1];
        }
        return parts[parts.length - 1];
    }

    // 判断字符串是否为数字
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void loadLangJsonFromUrl(String uriPath) {
        try {
            URI uri = new URI(uriPath);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String fullKey = entry.getKey();
                if (startsWithAnyPrefix(fullKey, PREFIXES)) {
                    String simplifiedName = extractSimplifiedName(fullKey);
                    String value = entry.getValue().getAsString();
                    langs.put(simplifiedName, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String findFullKey(String simplifiedName) {
        String name = langs.get(simplifiedName);
        if (name != null) {
            return name;
        }
        return simplifiedName;
    }
}
