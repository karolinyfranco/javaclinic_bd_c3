package br.faesa.javaclinic_bd_c3.controller;

import br.faesa.javaclinic_bd_c3.conexion.ConexaoMongoDB;
import br.faesa.javaclinic_bd_c3.model.Medico;
import br.faesa.javaclinic_bd_c3.model.Especialidade;
import br.faesa.javaclinic_bd_c3.utils.ValidatorUtils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ControllerMedico {
    ConexaoMongoDB conexao = new ConexaoMongoDB();
    ValidatorUtils validator = new ValidatorUtils();

    private MongoCollection<Document> getCollection() {
        return conexao.getDatabase().getCollection("medico");
    }

    public void inserir(Medico medico) {
        try {
            conexao.connect();

            Document doc = new Document()
                    .append("crm", medico.getCrm())
                    .append("nome", medico.getNome())
                    .append("email", medico.getEmail())
                    .append("especialidade", medico.getEspecialidade().name())
                    .append("telefone", medico.getTelefone())
                    .append("endereco", medico.getEndereco());

            getCollection().insertOne(doc);
            System.out.println("Médico inserido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir médico: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public void atualizar(String crm, String campo, String novoValor) {
        try {
            conexao.connect();

            if (!validator.existeMedico(conexao, crm)) {
                System.out.println("Médico com CRM " + crm + " não encontrado.");
                return;
            }

            getCollection().updateOne(Filters.eq("crm", crm), Updates.set(campo, novoValor));

            System.out.println("Médico atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar médico: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public void excluir(String crm) {
        try {
            conexao.connect();

            if (!validator.existeMedico(conexao, crm)) {
                System.out.println("Médico com CRM " + crm + " não encontrado.");
                return;
            }

            // Verificar se tem consultas (equivalente ao FK)
            MongoCollection<Document> consultas = conexao.getDatabase().getCollection("consulta");
            Document docConsulta = consultas.find(Filters.eq("crm_medico", crm)).first();

            if (docConsulta != null) {
                System.out.println("Não é possível excluir. O médico possui consultas.");
                return;
            }

            getCollection().deleteOne(Filters.eq("crm", crm));

            System.out.println("Médico excluído com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir médico: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public List<Medico> listar() {
        List<Medico> lista = new ArrayList<>();

        try {
            conexao.connect();
            for (Document doc : getCollection().find()) {
                Medico m = new Medico(
                        doc.getString("nome"),
                        doc.getString("email"),
                        doc.getString("endereco"),
                        doc.getString("telefone"),
                        doc.getString("crm"),
                        Especialidade.valueOf(doc.getString("especialidade"))
                );
                lista.add(m);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar médicos: " + e.getMessage());
        } finally {
            conexao.close();
        }
        return lista;
    }
}