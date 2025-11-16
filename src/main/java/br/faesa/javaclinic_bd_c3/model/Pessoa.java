package br.faesa.javaclinic_bd_c3.model;

public class Pessoa {
    private String nome;
    private String email;
    private String endereco;
    private String telefone;

    // Construtor com todos os parâmetros
    public Pessoa(String nome, String email, String endereco, String telefone) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // Métodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Método toString que gera uma representação textual da pessoa
    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Endereço: " + endereco + "\n" +
                "Telefone: " + telefone + "\n";
    }
}
