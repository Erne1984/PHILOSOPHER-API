## 🧑‍🎓 Philosopher

### Regras de negócio
- Deve possuir `name`, `bio`, `birthYear`, `deathYear` e `country`.
- `deathYear` pode ser nulo.
- Pode ser criado sem `works`, `quotes`, `themes`, `schoolOfThought`, `influenced`, `influencedBy`.
- Apenas países já existentes devem ser referenciados.

### JSON de exemplo 1 – criação básica
```json
{
  "name": "Baruch Spinoza",
  "birthYear": 1632,
  "deathYear": 1677,
  "bio": "Dutch rationalist philosopher of Portuguese Jewish origin.",
  "country": {
    "id": 2
  }
}
```

——————————————————

## 🌎 Country

### Regras de negócio
- Deve possuir `name` e `startYear`.
- `isoCode`, `endYear` e `region` pode ser nulo.
- Pode ser criado sem `philosophers` e `works`.

### JSON de exemplo 1 – criação básica
```json
{
  "name": "Ancient Greece",
  "startYear": -800,
  "endYear": 330,
  "isoCode": "300",
  "region": "Europe"
}
```

——————————————————

## 🏫 SchoolOfThought

### Regras de negócio
- Deve possuir `name`, `description ` e `originCentury`.
- Pode ser criado sem `philosophers` e `works`.

### JSON de exemplo 1 – criação básica
```json
{
  "name": "Existentialism",
  "description": "A philosophical movement emphasizing individual freedom, choice, and responsibility.",
  "originCentury": 19
}
```

——————————————————

## 💭 Theme

### Regras de negócio
- Deve possuir `name` e `desc `.
- Pode ser criado sem `philosophers`, `works` e `quotes`.

### JSON de exemplo 1 – criação básica
```json
{
  "name": "Freedom",
  "desc": "Theme exploring the conditions and implications of individual liberty."
}
```

——————————————————

## 📘 Work

### Regras de negócio
- Deve possuir `title`, `year`, `summary` e `philosopher`.
- Pode ser criado sem `schoolsOfThought`, e `themes`.
- `philosopher` deve ser um filósofo já existente.

### JSON de exemplo 1 – criação básica
```json
{
  "title": "Ethics",
  "year": 1677,
  "summary": "A philosophical treatise by Baruch Spinoza, laying out his ethical vision grounded in a geometric method.",
  "philosopher": {
    "id": 1
  }
}
```

——————————————————

## 💬 Quote

### Regras de negócio
- Deve possuir `content` e `philosopher`.
- Pode ser criado sem `themes` e `work`.
- Se `work` for fornecido, ele deve ser de autoria do `philosopher`.

### JSON de exemplo 1 – criação básica
```json
{
  "content": "The more you struggle to live, the less you live. Give up the notion that you must be sure of what you are doing. Just take action.",
  "philosopher": {
    "id": 1
  }
}
```

——————————————————

## 💬 Influence

### Regras de negócio
- Deve possuir `Influence`, `influenced` e `strength`.
- `influencer` e `influenced` não podem ser o mesmo filósofo.
- `strength` deve ser um valor entre 1 (baixa) e 3 (forte).

### JSON de exemplo 1 – criação básica
```json
{
  "influencer": {
    "id": 1
  },
  "influenced": {
    "id": 2
  },
  "strength": 3
}
```