# Sistema de Estacionamento – Faseh

Projeto desenvolvido a partir do template de Spring Boot fornecido pelo professor, para controle de estacionamento da faculdade.

O sistema permite:

- Cadastrar veículos (placa, modelo, marca, cor, tipo).
- Registrar a entrada de veículos em vagas específicas.
- Registrar o usuário responsável pela entrada.
- Consultar vagas livres, vagas ocupadas e total de carros no estacionamento.
- Registrar a saída de um veículo pela placa.
- Calcular automaticamente o valor a ser pago com base no tipo do veículo e no tempo estacionado.
- Retornar erros em formato JSON (vaga ocupada, placa não encontrada, etc).

---

## Tecnologias utilizadas

- **Java 17** 
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
- **MySQL** 
- **Hibernate** (ORM)
- **Swagger / OpenAPI** (documentação e teste dos endpoints)

---

## Modelo de domínio

### Entidades principais

- `Veiculo`
    - `id`
    - `placa`
    - `modelo`
    - `marca`
    - `cor`
    - `tipo` (`CARRO_PEQUENO`, `CARRO_GRANDE`, `MOTO`)
    - `dataCriacao`
    - `dataUpdate`

- `Estacionar`
    - `id`
    - `veiculo` (associação com `Veiculo`)
    - `vaga`
    - `entrada` (data/hora de entrada)
    - `saida` (data/hora de saída, quando houver)
    - `usuarioResponsavel`
    - `dataCriacao`
    - `dataUpdate`

### Enum

- `TipoVeiculo`
    - `CARRO_PEQUENO` → R$ 16,00 / hora
    - `CARRO_GRANDE` → R$ 25,00 / hora
    - `MOTO` → R$ 8,00 / hora

---

## Regras de negócio principais

- Não é permitido estacionar:
    - Em **vaga já ocupada**.
    - Com **placa que já está estacionada** (sem saída registrada).
- Quando isso acontece, o sistema lança exceções de negócio e retorna **JSON com mensagem de erro** e **status HTTP apropriado** (409, 404, etc).
- A cobrança é feita **por hora**, arredondando para cima:
    - Tempo mínimo cobrado = **1 hora**.
    - Valor total = `horasCobradas * valorPorHora`, onde:
        - Carro pequeno → 16,00
        - Carro grande → 25,00
        - Moto → 8,00

---

## Configuração do banco de dados

Banco: **MySQL**

1. Criar o banco:

```sql
CREATE DATABASE estacionamento;
