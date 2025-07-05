CREATE TABLE usuario (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único do usuário',
    nome VARCHAR(100) NOT NULL COMMENT 'Nome completo do usuário',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'E-mail único para login',
    senha VARCHAR(255) NOT NULL COMMENT 'Hash da senha (armazenamento seguro)',
    data_nascimento DATE NOT NULL COMMENT 'Data de nascimento (obrigatória)',
    tipo ENUM('COMUM', 'ADMIN') DEFAULT 'COMUM' NOT NULL COMMENT 'Tipo de perfil com valor padrão',
    saldo DECIMAL(10,2) DEFAULT 0.00 NOT NULL COMMENT 'Saldo financeiro inicial',
    data_criacao TIMESTAMP DEFAULT now() COMMENT 'Registro de criação',
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Última atualização',
    INDEX idx_email (email)
) COMMENT='Tabela de usuários do sistema';

CREATE TABLE transacao (
    transacao_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único da transação',
    usuario_id INT NOT NULL COMMENT 'Referência ao usuário associado',
    tipo ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA', 'PAGAMENTO') NOT NULL COMMENT 'Categoria da operação',
    valor DECIMAL(10,2) NOT NULL CHECK (valor > 0) COMMENT 'Valor monetário positivo',
    data_hora TIMESTAMP DEFAULT now() COMMENT 'Data e hora completa da transação',
    descricao VARCHAR(255) DEFAULT 'Sem descrição' COMMENT 'Detalhes adicionais',
    status ENUM('PENDENTE', 'CONCLUIDO', 'CANCELADO') DEFAULT 'CONCLUIDO' NOT NULL COMMENT 'Estado da operação',
    data_criacao TIMESTAMP DEFAULT now() COMMENT 'Registro de criação',
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Última atualização',
    
    FOREIGN KEY (usuario_id) 
    REFERENCES usuario(usuario_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
        
    INDEX idx_usuario_data (usuario_id, data_hora),
    INDEX idx_tipo (tipo)
) COMMENT='Registro de operações financeiras';

CREATE TABLE jogo (
    jogo_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único do jogo',
    nome VARCHAR(50) NOT NULL UNIQUE COMMENT 'Nome oficial do jogo (ex: FlaFlu-2024)',
    odds JSON NOT NULL COMMENT 'Probabilidades em formato JSON (ex: {"vitoria_timeA": 1.85, "empate": 3.40, "vitoria_timeB": 4.20})',
    INDEX idx_nome (nome)  
) COMMENT='Cadastro de jogos disponíveis para apostas';

CREATE TABLE aposta (
    aposta_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único da aposta',
    usuario_id INT NOT NULL COMMENT 'Referência ao usuário apostador',
    jogo_id INT NOT NULL COMMENT 'Referência ao jogo relacionado',
    valor DECIMAL(10,2) NOT NULL COMMENT 'Valor apostado (deve ser positivo)',
    data_aposta TIMESTAMP DEFAULT now() COMMENT 'Data/hora da realização da aposta',
    resultado VARCHAR(250) NOT NULL COMMENT 'Resultado obtido (ex: VITÓRIA, EMPATE, DERROTA)',
    tipo_aposta VARCHAR(50) NOT NULL COMMENT 'Categoria da aposta (ex: VITÓRIA_TIME_A, PLACAR_EXATO)',
    
    -- Constraints de integridade
    CONSTRAINT fk_aposta_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(usuario_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    
    CONSTRAINT fk_aposta_jogo
        FOREIGN KEY (jogo_id)
        REFERENCES jogo(jogo_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    -- Validação de negócio
    CONSTRAINT chk_valor_positivo CHECK (valor > 0),
    
    -- Índices para otimização
    INDEX idx_usuario_jogo (usuario_id, jogo_id),
    INDEX idx_data_aposta (data_aposta)
) COMMENT='Registro de apostas realizadas pelos usuários';

CREATE TABLE estatiscas_jogo (
    estatiscas_jogo_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID único das estatísticas do jogo',
    total_apostas INT DEFAULT 0 NOT NULL COMMENT 'Quantidade total de apostas realizadas',
    lucro_casa DECIMAL(15, 2) DEFAULT 0.00 NOT NULL COMMENT 'Lucro da casa acumulado',
    jogadores_ativos INT DEFAULT 0 NOT NULL COMMENT 'Número de jogadores ativos',
    jogo_id INT NOT NULL COMMENT 'Referência ao jogo relacionado',
    
    CONSTRAINT fk_estatisticas_jogo 
        FOREIGN KEY (jogo_id) 
        REFERENCES jogo(jogo_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    
    -- Constraints de validação
    CONSTRAINT chk_total_apostas CHECK (total_apostas >= 0),
    CONSTRAINT chk_lucro_casa CHECK (lucro_casa >= 0),
    CONSTRAINT chk_jogadores_ativos CHECK (jogadores_ativos >= 0)
) COMMENT='Estatísticas agregadas por jogo';

INSERT INTO jogo (nome, odds) VALUES (
     'Roleta Europeia',
     '{
       "aposta_direta": 35,
       "split": 17,
       "street": 11,
       "corner": 8,
       "six_line": 5,
       "duzia": 2,
       "coluna": 2,
       "vermelho_preto": 1,
       "par_impar": 1,
       "alto_baixo": 1
     }'
 );