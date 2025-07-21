# 🧠 Philosophers API – Documentação

> Projeto educacional/curioso voltado ao estudo de filosofia e história das ideias.

---

## 📌 Sumário

- [1. Objetivo Geral](#1-objetivo-geral)
- [2. Entidades do Domínio](#2-entidades-do-domínio)
- [3. Relacionamentos](#3-relacionamentos)
- [4. Endpoints Planejados](#4-endpoints-planejados)
- [5. Filtros e Parâmetros de Consulta](#5-filtros-e-parâmetros-de-consulta)

---

## 1. 🎯 Objetivo Geral

Desenvolver uma API REST para consulta, estudo e visualização de dados sobre filósofos, escolas de pensamento, obras e
citações, com foco em uso educacional e exploração livre por estudantes, entusiastas ou desenvolvedores.

---

## 2. 🧱 Entidades do Domínio

### 🧑‍🎓 Philosopher

| Campo             | Tipo                  | Descrição                      |
|-------------------|-----------------------|--------------------------------|
| `id`              | UUID                  | Identificador único            |
| `name`            | String                | Nome completo                  |
| `birthYear`       | Int                   | Ano de nascimento              |
| `deathYear`       | Int?                  | Ano de falecimento (nullable)  |
| `country`         | Country               | País de origem                 |
| `bio`             | Text                  | Biografia resumida             |
| `schoolOfThought` | List<SchoolOfThought> | tradição que filósofo pertence |
| `works`           | List<Work>            | Obras do filósofo              |
| `influenced`      | List<Infleunce>       | filósofos que ele influenciou  |
| `influencedBy`    | List<Infleunce>       | filósofos que o influenciaram  |
| `themes`          | List<Themes>          | Temas tratados em sua obra     |
| `quotes`          | List<Quote>           | Citações do filósofo           |

---

### 🏫 SchoolOfThought

| Campo           | Tipo              | Descrição                              |
|-----------------|-------------------|----------------------------------------|
| `id`            | UUID              | Identificador único                    |
| `name`          | String            | Nome da corrente (ex: Existencialismo) |
| `desc`          | Text              | Descrição geral da escola              |
| `originCentury` | int               | Século/origem histórica                |
| `philosophers`  | List<Philosopher> | Filósofos encaixados em tal tradição   |
| `works`         | List<Work>        | Trabalhos encaixados em tal tradição   |

---

### 🧑 Country

| Campo       | Tipo     | Descrição                      |
|-------------|----------|--------------------------------|
| `id`        | UUID     | Identificador único            |
| `name`      | String   | Nome oficial do país           |
| `isoCode`   | String?  | Código ISO                     |
| `startYear` | Int      | Ano de fundação                |
| `endYear`   | Int?     | Ano de extinção                |
| `notes`     | String?  | Observações                    |
| `region`    | String?  | Continente/região geopolítica  |

---

### 🧑🧑 Infleunce

| Campo        | Tipo    | Descrição                                 |
|--------------|---------|-------------------------------------------|
| `id`         | UUID    | Identificador único                       |
| `influencer` | String  | Filósofo que inflûenciou na relação       |
| `influenced` | String  | Filósofo que foi influenciado             |
| `strength`   | Int     | Grau da inflûencia( baixam média e forte) |

### 💭 Theme

| Campo          | Tipo              | Descrição                               |
|----------------|-------------------|-----------------------------------------|
| `id`           | UUID              | Identificador único                     |
| `name`         | String            | Nome da corrente (ex: Existencialismo)  |
| `desc`         | String            | Descrição geral do tema                 |
| `philosophers` | List<Philosopher> | Filósofos que tratam de tal tema        |
| `works`        | List<Work>        | Trabalhos que tratam de tal tema        |

---

### 📘 Work

| Campo         | Tipo         | Descrição           |
|---------------|--------------|---------------------|
| `id`          | UUID         | Identificador único |
| `title`       | String       | Título da obra      |
| `year`        | Int          | Ano de publicação   |
| `summary`     | Text         | Resumo ou sinopse   |
| `philosopher` | Philoshopher | Autor               |
| `themes`      | List<Theme>  | Temas tratados      |

---

### 💬 Quote

| Campo         | Tipo        | Descrição                  |
|---------------|-------------|----------------------------|
| `id`          | UUID        | Identificador único        |
| `text`        | Text        | Conteúdo da citação        |
| `themes`      | List<Theme> | Temas (liberdade, moral)   |
| `work`        | Work?       | Obra de origem (se houver) |
| `philosopher` | Philosopher | Autor da citação           |

---

## 3. 🔗 Relacionamentos

- Um **Philosopher** pode pertence a varias **SchoolOfThought**
- Um **Philosopher** pode ter várias **Influences** 
- Um **Philosopher** pode ter vários **Works** e **Quotes**
- Um **Philosopher** pertence apenas à um **country**
- Uma **SchoolOfThought** pode possuir vários **Philosopher**
- Uma **SchoolOfThought** pode possuir vários **Work**
- Um **Country** possui apenas um  **Philosopher**
- Uma **Influence** pode ter várias **Philosopher**
- Um **Work** pertence a um filósofo **Philosopher**
- Um **Work** pode ter vários **Theme**
- Uma **Quote** pode vir de uma **Work**, mas não obrigatoriamente
- Uma **Quote** pertence a um **Philosopher**
- Uma **Quote** pode ter vários **Theme**
- Um **Theme** pode pertence a vários **Philosopher**
- Um **Theme** pode pertence a vários **Work**

---

## 4. 🌐 Endpoints Planejados

### 📖 Philosophers

| Método | Rota                                        | Descrição                         |
|--------|---------------------------------------------|-----------------------------------|
| GET    | `/api/v1/philosophers`                      | Listar todos os filósofos         |
| GET    | `/api/v1/philosophers/:id`                  | Detalhes de um filósofo           |
| GET    | `/api/v1/philosophers/:id/works`            | Listar todas as obras um filósofo |
| GET    | `/api/v1/philosophers/:id/quotes`           | Listar citações de um filósofo    |
| GET    | `/api/v1/philosophers/search`               | Filtro avançado por parâmetros    |
| POST   | `/api/v1/philosophers`                      | Criar novo filósofo               |
| PUT    | `/api/v1/philosophers/:id`                  | Atualizar dados de um filósofo    |
| DELETE | `/api/v1/philosophers/:id`                  | Deletar filósofo                  |

#### 🔍 Parâmetros de busca para /philosophers/search

| Parâmetro  | Tipo    | Obrigatório | Exemplo        | Descrição                                        |
|------------|---------|-------------|----------------|--------------------------------------------------|
| nameSchool | string  | Não         | Nietzsche      | Nome do filósofo                                 |
| country    | string  | Não         | Alemanha       | País de origem                                   |
| school     | string  | Não         | Positivismo    | tradições associadas                             |
| themes     | string  | Não         | niilismo       | Temas tratados pelo filósofo                     |
| bornAfter  | int     | Não         | 1800           | Filtrar nascidos depois de um ano                |
| bornBefore | int     | Não         | 1900           | Filtrar nascidos antes de um ano               |
| sortBy     | string  | Não         | publishedAfter | Campo de ordenação                               |
| order      | string  | Não         | asc ou desc    | Ordem da ordenação                               |
| offset     | int     | Não         | 0              | Posição inicial dos dados recuperados            |
| limit      | int     | Não         | 10             | específica o número máximo de objetos retornados |

---

### 🏫 Schools of Thought

| Método | Rota                               | Descrição                                     |
|--------|------------------------------------|-----------------------------------------------|
| GET    | `/api/v1/schools`                  | Listar todas as escolas                       |
| GET    | `/api/v1/schools/:id`              | Detalhes de uma escola                        |
| GET    | `/api/v1/schools/:id/philosophers` | todos os filósofos associados a tal tradição  |
| GET    | `/api/v1/schools/:id/works`        | todos as obras relacionadas com tal tradição  |
| GET    | `/api/v1/schools/search`           | Filtro avançado por parâmetros                |
| POST   | `/api/v1/schools`                  | Criar nova escola filosófica                  |
| PUT    | `/api/v1/schools/:id`              | Atualizar escola filosófica                   |
| DELETE | `/api/v1/schools`                  | Deletar escola filosófica                     |

#### 🔍 Parâmetros de busca para /schools/search

| Parâmetro           | Tipo    | Obrigatório | Exemplo             | Descrição                                        |
|---------------------|---------|-------------|---------------------|--------------------------------------------------|
| name                | string  | Não         | Nietzsche           | Escola filosófica associada a obra               |
| country             | string  | Não         | Alemanha            | País de origem                                   |
| themes              | string  | Não         | niilismo            | Temas tratados na obra                           |
| origenCenturyAfter  | int     | Não         | 1800                | Filtrar publicados depois de um ano              |
| origenCenturyBefore | int     | Não         | 1900                | Filtrar publicados antes de um ano               |
| sortBy              | string  | Não         | publishedAfter      | Campo de ordenação                               |
| order               | string  | Não         | asc ou desc         | Ordem da ordenação                               |
| offset              | int     | Não         | 0                   | Posição inicial dos dados recuperados            |
| limit               | int     | Não         | 10                  | específica o número máximo de objetos retornados |

---

###  Theme

| Método | Rota                               | Descrição                                         |
|--------|------------------------------------|---------------------------------------------------|
| GET    | `/api/v1/themes`                   | Listar todos os temas                             |
| GET    | `/api/v1/themes/:id`               | Listar detalhes de um tema                        |
| GET    | `/api/v1/themes/:id/philoshophers` | Listar todos os filósofos que tratam de tal tema  |
| GET    | `/api/v1/themes/:id/quotes`        | Listar todas as citações relacionadas com um tema |
| GET    | `/api/v1/themes/search`            | Filtro avançado por parâmetros                    |
| POST   | `/api/v1/themes`                   | Criar um novo tema                                |
| PUT    | `/api/v1/themes/:id`               | Atualizar Dados de um tema                        |
| DELETE | `/api/v1/themes`                   | Deletar tema                                      |

#### 🔍 Parâmetros de busca para /themes/search

| Parâmetro           | Tipo    | Obrigatório | Exemplo         | Descrição                                        |
|---------------------|---------|-------------|-----------------|--------------------------------------------------|
| nameTheme           | string  | Não         | Existencialismo | Nome do tema                                     |
| originCenturyAfter  | int     | Não         | 1800            | Filtrar publicados depois de um ano              |
| originCenturyBefore | int     | Não         | 1900            | Filtrar publicados antes de um ano               |
| sortBy              | string  | Não         | publishedAfter  | Campo de ordenação                               |
| order               | string  | Não         | asc ou desc     | Ordem da ordenação                               |
| offset              | int     | Não         | 0               | Posição inicial dos dados recuperados            |
| limit               | int     | Não         | 10              | específica o número máximo de objetos retornados |

---

### 📘 Works

| Método | Rota                       | Descrição                         |
|--------|----------------------------|-----------------------------------|
| GET    | `/api/v1/works`            | Listar obras                      |
| GET    | `/api/v1/works/:id`        | Detalhes da obra                  |
| GET    | `/api/v1/works/:id/quotes` | Listar citações associadas a obra |
| GET    | `/api/v1/works/search`     | Detalhes da obra                  |
| POST   | `/api/v1/works`            | Criar nova obra                   |
| PUT    | `/api/v1/works/:id`        | Atualizar Dados de uma obra       |
| DELETE | `/api/v1/works`            | Deletar obra                      |

#### 🔍 Parâmetros de busca para /works/search

| Parâmetro       | Tipo    | Obrigatório | Exemplo             | Descrição                                        |
|-----------------|---------|-------------|---------------------|--------------------------------------------------|
| title           | string  | Não         | Além do bem e o mal | Nome da obra                                     |
| author          | string  | Não         | Nietzsche           | Escola filosófica associada a obra               |
| country         | string  | Não         | Alemanha            | País de origem                                   |
| themes          | string  | Não         | niilismo            | Temas tratados na obra                           |
| publishedAfter  | int     | Não         | 1800                | Filtrar publicações depois de um ano             |
| publishedBefore | int     | Não         | 1900                | Filtrar publicações antes de um ano              |
| sortBy          | string  | Não         | publishedAfter      | Campo de ordenação                               |
| order           | string  | Não         | asc ou desc         | Ordem da ordenação                               |
| offset          | int     | Não         | 0                   | Posição inicial dos dados recuperados            |
| limit           | int     | Não         | 10                  | específica o número máximo de objetos retornados |

---

### 💬 Quotes

| Método | Rota                                                   | Descrição                |
|--------|--------------------------------------------------------|--------------------------|
| GET    | `/api/v1/quotes`                                       | Listar todas as citações |
| GET    | `/api/v1/quotes/:id`                                   | Detalhe de uma citação   |
| GET    | `/api/v1/quotes/search`                                | Filtro por autor/tema    |
| POST   | `/api/v1/quotes`                                       | Adicionar nova citação   |
| PUT    | `/api/v1/quotes/:id`                                   | Atualizar citação        |
| DELETE | `/api/v1/quotes`                                       | Deletar citação          |

#### 🔍 Parâmetros de busca para /quotes/search

| Parâmetro | Tipo    | Obrigatório | Exemplo             | Descrição                                        |
|-----------|---------|-------------|---------------------|--------------------------------------------------|
| author    | string  | Não         | niilismo            | Filtrar citações de por filósofos                |
| work      | string  | Não         | niilismo            | filtrar citações por obras                       |
| theme     | string  | Não         | niilismo            | Temas associados a citação                       |
| sortBy    | string  | Não         | publishedAfter      | Campo de ordenação                               |
| order     | string  | Não         | asc ou desc         | Ordem da ordenação                               |
| offset    | int     | Não         | 0                   | Posição inicial dos dados recuperados            |
| limit     | int     | Não         | 10                  | específica o número máximo de objetos retornados |

---

### 🧩 Relacionamentos Úteis para Country

| Método  | Rota                                 | Descrição                                     |
|---------|--------------------------------------|-----------------------------------------------|
| GET     | `/api/v1/countries`                  | Listar todos os países (históricos e atuais)  |
| GET     | `/api/v1/countries/:id`              | Detalhes de um país                           |
| GET     | `/api/v1/countries/:id/philosophers` | Detalhes de um país                           |
| GET     | `/api/v1/countries/:id/works`        | Filósofos nascidos nesse país                 |
| GET     | `/api/v1/countries/:id/themes`       | Obras associadas a filósofos desse país       |
| GET     | `/api/v1/countries/:id/themes`       | Detalhes de um país                           |
| GET     | `/api/v1/countries/search`           | Filtro avançado por nome, região etc          |
| POST    | `/api/v1/countries`                  | Criar um novo país                            |
| PUT     | `/api/v1/countries/:id`              | Atualizar dados de um país                    |
| DELETE  | `/api/v1/countries/:id`              | Deletar país                                  |

#### 🔍 Parâmetros para /countries/search

| Parâmetro    | Tipo     | Exemplo          | Descrição                             |
|--------------|----------|------------------|---------------------------------------|
| name         | string   | "Persian Empire" | Nome total ou parcial do país         |
| region       | string   | "Europe"         | Filtrar por região                    |
| stillExists  | boolean  | true             | Se ainda existe (`endYear` é `null`)  |
| isoCode      | string   | "FR"             | Código ISO (quando aplicável)         |
| afterYear    | int      | 1800             | Fundados após esse ano                |
| beforeYear   | int      | 1900             | Fundados antes desse ano              |
| sortBy       | string   | "startYear"      | Campo de ordenação                    |
| order        | string   | "asc"            | Ordem: crescente ou decrescente       |
| offset       | int      | 0                | Posição inicial dos dados recuperados |
| limit        | int      | 20               | Máximo de países retornados           |

---

### 🧑🧑 endpoints  para Influence

| Método    | Rota                                 | Descrição                             |
|-----------|--------------------------------------|---------------------------------------|
| GET       | `/api/v1/influences`                 | Lista todas as relações de influência |
| GET       | `/api/v1/influences/:id`             | Detalhes de uma relação específica    |
| GET       | `/api/v1/countries/:id/philosophers` | Detalhes de um país                   |
| GET       | `/api/v1/influences/search`          | Filtro avançado por nome, região etc  |
| POST      | `/api/v1/influences`                 | Criar nova relação de influência      |
| PUT       | `/api/v1/influences/:id`             | Atualizar força ou IDs da relação     |
| DELETE    | `/api/v1/influences/:id`             | Remover relação de influência         |

#### 🔍 Parâmetros para /influences/search

| Parâmetro  | Tipo   | Exemplo   | Descrição                                 |
|------------|--------|-----------|-------------------------------------------|
| influencer | string | Nietzsche | Nome do influenciador                     |
| influenced | string | Sartre    | Nome do influenciado                      |
| strength   | int    | 2         | Grau da influência (1 = baixa, 3 = forte) |
| sortBy     | string | strength  | Campo para ordenação                      |
| order      | string | asc       | asc ou desc                               |
| offset     | int    | 0         | Posição inicial                           |
| limit      | int    | 10        | Máximo de registros                       |


---

## 5. 🧪 Filtros e Parâmetros de Consulta

Exemplos úteis de filtros: