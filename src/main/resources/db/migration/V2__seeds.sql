-- ============================================================================
-- Global Solution 2025 - O Futuro do Trabalho
-- Plataforma de Upskilling/Reskilling
-- ============================================================================
-- Migration V2: Dados iniciais (Seeds)
-- ============================================================================

-- ============================================================================
-- COMPETÊNCIAS DO FUTURO
-- ============================================================================

-- Competências Tecnológicas
INSERT INTO competencias (nome, categoria, descricao) VALUES
('Inteligência Artificial', 'Tecnológica', 'Desenvolvimento e aplicação de sistemas de IA, machine learning e deep learning'),
('Ciência de Dados', 'Tecnológica', 'Análise, visualização e interpretação de grandes volumes de dados'),
('Cloud Computing', 'Tecnológica', 'Gestão e desenvolvimento de aplicações em nuvem (AWS, Azure, GCP)'),
('Cibersegurança', 'Tecnológica', 'Proteção de sistemas, redes e dados contra ataques digitais'),
('Desenvolvimento Full Stack', 'Tecnológica', 'Desenvolvimento completo de aplicações web (frontend e backend)'),
('Internet das Coisas (IoT)', 'Tecnológica', 'Desenvolvimento de soluções conectadas e dispositivos inteligentes'),
('Blockchain', 'Tecnológica', 'Tecnologias de registros distribuídos e contratos inteligentes'),
('DevOps', 'Tecnológica', 'Integração entre desenvolvimento e operações de TI'),
('Automação e RPA', 'Tecnológica', 'Automação de processos robóticos e workflows'),
('Big Data', 'Tecnológica', 'Processamento e análise de grandes volumes de dados estruturados e não estruturados');

-- Competências Humanas (Soft Skills)
INSERT INTO competencias (nome, categoria, descricao) VALUES
('Pensamento Crítico', 'Humana', 'Capacidade de análise e avaliação objetiva de informações'),
('Criatividade', 'Humana', 'Capacidade de gerar ideias inovadoras e soluções originais'),
('Inteligência Emocional', 'Humana', 'Capacidade de reconhecer e gerenciar emoções próprias e alheias'),
('Colaboração', 'Humana', 'Trabalho efetivo em equipe e networking'),
('Adaptabilidade', 'Humana', 'Flexibilidade para se ajustar a mudanças e novos contextos'),
('Comunicação', 'Humana', 'Capacidade de expressar ideias de forma clara e eficaz'),
('Liderança', 'Humana', 'Capacidade de inspirar, motivar e guiar equipes'),
('Resolução de Problemas Complexos', 'Humana', 'Identificação e solução de desafios multifacetados'),
('Aprendizagem Contínua', 'Humana', 'Disposição e capacidade para aprender constantemente'),
('Ética Digital', 'Humana', 'Compreensão de questões éticas relacionadas à tecnologia');

-- Competências de Gestão
INSERT INTO competencias (nome, categoria, descricao) VALUES
('Gestão de Projetos Ágeis', 'Gestão', 'Metodologias ágeis (Scrum, Kanban) para gestão de projetos'),
('Gestão de Mudanças', 'Gestão', 'Liderança de processos de transformação organizacional'),
('Análise de Negócios', 'Gestão', 'Identificação de oportunidades e requisitos de negócio'),
('Gestão de Produtos Digitais', 'Gestão', 'Product Management e estratégia de produtos tech'),
('Design Thinking', 'Gestão', 'Metodologia centrada no usuário para inovação');

-- ============================================================================
-- TRILHAS DE APRENDIZAGEM
-- ============================================================================

-- Trilhas de Nível INICIANTE
INSERT INTO trilhas (nome, descricao, nivel, carga_horaria, foco_principal) VALUES
('Introdução à Inteligência Artificial',
 'Fundamentos de IA, Machine Learning e suas aplicações no mercado de trabalho. Ideal para quem está começando.',
 'INICIANTE', 40, 'Inteligência Artificial'),

('Fundamentos de Programação',
 'Aprenda lógica de programação e desenvolvimento de software do zero. Base essencial para carreira tech.',
 'INICIANTE', 60, 'Desenvolvimento'),

('Alfabetização Digital',
 'Competências digitais básicas para o mundo do trabalho moderno. Excel, ferramentas colaborativas e mais.',
 'INICIANTE', 30, 'Habilidades Digitais'),

('Soft Skills para o Futuro',
 'Desenvolva competências humanas essenciais: comunicação, colaboração, pensamento crítico e criatividade.',
 'INICIANTE', 35, 'Soft Skills');

-- Trilhas de Nível INTERMEDIARIO
INSERT INTO trilhas (nome, descricao, nivel, carga_horaria, foco_principal) VALUES
('Ciência de Dados na Prática',
 'Análise de dados com Python, visualização e machine learning aplicado. Projetos hands-on reais.',
 'INTERMEDIARIO', 80, 'Ciência de Dados'),

