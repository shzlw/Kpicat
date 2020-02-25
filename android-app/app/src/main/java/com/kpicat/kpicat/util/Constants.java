package com.kpicat.kpicat.util;

import com.kpicat.kpicat.model.Configuration;

public final class Constants {

    private Constants() {}

    public static final String SHARED_PREFERENCES = "kpicat";
    public static final String MOBILE_KEY = "mobile_key";

    public static final String SPLASH_FONT_COLOR = "splash_font_color";
    public static final String SPLASH_TEXT = "splash_text";
    public static final String SPLASH_BG_COLOR = "splash_bg_color";


    public static final Configuration DEFAULT_CONFIG = new Configuration();
    static {
        DEFAULT_CONFIG.setSidebarBgColor("#00a8e8");
        DEFAULT_CONFIG.setSidebarFontColor("#FFFFFF");
        DEFAULT_CONFIG.setToolbarBgColor("#a9a9a9");
        DEFAULT_CONFIG.setToolbarFontColor("#FFFFFF");
        DEFAULT_CONFIG.setSplashFontColor("#FFFFFF");
        DEFAULT_CONFIG.setSplashImage(null);
        DEFAULT_CONFIG.setSplashText("KpiCat");
        DEFAULT_CONFIG.setSplashBgColor("#00a8e8");
    }
}
