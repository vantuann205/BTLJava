package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://ep-winter-thunder-a1e9lo0j-pooler.ap-southeast-1.aws.neon.tech/DBJAVA?sslmode=require&connect_timeout=10";
    private static final String USER = "DBJAVA_owner";
    private static final String PASSWORD = "npg_Jig08jpEuSrQ";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found: " + e.getMessage());
        }
    }

    public void saveGameResult(String winner) throws SQLException {
        String sql = "INSERT INTO GameHistory (GameDateTime, Winner) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(2, winner);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to save game result: " + e.getMessage());
        }
    }
}