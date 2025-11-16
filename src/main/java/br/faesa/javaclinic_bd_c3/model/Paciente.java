package br.faesa.javaclinic_bd_c3.model;

public class Paciente extends Pessoa{
    private String cpf;

    // Construtor com todos os parâmetros
    public Paciente(String nome, String email, String endereco, String telefone, String cpf) {
        super(nome, email, endereco, telefone);
        this.cpf = cpf;
    }

    // Métodos getters e setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Método toString para representar o paciente como uma string
    @Override
    public String toString() {
        return "---------------------" + "\n"
                + super.toString() +
                "CPF: " + cpf;
    }
}
