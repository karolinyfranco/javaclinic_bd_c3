package br.faesa.javaclinic_bd_c3.utils;

import br.faesa.javaclinic_bd_c3.conexion.ConexaoMongoDB;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class SplashScreen {
    ConexaoMongoDB conexao = new ConexaoMongoDB();

    private final String PROFESSOR = "Prof. M.Sc. Howard Roatti";
    private final String DISCIPLINA = "Banco de Dados";
    private final String SEMESTRE = "2025/2";

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 10; i++) System.out.println();
        }
    }

    private long contarDocumentos(String colecao) {
        try {
            conexao.connect();
            MongoCollection<Document> col = conexao.getDatabase().getCollection(colecao);
            return col.countDocuments();
        } catch (Exception e) {
            System.out.println("Erro ao contar documentos da coleção " + colecao + ": " + e.getMessage());
            return 0;
        } finally {
            conexao.close();
        }
    }

    private long getTotalPacientes() {
        return contarDocumentos("paciente");
    }

    private long getTotalMedicos() {
        return contarDocumentos("medico");
    }

    private long getTotalConsultas() {
        return contarDocumentos("consulta");
    }

    public void mostrarTela() {
        clearConsole();

        long totalPacientes = getTotalPacientes();
        long totalMedicos = getTotalMedicos();
        long totalConsultas = getTotalConsultas();

        String tela = """
                ########################################################
                #                                                      #
                #                      JAVACLINIC                      #
                #        SISTEMA DE GESTÃO DE CONSULTAS MÉDICAS        #
                #                                                      #
                #  TOTAL DE REGISTROS:                                 #
                #                                                      #
                #    1 - PACIENTES:     %29d  #
                #    2 - MÉDICOS:       %29d  #
                #    3 - CONSULTAS:     %29d  #
                #                                                      #
                #  CRIADO POR: %-39s #
                #              %-39s #
                #                                                      #
                #  PROFESSOR:  %-39s #
                #                                                      #
                #  DISCIPLINA: %-39s #
                #              %-39s #
                ########################################################
                """.formatted(
                totalPacientes,
                totalMedicos,
                totalConsultas,
                "Karoliny Franco, Ana Luiza Menelli,",
                "Gustavo Rissoli e Felipe Valério",
                PROFESSOR,
                DISCIPLINA,
                SEMESTRE
        );

        System.out.println(tela);
        System.out.println("\nCarregando sistema...");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

