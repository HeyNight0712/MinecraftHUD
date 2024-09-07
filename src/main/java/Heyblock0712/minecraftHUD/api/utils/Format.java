package Heyblock0712.minecraftHUD.api.utils;

public class Format {
    public static String formatEntityName(String name) {
        if (name == null) {
            return null;
        }
        String lowerCaseName = name.toLowerCase();
        return lowerCaseName.replace(" ", "_");
    }
}
