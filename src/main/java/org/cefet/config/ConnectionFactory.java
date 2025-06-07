package org.cefet.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    DataBaseConfig.getDbUrl(),
                    DataBaseConfig.getDbUser(),
                    DataBaseConfig.getDbPassword()
            );
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado no classpath.");
            System.err.println("Detalhes: " + e.getMessage());
            throw new SQLException("Driver JDBC do MySQL não encontrado. Verifique se o JAR está em WEB-INF/lib.", e);
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão com o banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public static void closeConnection(Connection conn, java.sql.Statement stmt) {
        closeConnection(conn);
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar Statement: " + e.getMessage());
            }
        }
    }

    public static void closeConnection(Connection conn, java.sql.Statement stmt, java.sql.ResultSet rs) {
        closeConnection(conn, stmt);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
            }
        }
    }
}