package br.faesa.javaclinic_bd_c3.conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexaoMongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;

    private final String HOST = "localhost";
    private final int PORT = 27017;
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DATABASE_NAME = "javaclinic_mongo";

    public void connect() {
        try {
            String connectionString = String.format(
                    "mongodb://%s:%s@%s:%d/?authSource=admin",
                    USER, PASSWORD, HOST, PORT
            );

            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao MongoDB: " + e.getMessage());
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

