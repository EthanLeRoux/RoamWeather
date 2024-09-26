package simpleSettings;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import simpleusers.DBConnection;

public class SettingsDAO {

    private DBConnection conn;

    public SettingsDAO() {
        this.conn = new DBConnection();
        JOptionPane.showMessageDialog(null, "Connection established");
    }

    public Settings insertSettings(Settings settings) {
        String insertSQL = "INSERT INTO SETTINGS (USER_ID, SETTINGS_LANG, SETTINGS_THEME, SETTINGS_UNIT) VALUES (?, ?, ?, ?)";
        try (Connection connection = conn.getConn(); 
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {

            pstmt.setInt(1, settings.getUserId());
            pstmt.setString(2, settings.getLanguage());
            pstmt.setBoolean(3, settings.isTheme());
            pstmt.setString(4, settings.getUnit());
            
            int ok = pstmt.executeUpdate();

            if (ok > 0) {
                JOptionPane.showMessageDialog(null, "Settings saved successfully");
                return settings;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to insert settings");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public List<Settings> getAllSettings() {
        List<Settings> settingsList = new ArrayList<>();
        String selectSQL = "SELECT * FROM SETTINGS";

        try (Connection connection = conn.getConn();
             PreparedStatement pstmt = connection.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Settings settings = new Settings();
                settings.setSettingsId(rs.getInt("SETTINGS_ID"));
                settings.setUserId(rs.getInt("USER_ID"));
                settings.setLanguage(rs.getString("SETTINGS_LANG"));
                settings.setTheme(rs.getBoolean("SETTINGS_THEME"));
                settings.setUnit(rs.getString("SETTINGS_UNIT"));

                settingsList.add(settings);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }

        return settingsList;
    }

    public Settings getSettingsByID(int userId) {
        Settings settings = null;
        String selectSQL = "SELECT * FROM SETTINGS WHERE USER_ID = ?";

        try (Connection connection = conn.getConn();
             PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    settings = new Settings();
                    settings.setSettingsId(rs.getInt("SETTINGS_ID"));
                    settings.setUserId(rs.getInt("USER_ID"));
                    settings.setLanguage(rs.getString("SETTINGS_LANG"));
                    settings.setTheme(rs.getBoolean("SETTINGS_THEME"));
                    settings.setUnit(rs.getString("SETTINGS_UNIT"));
                } else {
                    JOptionPane.showMessageDialog(null, "No settings found for user ID: " + userId);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }

        return settings;
    }

    public void updateSettings(int settingsId, String language, boolean theme, String unit) {
        String updateString = "UPDATE SETTINGS SET SETTINGS_LANG = ?, SETTINGS_THEME = ?, SETTINGS_UNIT = ? WHERE SETTINGS_ID = ?";

        try (Connection connection = conn.getConn();
             PreparedStatement pstmt = connection.prepareStatement(updateString)) {

            pstmt.setString(1, language);
            pstmt.setBoolean(2, theme);  // Set the boolean directly
            pstmt.setString(3, unit);
            pstmt.setInt(4, settingsId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Settings updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update settings");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void removeSettings(int settingsId) {
        String deleteSQL = "DELETE FROM SETTINGS WHERE SETTINGS_ID = ?";

        try (Connection connection = conn.getConn();
             PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, settingsId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public int extractSettingsIdFromString(String settingsString) {
        if (settingsString == null) {
            throw new IllegalArgumentException("Input string is null");
        }

        String settingsIdPrefix = "settingsId=";
        int startIndex = settingsString.indexOf(settingsIdPrefix);

        if (startIndex == -1) {
            throw new IllegalArgumentException("String does not contain settingsId=");
        }

        int endIndex = settingsString.indexOf(", userId=", startIndex);
        if (endIndex == -1) {
            endIndex = settingsString.length();
        }

        String settingsIdString = settingsString.substring(startIndex + settingsIdPrefix.length(), endIndex).trim();

        try {
            return Integer.parseInt(settingsIdString);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("settingsId is not a valid integer", ex);
        }
    }
}
