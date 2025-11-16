package br.faesa.javaclinic_bd_c3.model;

public enum Especialidade {
    ORTOPEDIA,
    PEDIATRIA,
    CARDIOLOGIA,
    GINECOLOGIA,
    DERMATOLOGIA,
    OFTALMOLOGIA;

    // Método estático para converter uma String para o valor correspondente do enum
    public static Especialidade fromString(String value) {
        try {
            // Converte o valor para maiúsculas e tenta obter o valor correspondente do enum
            return Especialidade.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Lança uma exceção caso o valor fornecido não seja válido
            throw new IllegalArgumentException("Especialidade inválida.");
        }
    }
}
