package br.faesa.javaclinic_bd_c3.utils;

import br.faesa.javaclinic_bd_c3.conexion.ConexaoMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class ValidatorUtils {
    public boolean existePaciente(ConexaoMongoDB conexao, String cpf) {
        try {
            MongoCollection<Document> col = conexao.getDatabase().getCollection("paciente");
            Document doc = col.find(Filters.eq("cpf", cpf)).first();
            return doc != null;
        } catch (Exception e) {
            System.out.println("Erro ao validar paciente: " + e.getMessage());
            return false;
        }
    }

    public boolean existeMedico(ConexaoMongoDB conexao, String crm) {
        try {
            MongoCollection<Document> col = conexao.getDatabase().getCollection("medico");
            Document doc = col.find(Filters.eq("crm", crm)).first();
            return doc != null;
        } catch (Exception e) {
            System.out.println("Erro ao validar médico: " + e.getMessage());
            return false;
        }
    }

    public boolean existeConsulta(ConexaoMongoDB conexao, long id) {
        try {
            MongoCollection<Document> col = conexao.getDatabase().getCollection("consulta");
            Document doc = col.find(Filters.eq("id_consulta", id)).first();
            return doc != null;
        } catch (Exception e) {
            System.out.println("Erro ao validar consulta: " + e.getMessage());
            return false;
        }
    }
}
