# MedPro Mobile - Sistema de Gestão para Clínica Médica FRONTEND

Este projeto consiste em um aplicativo mobile desenvolvido para
administração completa de uma clínica médica. Ele permite o
gerenciamento de médicos, pacientes e consultas, integrando um
aplicativo desenvolvido em React Native com uma API REST robusta
construída em Spring Boot.

## Funcionalidades

-   **Gestão de Médicos:** Cadastro, listagem, edição, pesquisa e
    desativação (remoção lógica) de médicos.
-   **Gestão de Pacientes:** Registro detalhado de pacientes, incluindo
    endereço, edição de dados e histórico.
-   **Agendamento de Consultas:**
    -   Validação de regras de negócio (horários permitidos, conflito de
        agendas, antecedência mínima).
    -   Possibilidade de selecionar um médico específico ou optar por um
        profissional aleatório baseado na especialidade.
-   **Cancelamento:** Cancelamento de consultas com justificativa
    obrigatória.
-   **Busca:** Filtros avançados por nome, CRM ou CPF.

## Tecnologias Utilizadas

### Frontend (Mobile)

-   React Native com Expo
-   Axios (consumo da API)
-   React Navigation (navegação em pilha)
-   StyleSheet (estilização)


------------------------------------------------------------------------

## Como Executar o Projeto

Este repositório contém tanto o frontend quanto o backend. A seguir
estão as instruções para executar ambos.

### Pré-requisitos

-   Node.js e npm instalados\
-   Um dispositivo móvel com o aplicativo Expo Go instalado ou um
    emulador Android/iOS


### Configuração do Frontend (`App.clinica`)


### 1. Instalação das Dependências

``` bash
cd app_clinica
npm install
```

### 2. Inicializar o Projeto com Expo

``` bash
npx expo start
```

### 3. Execução

Escaneie o QR Code exibido no terminal utilizando o aplicativo Expo Go
ou utilize o emulador Android.

------------------------------------------------------------------------

## Autores

Desenvolvido como parte da disciplina de Aplicações Mobile.

-   Ana Rubia de Almeida
-   Guilherme Calandrim Favero
-   Eli Hofni Mariano
