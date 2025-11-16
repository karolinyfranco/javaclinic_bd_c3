package br.faesa.javaclinic_bd_c3.controller;

import br.faesa.javaclinic_bd_c3.conexion.ConexaoMongoDB;
import br.faesa.javaclinic_bd_c3.model.Consulta;
import br.faesa.javaclinic_bd_c3.model.Especialidade;
import br.faesa.javaclinic_bd_c3.utils.ValidatorUtils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControllerConsulta {
    ConexaoMongoDB conexao = new ConexaoMongoDB();
    ValidatorUtils validator = new ValidatorUtils();

    private MongoCollection<Document> getCollection() {
        return conexao.getDatabase().getCollection("consulta");
    }

    // Método para gerar o próximo ID
    private long getProximoId() {
        MongoCollection<Document> counters = conexao.getDatabase().getCollection("counters");
        MongoCollection<Document> consultas = getCollection();

        Document counter = counters.find(Filters.eq("_id", "consulta_id")).first();

        if (counter == null) {
            // Contador não existe - criar baseado no maior ID existente
            long maxId = 0;
            for (Document doc : consultas.find()) {
                Object idObj = doc.get("id_consulta");
                if (idObj instanceof Number) {
                    long currentId = ((Number) idObj).longValue();
                    if (currentId > maxId) {
                        maxId = currentId;
                    }
                }
            }
        }

        long proximoId = counter.getLong("seq") + 1;
        counters.updateOne(
                Filters.eq("_id", "consulta_id"),
                new Document("$set", new Document("seq", proximoId))
        );

        return proximoId;
    }

    public void inserir(Consulta consulta) {
        try {
            conexao.connect();

            if (!validator.existePaciente(conexao, consulta.getCpfPaciente())) {
                System.out.println("Paciente com CPF " + consulta.getCpfPaciente() + " não encontrado.");
                return;
            }

            if (!validator.existeMedico(conexao, consulta.getCrmMedico())) {
                System.out.println("Médico com CRM " + consulta.getCrmMedico() + " não encontrado.");
                return;
            }

            // Gerar próximo ID automaticamente
            long novoId = getProximoId();

            // Converter LocalDateTime para Date
            Date data = Date.from(consulta.getData().atZone(ZoneId.systemDefault()).toInstant());

            Document doc = new Document("id_consulta", novoId)
                    .append("cpf_paciente", consulta.getCpfPaciente())
                    .append("crm_medico", consulta.getCrmMedico())
                    .append("especialidade", consulta.getEspecialidade().name())
                    .append("data", data); // Salvando como Date, não String

            getCollection().insertOne(doc);

            System.out.println("Consulta agendada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao agendar consulta: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public void excluir(long id) {
        try {
            conexao.connect();

            if (!validator.existeConsulta(conexao, id)) {
                System.out.println("Consulta de ID " + id + " não encontrada.");
                return;
            }

            getCollection().deleteOne(Filters.eq("id_consulta", id));

            System.out.println("Consulta cancelada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir consulta: " + e.getMessage());
        } finally {
            conexao.close();
        }
    }

    public List<Consulta> listar() {
        List<Consulta> consultas = new ArrayList<>();

        try {
            conexao.connect();

            for (Document doc : getCollection().find()) {
                Number idNum = (Number) doc.get("id_consulta");
                long idConsulta = idNum.longValue();

                // Converter Date para LocalDateTime
                Date data = doc.getDate("data");
                LocalDateTime dataHora = LocalDateTime.ofInstant(
                        data.toInstant(),
                        ZoneId.systemDefault()
                );

                Consulta c = new Consulta(
                        idConsulta,
                        doc.getString("crm_medico"),
                        doc.getString("cpf_paciente"),
                        Especialidade.valueOf(doc.getString("especialidade")),
                        dataHora
                );

                consultas.add(c);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
        } finally {
            conexao.close();
        }

        return consultas;
    }
}
