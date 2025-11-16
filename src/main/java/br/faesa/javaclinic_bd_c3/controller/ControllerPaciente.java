package br.faesa.javaclinic_bd_c3.controller;

import br.faesa.javaclinic_bd_c3.conexion.ConexaoMongoDB;
import br.faesa.javaclinic_bd_c3.model.Paciente;
import br.faesa.javaclinic_bd_c3.utils.ValidatorUtils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ControllerPaciente {
    ConexaoMongoDB conexao = new ConexaoMongoDB();
    ValidatorUtils validator = new ValidatorUtils();

    private MongoCollection<Document> getCollection() {
        return conexao.getDatabase().getCollection("paciente");
    }

    public void inserir(Paciente paciente) {
        try {
            conexao.connect();

            Document doc = new Document("cpf", paciente.getCpf())
                    .append("nome", paciente.getNome())
                    .append("email", paciente.getEmail())
                    .append("telefone", paciente.getTelefone())
                    .append("endereco", paciente.getEndereco());

            getCollection().insertOne(doc);
            System.out.println("Paciente inserido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir paciente: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public void atualizar(String cpf, String campo, String novoValor) {
        try {
            conexao.connect();

            if (!validator.existePaciente(conexao, cpf)) {
                System.out.println("Paciente com CPF " + cpf + " não encontrado.");
                return;
            }

            getCollection().updateOne(
                    Filters.eq("cpf", cpf),
                    Updates.set(campo, novoValor)
            );

            System.out.println("Paciente atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar paciente: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public void excluir(String cpf) {
        try {
            conexao.connect();

            if (!validator.existePaciente(conexao, cpf)) {
                System.out.println("Paciente com CPF " + cpf + " não encontrado.");
                return;
            }

            getCollection().deleteOne(Filters.eq("cpf", cpf));

            System.out.println("Paciente excluído com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir paciente: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public List<Paciente> listar() {
        List<Paciente> pacientes = new ArrayList<>();

        try {
            conexao.connect();
            for (Document doc : getCollection().find()) {
                Paciente paciente = new Paciente(
                        doc.getString("nome"),
                        doc.getString("email"),
                        doc.getString("endereco"),
                        doc.getString("telefone"),
                        doc.getString("cpf")
                );
                pacientes.add(paciente);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar pacientes: " + e.getMessage());
        } finally {
            conexao.close();
        }

        return pacientes;
    }

    public boolean pacienteTemConsulta(String cpf) {
        try {
            conexao.connect();

            MongoCollection<Document> consultas =
                    conexao.getDatabase().getCollection("consulta");
            Document doc = consultas.find(Filters.eq("cpf_paciente", cpf)).first();

            return doc != null;
        } catch (Exception e) {
            System.out.println("Erro ao verificar consultas: " + e.getMessage());
        } finally {
            conexao.close();
        }
        return false;
    }
}