('Desenvolvimento Full Stack Moderno',
 'Domine React, Node.js e bancos de dados. Construa aplicações web completas do zero ao deploy.',
 'INTERMEDIARIO', 100, 'Desenvolvimento'),

('Cloud Computing e DevOps',
 'AWS, Azure, containers, CI/CD e arquitetura em nuvem. Prepare-se para o mercado cloud.',
 'INTERMEDIARIO', 70, 'Cloud'),

('Gestão Ágil de Projetos',
 'Scrum, Kanban e metodologias ágeis aplicadas. Torne-se um Agile Practitioner certificado.',
 'INTERMEDIARIO', 50, 'Gestão'),

('UX/UI Design',
 'Design centrado no usuário, prototipação e ferramentas modernas (Figma, Adobe XD).',
 'INTERMEDIARIO', 60, 'Design');

-- Trilhas de Nível AVANCADO
INSERT INTO trilhas (nome, descricao, nivel, carga_horaria, foco_principal) VALUES
('Arquitetura de Sistemas IA',
 'Deep Learning, NLP, Computer Vision e deployment de modelos em produção. Para especialistas.',
 'AVANCADO', 120, 'Inteligência Artificial'),

('Cibersegurança Avançada',
 'Ethical hacking, pentest, segurança em cloud e resposta a incidentes. Certificação inclusa.',
 'AVANCADO', 100, 'Segurança'),

('Blockchain e Web3',
 'Smart contracts, DApps, Ethereum, Solidity e economia descentralizada.',
 'AVANCADO', 90, 'Blockchain'),

('Liderança em Transformação Digital',
 'Estratégia digital, gestão de mudanças e liderança de times tech. Para C-level e gestores.',
 'AVANCADO', 60, 'Liderança');

-- ============================================================================
-- RELACIONAMENTO: TRILHAS ↔ COMPETÊNCIAS
-- ============================================================================

-- Trilha: Introdução à IA (ID 1)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(1, 1),  -- Inteligência Artificial
(1, 2),  -- Ciência de Dados
(1, 11), -- Pensamento Crítico
(1, 19); -- Aprendizagem Contínua

-- Trilha: Fundamentos de Programação (ID 2)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(2, 5),  -- Desenvolvimento Full Stack
(2, 18), -- Resolução de Problemas
(2, 11), -- Pensamento Crítico
(2, 19); -- Aprendizagem Contínua

-- Trilha: Alfabetização Digital (ID 3)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(3, 16), -- Comunicação
(3, 14), -- Colaboração
(3, 15); -- Adaptabilidade

-- Trilha: Soft Skills para o Futuro (ID 4)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(4, 11), -- Pensamento Crítico
(4, 12), -- Criatividade
(4, 13), -- Inteligência Emocional
(4, 14), -- Colaboração
(4, 16), -- Comunicação
(4, 17); -- Liderança

-- Trilha: Ciência de Dados na Prática (ID 5)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(5, 2),  -- Ciência de Dados
(5, 10), -- Big Data
(5, 1),  -- IA
(5, 11), -- Pensamento Crítico
(5, 23); -- Análise de Negócios

-- Trilha: Desenvolvimento Full Stack Moderno (ID 6)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(6, 5),  -- Desenvolvimento Full Stack
(6, 3),  -- Cloud Computing
(6, 8),  -- DevOps
(6, 18); -- Resolução de Problemas

-- Trilha: Cloud Computing e DevOps (ID 7)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(7, 3),  -- Cloud Computing
(7, 8),  -- DevOps
(7, 9),  -- Automação
(7, 21); -- Gestão de Projetos Ágeis

-- Trilha: Gestão Ágil de Projetos (ID 8)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(8, 21), -- Gestão de Projetos Ágeis
(8, 17), -- Liderança
(8, 14), -- Colaboração
(8, 16); -- Comunicação

-- Trilha: UX/UI Design (ID 9)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(9, 12), -- Criatividade
(9, 25), -- Design Thinking
(9, 13), -- Inteligência Emocional
(9, 11); -- Pensamento Crítico

-- Trilha: Arquitetura de Sistemas IA (ID 10)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(10, 1),  -- IA
(10, 2),  -- Ciência de Dados
(10, 3),  -- Cloud
(10, 10); -- Big Data

-- Trilha: Cibersegurança Avançada (ID 11)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(11, 4),  -- Cibersegurança
(11, 20), -- Ética Digital
(11, 11), -- Pensamento Crítico
(11, 18); -- Resolução de Problemas

-- Trilha: Blockchain e Web3 (ID 12)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(12, 7),  -- Blockchain
(12, 5),  -- Desenvolvimento
(12, 20), -- Ética Digital
(12, 12); -- Criatividade

