# 🏥 JavaClinic — Sistema de Gerenciamento de Consultas Médicas

## Sobre o Projeto

O JavaClinic é um sistema de gerenciamento de consultas médicas desenvolvido em Java, com persistência de dados em MongoDB e estrutura organizada segundo boas práticas de separação em pacotes.

O objetivo do projeto é simular um sistema de clínica médica, permitindo o cadastro de médicos e pacientes, e o agendamento de consultas. O sistema foi migrado de um banco de dados relacional MySQL para o MongoDB, demonstrando a transição entre paradigmas de bancos de dados.

Este projeto foi desenvolvido como parte da disciplina de Banco de Dados, com Java + MongoDB utilizando Docker e Maven.


## Vídeo do Funcionamento do Projeto

Link para o vídeo no Youtube: https://www.youtube.com/watch?v=DOF1uYX27R4


## Funcionalidades

- Cadastro, atualização, listagem e exclusão de Médicos
- Cadastro, atualização, listagem e exclusão de Pacientes
- Agendamento e cancelamento de Consultas
- Geração de relatórios:
    - Total de consultas por especialidade
    - Histórico de consultas por paciente
    - Total de consultas por médico
    - Consultas detalhadas (com dados de médico e paciente)

## Tecnologias Utilizadas

- **Linguagem:** Java 21+
- **Gerenciador de Dependências:** Maven
- **Banco de Dados:** MongoDB 6.0
- **Banco de Dados Origem:** MySQL 8.0 (projeto anterior)
- **Ambiente de Banco:** Docker
- **Interface de Gerenciamento do Banco:** Mongo Express e phpMyAdmin
- **Drivers:** MySQL Connector/J e MongoDB Driver

## Estrutura NoSQL (MongoDB)
O modelo feito no projeto anterior (MySQL) foi migrado para MongoDB mantendo a mesma estrutura lógica,
porém adaptado para o paradigma de documentos:

- **Coleção medico**: Armazena dados dos médicos
- **Coleção paciente**: Armazena dados dos pacientes
- **Coleção consulta**: Armazena consultas com referências (crm_medico, cpf_paciente)

### Considerações Técnicas
- MongoDB não possui auto-incremento nativo como MySQL
- Implementada coleção auxiliar `counters` para gerenciar sequências de IDs
- Relacionamentos são mantidos através de referências (crm_medico, cpf_paciente)
- Datas convertidas de DATETIME (MySQL) para ISODate (MongoDB)


## Estrutura de Pacotes

```
javaclinic_bd_c3/
├── src/
│   ├── main/java/br/faesa/javaclinic_bd_c3/
│        ├── model/              # Classes de entidade
│        ├── controller/         # Controle das operações CRUD
│        ├── conexion/           # Conexão com os bancos e migração de dados
│        ├── reports/            # Geração de relatórios
│        ├── utils/              # Utilitários gerais
│        └── principal/          # Ponto de entrada da aplicação
│
├── diagrams/                    # Diagrama relacional do projeto
│
├── mysql_data/                 # Dados persistidos do MySQL
├── mongo_data/                 # Dados persistidos do MongoDB
├── sql/                        # Scripts de inicialização
├── docker-compose.yml          # Configuração dos containers
├── pom.xml                     # Configurações Maven e dependências
└── README.md
```

## Executando o Projeto em Linux

O projeto já inclui um arquivo docker-compose.yml para subir o ambiente completo do banco de dados.

### 1️⃣ Pré-requisitos

Verifique se você tem tudo instalado (e funcionando):

```bash
  java -version             # deve mostrar "openjdk 21" ou superior
  mvn -version              # deve mostrar a versão do Maven
  docker --version          # deve mostrar a versão do Docker
  docker compose version    # deve mostrar a versão do Docker Compose
```

Se algum comando não for reconhecido, instale antes de continuar:

```bash
  sudo apt update
  sudo apt install openjdk-21-jdk maven docker.io -y
  docker-compose-plugin -y
```

### 2️⃣ Subir o ambiente

No terminal, dentro da pasta raiz do projeto:

