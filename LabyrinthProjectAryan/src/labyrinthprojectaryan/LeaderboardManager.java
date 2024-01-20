package labyrinthprojectaryan;

import java.sql.*;

public class LeaderboardManager {
    public static void addToLeaderboard(String playerName, int winCount) {
        try (Connection connection = DriverManager.getConnection(LeaderboardGUI.JDBC_URL, LeaderboardGUI.USER, LeaderboardGUI.PASSWORD)) {
            String query = "INSERT INTO leaderboard (player_name, win_count) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, playerName);
                preparedStatement.setInt(2, winCount);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayLeaderboard() {
        LeaderboardGUI leaderboardGUI = new LeaderboardGUI();
        leaderboardGUI.setVisible(true);
    }
}
