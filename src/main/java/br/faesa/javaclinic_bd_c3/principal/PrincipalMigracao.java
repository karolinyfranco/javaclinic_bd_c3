package br.faesa.javaclinic_bd_c3.principal;

import br.faesa.javaclinic_bd_c3.conexion.MigrarMySQLParaMongo;

public class PrincipalMigracao {
    public static void main(String[] args) {
        System.out.println("Iniciando migração MySQL → MongoDB...");

        MigrarMySQLParaMongo migracao = new MigrarMySQLParaMongo();
        migracao.executarMigracao();

        System.out.println("Migração concluída com sucesso!");
    }
}

