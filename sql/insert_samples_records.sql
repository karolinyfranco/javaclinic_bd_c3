USE javaclinic;

/* INSERE DADOS NA TABELA DE MÉDICOS */
INSERT INTO medico (crm, nome, email, especialidade, telefone, endereco) VALUES
('1001', 'Dr. João Silva', 'joao.silva@clinic.com', 'CARDIOLOGIA', '(27) 99999-1111', 'Rua das Flores-123-Centro-Vitória-ES'),
('1002', 'Dra. Maria Souza', 'maria.souza@clinic.com', 'PEDIATRIA', '(27) 98888-2222', 'Rua da Paz-456-Jardim-Cariacica-ES'),
('1003', 'Dr. Ricardo Lima', 'ricardo.lima@clinic.com', 'DERMATOLOGIA', '(27) 97777-3333', 'Av. Central-789-Boa Vista-Vila Velha-ES'),
('1004', 'Dra. Fernanda Torres', 'fernanda.torres@clinic.com', 'ORTOPEDIA', '(27) 96666-4444', 'Rua Alegre-321-Industrial-Serra-ES'),
('1005', 'Dr. Paulo Mendes', 'paulo.mendes@clinic.com', 'GINECOLOGIA', '(27) 95555-5555', 'Rua Bahia-654-Residencial-Vitória-ES');

/* INSERE DADOS NA TABELA DE PACIENTES */
INSERT INTO paciente (cpf, nome, email, telefone, endereco) VALUES
('12345678901', 'Ana Clara', 'ana.clara@gmail.com', '(27) 99991-1111', 'Rua das Flores-12-Centro-Vitória-ES'),
('23456789012', 'Carlos Eduardo', 'carlos.edu@gmail.com', '(27) 99992-2222', 'Rua das Rosas-34-Jardim-Cariacica-ES'),
('34567890123', 'Fernanda Alves', 'fernanda.alves@gmail.com', '(27) 99993-3333', 'Rua da Paz-56-Boa Vista-Vila Velha-ES'),
('45678901234', 'Marcos Vinícius', 'marcos.vinicius@gmail.com', '(27) 99994-4444', 'Av. Central-78-Industrial-Serra-ES'),
('56789012345', 'Patrícia Gomes', 'patricia.gomes@gmail.com', '(27) 99995-5555', 'Rua Alegre-90-Residencial-Vitória-ES');

COMMIT;