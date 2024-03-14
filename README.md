# Projeto teste vaga Texo

## Reference Documentation
API RESTful para possibilitar a leitura da lista de indicados e vencedores
da categoria Pior Filme do Golden Raspberry Awards.

## Detalhes da aplicação
- Para execução do projeto é necessário instalação do JDK 17
- Foi utilizado Spring Boot na versão <b>3.2.3</b>
- O servidor esta rodando na porta <b>9002</b>
- Foi utilizado o Banco de dados H2 conforme solicitado, sendo possivel acessar utilizando as configurações
    - http://localhost:9002/h2
    - User Name: sa
    - Password: admin
- Para execução da aplicação seguir os seguintes passos:
    - mvn clean install
    - mvn spring-boot:run
- Para execução dos testes seguir os seguintes passos:
    - mvn clean install
    - mvn test

## Rotas disponivies
### Movies
- GET    - "/api/movies"
- GET    - "/api/movies/title/{title}"
- GET    - "/api/movies/year/{year}"
- GET    - "/api/movies/winner"
- POST   - "/api/movies"
- PUT    - "/api/movies/{id}"
- DELETE - "/api/movies/{id}"

### Awards
- GET    - "/api/awards"