```bash
  docker compose up -d
```
Isso criará **4 containers**:
- **MySQL** (porta 3307)
- **phpMyAdmin** (porta 8080)
- **MongoDB** (porta 27017)
- **Mongo Express** (porta 8082)

### 3️⃣ Acessar as Interfaces de Gerenciamento

**phpMyAdmin (MySQL):**
- 🌐 URL: http://localhost:8080

**Mongo Express (MongoDB):**
- 🌐 URL: http://localhost:8082
- 👤 Usuário: `admin`
- 🔑 Senha: `admin`

### 4️⃣ Scripts SQL

Os scripts estão em /sql e são automaticamente executados na primeira vez que o container é criado:

- create_tables_javaclinic.sql — Criação do banco e tabelas
- insert_samples_records.sql — Dados iniciais de médicos e pacientes
- insert_samples_related_records.sql — Inserção de consultas

### ️5️⃣ Compilar o Projeto

Ainda na pasta raiz do projeto (onde está o pom.xml), rode:

```bash
  mvn clean compile
```

Isso baixa as dependências e compila o código-fonte.

### 6️⃣ Migrar Dados do MySQL para MongoDB

Execute a classe de migração **UMA ÚNICA VEZ**:

```bash
mvn exec:java -Dexec.mainClass="br.faesa.javaclinic_bd_c3.principal.PrincipalMigracao"
```

**O que acontece:**
- Conecta no MySQL (localhost:3307)
- Lê todas as tabelas (médicos, pacientes, consultas)
- Cria as coleções no MongoDB
- Migra todos os dados preservando IDs e datas
- Exibe mensagem de sucesso

⚠️ **IMPORTANTE:** Execute esta migração apenas UMA VEZ. Executar novamente recria as coleções e perde dados novos.

### 7️⃣ Executar a Aplicação Principal

Para executar a classe Principal e iniciar a aplicação, rode:

```bash
  mvn exec:java -Dexec.mainClass="br.faesa.javaclinic_bd_c3.principal.Principal"
```

**Na primeira execução após a migração:**
```
Configurando sistema pela primeira vez...
Sistema configurado com sucesso!
Próximo ID de consulta: 4
```

O sistema:
- Detecta que é a primeira vez
- Configura o contador de IDs automaticamente, pois o MongoDB não possui auto-incremento nativo de IDs
- Está pronto para uso!

**Execuções seguintes:**
- Inicia normalmente sem mensagens de configuração

### 8️⃣ Parar o banco e limpar tudo

Depois que terminar de testar:

```bash
  docker compose down
```

Isso para todos os containers mas **mantém os dados**.

Para remover também os dados:

```bash
docker compose down -v
rm -rf mysql_data mongo_data
```

## Dependências (Maven)

No arquivo pom.xml, as dependências do MySQL Connector/J e do MongoDB Driver foram adicionadas:

```
<dependencies>
    <!-- Driver MySQL (para migração de dados) -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.3.0</version>
    </dependency>
    
    <!-- Driver MongoDB (banco principal) -->
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>5.6.1</version>
    </dependency>
</dependencies>
```
# Autores
Projeto desenvolvido por alunos do curso de Ciência da Computação — FAESA, para a disciplina de Banco de Dados (C3). Todos os integrantes do grupo contribuiram para o desenvolvimento da aplicação.

- *Ana Luiza Menelli Taylor* [(@analuizataylor)](https://github.com/analuizataylor): Responsável pela implementação da Tela de Splash Screen e Menus de navegação.
- *Felipe Valério Rocha* [(@sabugoestrela)](https://github.com/sabugoestrela): Responsável pela elaboração dos Relatórios e Documentação do projeto.
- *Gustavo Rissoli Vicente* [(@GustavoRissoli)](https://github.com/GustavoRissoli): Responsável pela edição do Vídeo e criação dos Diagramas.
- *Karoliny Vicente Franco* [(@karolinyfranco)](https://github.com/karolinyfranco): Responsável pelo desenvolvimento dos Controllers da aplicação, Integração com o Banco de dados e  Estruturação do código.
