package br.faesa.javaclinic_bd_c3.conexion;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MigrarMySQLParaMongo {
    private static final List<String> TABELAS = Arrays.asList(
            "medico",
            "paciente",
            "consulta"
    );
    private final ConexaoMySQL mysql = new ConexaoMySQL();
    private final ConexaoMongoDB mongo = new ConexaoMongoDB();

    // Cria coleções no MongoDB
    private void criarColecoes(boolean recriar) {
        MongoDatabase db = mongo.getDatabase();
        for (String tabela : TABELAS) {
            boolean existe = db.listCollectionNames()
                    .into(new ArrayList<>())
                    .contains(tabela);

            if (existe && recriar) {
                db.getCollection(tabela).drop();
                db.createCollection(tabela);
                System.out.println("Coleção recriada: " + tabela);
            } else if (!existe) {
                db.createCollection(tabela);
                System.out.println("Coleção criada: " + tabela);
            }
        }
    }

    // Extrai registros SQL → insere no MongoDB
    private void extrairEInserir() {
        mysql.connect();
        MongoDatabase db = mongo.getDatabase();

        for (String tabela : TABELAS) {
            try {
                String query = "SELECT * FROM " + tabela;
                PreparedStatement ps = mysql.getConn().prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                MongoCollection<Document> collection = db.getCollection(tabela);

                System.out.println("Migrando tabela: " + tabela);

                while (rs.next()) {
                    Document doc = new Document();
                    ResultSetMetaData meta = rs.getMetaData();

                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        String coluna = meta.getColumnName(i);
                        Object valor = rs.getObject(i);

                        doc.append(coluna, valor);
                    }
                    collection.insertOne(doc);
                }
                System.out.println("Dados inseridos na coleção: " + tabela);
            } catch (SQLException e) {
                System.out.println("Erro ao extrair tabela " + tabela + ": " + e.getMessage());
            }
        }

        mysql.close();
    }

    public void executarMigracao() {
        mongo.connect();
        criarColecoes(true);
        extrairEInserir();
        mongo.close();
    }
}
