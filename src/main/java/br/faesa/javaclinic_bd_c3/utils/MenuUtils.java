package br.faesa.javaclinic_bd_c3.utils;

public class MenuUtils {
    public void mostrarMenuPrincipal() {
        SplashScreen.clearConsole();
        System.out.println("""
                ===========================================
                ||       JAVA CLINIC - MENU PRINCIPAL    ||
                ===========================================
                || 1. Gerenciar Médicos                  ||
                || 2. Gerenciar Pacientes                ||
                || 3. Gerenciar Consultas                ||
                || 4. Gerar Relatórios                   ||
                || 0. Sair                               ||
                ===========================================
                """);
    }

    public void mostrarMenuMedicos() {
        SplashScreen.clearConsole();
        System.out.println("""
                ===========================================
                ||            MENU MÉDICOS               ||
                ===========================================
                || 1. Inserir Médico                     ||
                || 2. Listar Médicos                     ||
                || 3. Atualizar Médico                   ||
                || 4. Excluir Médico                     ||
                || 0. Voltar ao Menu Principal           ||
                ===========================================
                """);
    }

    public void mostrarMenuPacientes() {
        SplashScreen.clearConsole();
        System.out.println("""
                ===========================================
                ||           MENU PACIENTES              ||
                ===========================================
                || 1. Inserir Paciente                   ||
                || 2. Listar Pacientes                   ||
                || 3. Atualizar Paciente                 ||
                || 4. Excluir Paciente                   ||
                || 0. Voltar ao Menu Principal           ||
                ===========================================
                """);
    }

    public void mostrarMenuConsultas() {
        SplashScreen.clearConsole();
        System.out.println("""
                ===========================================
                ||           MENU CONSULTAS              ||
                ===========================================
                || 1. Agendar Consulta                   ||
                || 2. Listar Consultas                   ||
                || 3. Cancelar Consulta                  ||
                || 0. Voltar ao Menu Principal           ||
                ===========================================
                """);
    }

    public void mostrarMenuRelatorios() {
        SplashScreen.clearConsole();
        System.out.println("""
                ===========================================
                ||           MENU RELATÓRIOS             ||
                ===========================================
                || 1. Consultas por Especialidade        ||
                || 2. Histórico de Paciente              ||
                || 3. Consultas por Médico               ||
                || 4. Consultas Detalhadas               ||
                || 0. Voltar ao Menu Principal           ||
                ===========================================
                """);
    }
}
