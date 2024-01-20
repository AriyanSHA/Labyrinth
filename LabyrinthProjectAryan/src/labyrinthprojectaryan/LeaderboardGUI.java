package labyrinthprojectaryan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LeaderboardGUI extends JFrame {
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/labyrinth";
    public static final String USER = "root";
    public static final String PASSWORD = "123QWEasd";

    public LeaderboardGUI() {
        setTitle("Leaderboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 400);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Player Name");
        model.addColumn("Win Count");

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "SELECT * FROM leaderboard ORDER BY win_count DESC";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        String playerName = resultSet.getString("player_name");
                        int winCount = resultSet.getInt("win_count");
                        model.addRow(new Object[]{playerName, winCount});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
