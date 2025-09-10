# 🎰 Luckiest Gamble

## 🎲 Sobre o Projeto
Luckiest Gamble é uma aplicação web de cassino, desenvolvida como um projeto de estudo para aprofundar os conhecimentos na arquitetura **MVC (Model-View-Controller)** utilizando **Java, JSP e Servlets**.  

O objetivo principal foi construir uma aplicação completa e funcional, aplicando boas práticas de desenvolvimento, como separação de responsabilidades, componentização e abstração de código.

A aplicação simula um ambiente de cassino básico, com funcionalidades de **cadastro, login, perfil de usuário** e um **jogo de roleta interativo**.

---

## 🛠️ Tecnologias Utilizadas
- **Backend:** Java, Servlets  
- **Frontend:** JSP, JSTL, jQuery, HTML5, CSS3, Bootstrap  
- **Servidor de Aplicação:** Apache Tomcat  
- **Banco de Dados:** MySQL  
- **Build Tool:** Maven  

---

## ✨ Funcionalidades Principais
- **Autenticação de Usuário:** Página única com login e cadastro alternando dinamicamente via JavaScript.  
- **Página Inicial (Portal):** Hub central que direciona para jogos e perfil.  
- **Perfil do Usuário:** Exibe informações, saldo e histórico de transações.  
- **Jogo de Roleta:** Roleta clássica com animações e lógica de apostas sofisticada em JavaScript.  

---

## ⭐ Destaques da Arquitetura e Conceitos Aplicados

### 1. Arquitetura MVC Robusta
- **Model:** POJOs e DAOs para lógica de negócios e persistência.  
- **View:** JSPs desacopladas da lógica, usando JSTL.  
- **Controller:** Servlets centralizando rotas e validação de sessão.  

### 2. Frontend Componentizado e Dinâmico
- **JSP Tag Files (.tag):** Componentes reutilizáveis como `dynamicSelect.tag` e `head.tag`.  
- **Fragmentos de UI (.jsf):** Incluídos dinamicamente, mantendo páginas limpas.  

### 3. Integração Inteligente entre Backend e Frontend
- **TLDs (.tld):** Exposição de métodos Java como funções customizadas nas JSPs, mantendo a lógica no backend.  

### 4. Backend Abstrato e Escalável
- **Generic Repository:** `BaseRepositoryImpl` abstrai operações CRUD.  
- **Interfaces:** Garantem baixo acoplamento e facilitam testes.  

### 5. Testes Unitários
Inclui testes básicos para aprendizado e validação de partes críticas.  

---

## 🚀 Como Rodar o Projeto

### 1. Pré-requisitos
- Java JDK 21+  
- Apache Maven 3.8+  
- Apache Tomcat 10+  
- MySQL Server  

### 2. Configuração do Banco de Dados
1. Crie o schema (exemplo: `luckiest_gamble_db`).  
2. Execute o script SQL fornecido.  
3. Configure a conexão em:  
   `src/main/java/org/cefet/config/DataBaseConfig.java`  

### 3. Build do Projeto com Maven
```bash
git clone https://github.com/mateuszebendo/luckiest-gamble.git
cd LuckiestGamble
mvn clean package
```

### 4. Deploy no Apache Tomcat
- Copie `target/LuckiestGamble.war` para `tomcat/webapps/`  
- Inicie o servidor:  
  - Windows: `bin/startup.bat`  
  - Linux/macOS: `bin/startup.sh`  

### 5. Acesso à Aplicação
Abra no navegador:  
[http://localhost:8080/LuckiestGamble/](http://localhost:8080/LuckiestGamble/)  

---
