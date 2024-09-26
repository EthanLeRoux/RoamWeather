package simpleSettings;

public class Settings {
    private int settingsId;
    private int userId;
    private String language;
    private boolean theme; // This indicates if the theme is dark (true) or light (false)
    private String unit;

    public Settings(boolean theme, String language, String unit, int userId) {
        this.theme = theme;
        this.language = language;
        this.unit = unit;
        this.userId = userId;
    }

    public Settings() {}

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isTheme() {
        return theme;
    }

    public void setTheme(boolean theme) {
        this.theme = theme;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "settingsId=" + settingsId +
                ", userId=" + userId +
                ", language='" + language + '\'' +
                ", theme=" + theme +
                ", unit='" + unit + '\'' +
                '}';
    }
}
