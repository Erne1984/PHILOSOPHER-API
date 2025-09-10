# API for Philosophers & Schools
Uma API RESTful para consulta e gerenciamento de filósofos, obras, escolas filosóficas, temas, países e citações.
Inclui autenticação JWT, paginação, busca avançada (Specification) e documentação OpenAPI/Swagger.

![Escola de Atenas](./docs/imgs/readme_img.jpg)

<p align="center">
     <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v21-blue.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v3.5.0-brightgreen.svg" />
    </a>
    <a alt="Maven">
        <img src="https://img.shields.io/badge/Maven-v3.10.1-pink.svg" />
    </a>
    <a alt="PostgreSQL">
        <img src="https://img.shields.io/badge/PostgreSQL-v15-blue.svg" />
    </a>
    <a alt="Flyway">
        <img src="https://img.shields.io/badge/Flyway-v10.14.0-green.svg" />
    </a>
    <a alt="Docker">
        <img src="https://img.shields.io/badge/Docker-latest-darkblue.svg" />
    </a>
</p>


## Principais Recursos

-   **CRUD Completo:** Para filósofos, escolas, obras, temas, países e citações.

-   **Busca e Filtros Avançados:** Utilizando Specification do Spring Data JPA (filtro por nome, período, corrente filosófica, país, etc.).

-   **Paginação e Ordenação:** Implementadas em todos os endpoints de listagem.

-   **Relacionamentos Complexos:** Modelagem de influências entre filósofos, temas, escolas, obras e citações.

-   **Autenticação Segura:** Sistema de autenticação JWT com diferentes níveis de acesso (usuário e administrador).

-   **Documentação Interativa:** Integração com OpenAPI (Swagger) para explorar e testar os endpoints facilmente.

-   **Deploy Containerizado:** Configuração pronta com Docker Compose e banco PostgreSQL.

-   **Dados Iniciais:** O banco é populado automaticamente via Flyway migrations com mais de 20 filósofos, escolas, obras e citações, permitindo testar a API imediatamente após a inicialização.

## Como Rodar Localmente

Pré-requisitos

- JDK 17+
- Docker & Docker Compose (ou PostgreSQL local)
- Maven 3.9+ 
1


1. **Clone o repositório**:
   ```bash
   git clone https://github.com/Erne1984/PHILOSOPHER-API.git
   cd philosophy-api

2. **Rodar com Docker Compose**:
   ```bash
   docker-compose up --build

### 3. **Rodar sem Docker**:
- Configurar application.properties com suas credenciais do PostgreSQL
- Executar com Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run

## Exemplo de Requisição

Abaixo um exemplo de como consultar filósofos com paginação e ordenação:

**Endpoint:** GET http://localhost:8080/api/v1/philosophers


**Resposta de exemplo:**
```json
{
  "success": true,
  "message": "Philosophers list retrieved successfully",
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Socrates",
        "iepLink": "https://iep.utm.edu/socrates/",
        "speLink": "https://plato.stanford.edu/entries/socrates/",
        "img": "https://iep.utm.edu/wp-content/media/Socrates2.png",
        "birthYear": -470,
        "deathYear": -399,
        "bio": "Classical Greek philosopher credited as one of the founders of Western philosophy.",
        "country": "Ancient Greece",
        "schoolOfThoughtName": ["Socratic Philosophy"]
      },
      ... // Outros filósofos
    ],
    "page": 0,
    "size": 10,
    "totalElements": 22,
    "totalPages": 3
  }
}

````


## Estrutura do Projeto

O projeto está organizado em diversos pacotes, cada um com uma responsabilidade específica:

- **config**: contém as classes de configuração, como `OpenApiConfig` para documentação.
- **controller**: onde ficam os *REST Controllers*. Aqui são expostos os endpoints seguindo o estilo REST. Nesta camada utilizamos DTOs para definir as interfaces de consumo, evitando expor atributos desnecessários do modelo.
- **dto**: objetos de transferência de dados usados para entrada e saída de informações da API.
- **exception**: define nossas exceções customizadas e o *global exception handler* para tratar exceções do Spring.
- **infra.security**: configuração e implementação da autenticação/autorização via JWT.
- **mapper**: classes responsáveis por conversão entre entidades e DTOs.
- **model**: onde estão definidas as entidades de domínio, com anotações JPA (Jakarta) para o mapeamento objeto-relacional.
- **payload**: contém classes auxiliares como `ApiResponse` e `ResponseFactory`, utilizadas para padronizar respostas da API.
- **repository**: interfaces do Spring Data JPA para acesso ao banco de dados.
- **service**: camada de regras de negócio. Aqui validamos dados, tratamos exceções de negócio e acessamos os repositórios.
- **specification**: implementações de *Specifications* para filtros dinâmicos nas consultas.
- **PhilosophyApi.java**: classe principal que inicializa a aplicação Spring Boot e conecta todos os componentes.

> Essa estrutura é pensada para manter clareza e separação de responsabilidades, facilitando manutenção e evolução do projeto.

## 📚 Documentação

A API gera documentação interativa via Swagger UI:

- Local: http://localhost:8080/api/swagger-ui.html
- Produção: https://philosopher-api.onrender.com/api/swagger-ui/index.html#/

## 🤝 Contribuição 
Estou aberto a contribuições. Se você encontrar algum erro ou tiver sugestões de melhorias, não hesite em fazer um pull request.


## 📄 Licença

Este projeto está licenciado sob a [Licença MIT](./LICENSE.md).  
Sinta-se livre para usar, modificar e distribuir, conforme os termos descritos na licença.




