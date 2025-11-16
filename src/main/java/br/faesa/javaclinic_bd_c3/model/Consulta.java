package br.faesa.javaclinic_bd_c3.model;

import java.time.LocalDateTime;

public class Consulta {
    private long id;
    private String crmMedico;
    private String cpfPaciente;
    private Especialidade especialidade;
    private LocalDateTime data;

    // Construtor com todos os parâmetros
    public Consulta(long id, String crmMedico, String cpfPaciente, Especialidade especialidade, LocalDateTime data) {
        this.id = id;
        this.crmMedico = crmMedico;
        this.cpfPaciente = cpfPaciente;
        this.especialidade = especialidade;
        this.data = data;
    }

    // Métodos getters e setters
    public long getId() {
        return id;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String nomeMedico) {
        this.crmMedico = nomeMedico;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String nomePaciente) {
        this.cpfPaciente = nomePaciente;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    // Método toString para exibir informações sobre a consulta de forma legível
    @Override
    public String toString() {
        return "---------------------" +
                "\n ID: " + id +
                "\n CRM do médico: " + crmMedico +
                "\n CPF do paciente: " + cpfPaciente  +
                "\n Especialidade: " + especialidade.name() +
                "\n Data da consulta: " + data;
    }
}