-- Trilha: Liderança em Transformação Digital (ID 13)
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(13, 17), -- Liderança
(13, 22), -- Gestão de Mudanças
(13, 13), -- Inteligência Emocional
(13, 16), -- Comunicação
(13, 15); -- Adaptabilidade

-- ============================================================================
-- USUÁRIOS DE EXEMPLO
-- ============================================================================

INSERT INTO usuarios (nome, email, area_atuacao, nivel_carreira, data_cadastro) VALUES
-- Profissionais em Transição de Carreira
('Ana Silva', 'ana.silva@email.com', 'Marketing', 'PLENO', '2025-01-15'),
('Carlos Mendes', 'carlos.mendes@email.com', 'Recursos Humanos', 'SENIOR', '2025-02-10'),
('Beatriz Costa', 'beatriz.costa@email.com', 'Administração', 'JUNIOR', '2025-03-05'),

-- Profissionais de TI buscando atualização
('Diego Santos', 'diego.santos@email.com', 'Tecnologia', 'PLENO', '2025-01-20'),
('Elena Rodrigues', 'elena.rodrigues@email.com', 'Tecnologia', 'SENIOR', '2025-02-15'),
('Fernando Lima', 'fernando.lima@email.com', 'Tecnologia', 'ESPECIALISTA', '2025-03-01'),

-- Jovens entrando no mercado
('Gabriela Alves', 'gabriela.alves@email.com', 'Estudante', 'JUNIOR', '2025-01-25'),
('Hugo Ferreira', 'hugo.ferreira@email.com', 'Estudante', 'JUNIOR', '2025-02-20'),

-- Gestores e Líderes
('Isabela Martins', 'isabela.martins@email.com', 'Gestão', 'GERENTE', '2025-02-05'),
('João Pedro', 'joao.pedro@email.com', 'Gestão', 'DIRETOR', '2025-01-10');

-- ============================================================================
-- MATRÍCULAS DE EXEMPLO
-- ============================================================================

-- Ana Silva (ID 1) - Marketing para Tech
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(1, 3, '2025-01-16', 'CONCLUIDA'),     -- Alfabetização Digital
(1, 5, '2025-02-01', 'EM_ANDAMENTO');  -- Ciência de Dados

-- Carlos Mendes (ID 2) - RH para Digital
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(2, 4, '2025-02-11', 'CONCLUIDA'),     -- Soft Skills
(2, 8, '2025-03-01', 'EM_ANDAMENTO');  -- Gestão Ágil

-- Beatriz Costa (ID 3) - Iniciando em Tech
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(3, 3, '2025-03-06', 'EM_ANDAMENTO'),  -- Alfabetização Digital
(3, 2, '2025-03-15', 'EM_ANDAMENTO');  -- Fundamentos de Programação

-- Diego Santos (ID 4) - Desenvolvedor se especializando
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(4, 6, '2025-01-21', 'EM_ANDAMENTO'),  -- Full Stack Moderno
(4, 7, '2025-02-10', 'EM_ANDAMENTO');  -- Cloud e DevOps

-- Elena Rodrigues (ID 5) - Evolução para IA
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(5, 1, '2025-02-16', 'CONCLUIDA'),     -- Introdução à IA
(5, 10, '2025-03-10', 'EM_ANDAMENTO'); -- Arquitetura de Sistemas IA

-- Fernando Lima (ID 6) - Especialista em Segurança
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(6, 11, '2025-03-02', 'EM_ANDAMENTO'); -- Cibersegurança Avançada

-- Gabriela Alves (ID 7) - Estudante começando
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(7, 2, '2025-01-26', 'EM_ANDAMENTO'),  -- Fundamentos de Programação
(7, 4, '2025-02-15', 'EM_ANDAMENTO');  -- Soft Skills

-- Hugo Ferreira (ID 8) - Estudante em Design
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(8, 9, '2025-02-21', 'EM_ANDAMENTO');  -- UX/UI Design

-- Isabela Martins (ID 9) - Gestora se atualizando
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(9, 8, '2025-02-06', 'CONCLUIDA'),     -- Gestão Ágil
(9, 13, '2025-03-05', 'EM_ANDAMENTO'); -- Liderança Digital

-- João Pedro (ID 10) - Diretor em transformação
INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(10, 13, '2025-01-11', 'EM_ANDAMENTO'); -- Liderança Digital

-- ============================================================================
-- FIM DOS SEEDS
-- ============================================================================

-- Verificação dos dados inseridos
SELECT 'Competências cadastradas: ' || COUNT(*) FROM competencias;
SELECT 'Trilhas cadastradas: ' || COUNT(*) FROM trilhas;
SELECT 'Usuários cadastrados: ' || COUNT(*) FROM usuarios;
SELECT 'Matrículas criadas: ' || COUNT(*) FROM matriculas;