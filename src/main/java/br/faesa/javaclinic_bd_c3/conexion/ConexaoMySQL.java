package br.faesa.javaclinic_bd_c3.conexion;

import java.sql.*;

public class ConexaoMySQL {
    private Connection conn;
    private final String URL = "jdbc:mysql://localhost:3307/javaclinic";
    private final String USER = "javaclinic";
    private final String PASSWORD = "javaclinic";

    public void connect() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
