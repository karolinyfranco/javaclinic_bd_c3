package br.faesa.javaclinic_bd_c3.reports;

import br.faesa.javaclinic_bd_c3.conexion.ConexaoMongoDB;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Relatorios {
    ConexaoMongoDB conexao = new ConexaoMongoDB();

    /*
     * Exibe a contagem de consultas agrupada por especialidade.
     */
    public void gerarRelatorioConsultasPorEspecialidade() {
        try {
            conexao.connect();

            MongoCollection<Document> consultas = conexao.getDatabase().getCollection("consulta");
            System.out.println("\n===== RELATÓRIO: TOTAL DE CONSULTAS POR ESPECIALIDADE =====");
            System.out.printf("%-20s | %-10s\n", "ESPECIALIDADE", "Nº DE CONSULTAS");
            System.out.println("---------------------------------------");

            List<Document> pipeline = List.of(
                    new Document("$group", new Document("_id", "$especialidade")
                            .append("total", new Document("$sum", 1))),
                    new Document("$sort", new Document("total", -1))
            );

            for (Document doc : consultas.aggregate(pipeline)) {
                System.out.printf("%-20s | %-10d\n",
                        doc.getString("_id"),
                        doc.getInteger("total"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    /**
     * Exibe o histórico de consultas de um paciente específico.
     */
    public void gerarRelatorioHistoricoPaciente(Scanner sc) {
        System.out.print("\nDigite o CPF do paciente: ");
        String cpf = sc.nextLine();

        try {
            conexao.connect();

            MongoCollection<Document> consultas = conexao.getDatabase().getCollection("consulta");
            MongoCollection<Document> medicos = conexao.getDatabase().getCollection("medico");

            System.out.println("\n===== HISTÓRICO DO PACIENTE " + cpf + " =====");
            System.out.printf("%-20s | %-30s | %-20s\n", "DATA E HORA", "MÉDICO", "ESPECIALIDADE");
            System.out.println("--------------------------------------------------------------------------");

            for (Document c : consultas.find(new Document("cpf_paciente", cpf))) {

                Document medico = medicos.find(new Document("crm", c.getString("crm_medico"))).first();

                Date dataUtil = c.getDate("data");
                String data = dataUtil.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                String nomeMedico = medico != null ? medico.getString("nome") : "(Não encontrado)";
                String especialidade = medico != null ? medico.getString("especialidade") : "-";

                System.out.printf("%-20s | %-30s | %-20s\n",
                        data,
                        nomeMedico,
                        especialidade);
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    /**
     * Exibe a contagem de consultas para cada médico cadastrado.
     */
    public void gerarRelatorioConsultasPorMedico() {
        try {
            conexao.connect();

            MongoCollection<Document> consultas = conexao.getDatabase().getCollection("consulta");
            MongoCollection<Document> medicos = conexao.getDatabase().getCollection("medico");

            System.out.println("\n===== RELATÓRIO: TOTAL DE CONSULTAS POR MÉDICO =====");
            System.out.printf("%-10s | %-30s | %-10s\n", "CRM", "NOME DO MÉDICO", "Nº DE CONSULTAS");
            System.out.println("---------------------------------------------------------");

            List<Document> pipeline = List.of(
                    new Document("$group", new Document("_id", "$crm_medico")
                            .append("total", new Document("$sum", 1))),
                    new Document("$sort", new Document("total", -1))
            );

            for (Document d : consultas.aggregate(pipeline)) {
                String crm = d.getString("_id");

                long total = d.getInteger("total").longValue();

                Document medico = medicos.find(new Document("crm", crm)).first();
                String nome = medico != null ? medico.getString("nome") : "(Desconhecido)";

                System.out.printf("%-10s | %-30s | %-10d\n", crm, nome, total);
            }
        } catch (Exception e) {
            System.out.println("Erro no relatório: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    /**
     * Exibe uma lista detalhada de todas as consultas, juntando dados de médicos e pacientes.
     */
    public void gerarRelatorioConsultasDetalhadas() {
        try {
            conexao.connect();

            MongoCollection<Document> consultas = conexao.getDatabase().getCollection("consulta");
            MongoCollection<Document> medicos = conexao.getDatabase().getCollection("medico");
            MongoCollection<Document> pacientes = conexao.getDatabase().getCollection("paciente");

            System.out.println("\n===== RELATÓRIO DE CONSULTAS DETALHADAS =====");
            System.out.printf("%-5s | %-20s | %-25s | %-25s | %-15s\n",
                    "ID", "DATA E HORA", "PACIENTE", "MÉDICO", "ESPECIALIDADE");
            System.out.println("-----------------------------------------------------------------------------------------------------");

            for (Document c : consultas.find()) {

                Document paciente = pacientes.find(new Document("cpf", c.getString("cpf_paciente"))).first();
                Document medico = medicos.find(new Document("crm", c.getString("crm_medico"))).first();

                Date dataUtil = c.getDate("data");
                String data = dataUtil.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                Number idNum = (Number) c.get("id_consulta");
                String idStr = String.valueOf(idNum.intValue());

                String nomePaciente = paciente != null ? paciente.getString("nome") : "(Não encontrado)";
                String nomeMedico = medico != null ? medico.getString("nome") : "(Não encontrado)";
                String especialidade = medico != null ? medico.getString("especialidade") : "-";

                System.out.printf("%-5s | %-20s | %-25s | %-25s | %-15s\n",
                        idStr,
                        data,
                        nomePaciente,
                        nomeMedico,
                        especialidade);
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }
}
