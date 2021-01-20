# XY INC - POI

## Notas gerais

Valeu pela oportunidade, Zup! Este projeto foi desenvolvido com muita paixão e com base no framework Spring Data REST e o princípio de HATEOAS (com o formato de hipermídia HAL + JSON), fornecendo assim uma API auto-documentada e orientada a documentos.

## Setup de ambiente

### Sistema Operacional

Testado em Windows 10 20H2 e Ubuntu 20.04.1.

### JDK

Tenha a JDK11 instalada na sua máquina, com `JAVA_HOME` e `PATH` devidamente configurados.

### Docker

Tenha o Docker (testado com 20.10.2) e docker-compose (testado com 1.27.4) instalados na sua máquina e com o `PATH` devidamente configurado.

### Postman

Instale o Postman na sua máquina e importe os arquivos contidos em `{dirRaiz}/postman`

- `XY_POI-LOCAL.postman_environment.json` (variáveis de ambiente) 

- `XY - POI.postman_collection.json` (collection)

A partir de então, a URL de bookmark (conceito REST/HATEOAS) e os 3 serviços descritos na especificação estarão disponíveis para teste a partir da collection `XY - POI`.

### IDE

Testado em IntelliJ 2020.3. Para executar a aplicação de dentro da IDE, certifique-se primeiro de abrir um terminal no diretório raiz, rodar o comando

`docker-compose up xy_poi_postgres`

e setar o campo "Active profiles" da configuração IntelliJ para o valor `development` (do contrário, não será inicializado o banco).

### Execução de testes

A partir de um terminal aberto no diretório raiz, execute

`./gradlew clean test` ou equivalente do seu SO.

Por padrão, todos os testes (unitários, integração e end-to-end) serão executados. Para executar somente os testes unitários, adicionar o parâmetro `-PtestType=unit` ao comando (ex.: `./gradlew clean test -PtestType=unit`).

### Execução via docker-compose

A partir de um terminal aberto no diretório raiz, execute

`docker-compose up`

e a aplicação estará disponível em http://localhost:8080.

## API

A partir da URL de bookmark (raiz `/`), pode-se descobrir as funcionalidades do sistema graças à construção com base em HATEOAS.

### `/profile`

Essa URL (descoberta a partir da URL de bookmark) revela as coleções e funcionalidades disponíveis.

### `/pointsOfInterest`

Aceita os verbos HTTP `GET` e `POST`, dentre outros. Um exemplo do formato do recurso `PointOfInterest` (conforme collection Postman e especificado via HATEOAS/HAL) é: 

```
{
    "name":  "Lanchonete",
    "location": {
        "x": 34,
        "y": 123
    }
}
```

#### Regras:

- name: não pode ser nulo/vazio; string
- location: não pode ser nulo; objeto
    - x: não pode ser nulo; deve ser inteiro >= 0
    - y: não pode ser nulo; deve ser inteiro >= 0

### `/pointsOfInterest/search/findByProximity?referenceX={inteiro}&referenceY={inteiro}&distanceInMeters={inteiro}`

Além dos serviços `GET` e `POST` com semântica REST padrão, a aplicação fornece o serviço de busca por proximidade conforme a especificação dada. Os seguintes query parameter são obrigatórios:

- `referenceX`: ponto X de referência; inteiro >= 0
- `referenceY`: ponto Y de referência; inteiro >= 0
- `distanceInMeters`: distância em metros aceita para a busca a partir das coordenadas informadas; inteiro >= 0