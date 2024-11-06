# Microservices Example

Este projeto demonstra uma arquitetura de microserviços com dois serviços: Gerenciamento de Produtos e Gerenciamento de Pedidos.

## Pré-requisitos

<ul>
<li>Docker </li>
<li>Docker Compose </li>
<li>Postman</li>
</ul>

## Clone o repositório:
git clone

cd microservices-example

## Inicie os serviços usando Docker Compose:
docker-compose up --build

Este comando irá construir as imagens Docker (se necessário) e iniciar todos os serviços definidos no docker-compose.yml.

## Os serviços estarão disponíveis nos seguintes endereços:
Serviço de Produtos: http://localhost:8080 

Serviço de Pedidos: http://localhost:3000 

MySQL (para Produtos): localhost:3320 

PostgreSQL (para Pedidos): localhost:5490

## Testando os Serviços com Postman
Para testar os serviços, você pode importar as coleções do Postman fornecidas:

### Abra o Postman 
Clique em "Import" no canto superior esquerdo 

Escolha "File" e selecione os arquivos de coleção: 

Product-Management-Service.postman_collection.json 

Order-Management-Service.postman_collection.json 

## Após a importação, você verá duas novas coleções no Postman: 

Product Management Service 
Order Management Service 

Cada coleção contém vários requests pré-configurados para testar diferentes endpoints dos serviços. 

Você pode executar os requests individualmente ou usar a funcionalidade "Run Collection" do Postman para executar todos os testes de uma vez. 

## Estrutura do Projeto

docker-compose.yml: Define todos os serviços e suas configurações 

product-management-service/: Código fonte do serviço de produtos 

order-management-service/: Código fonte do serviço de pedidos 

databases/: Diretório para armazenamento de dados persistentes dos bancos de dados 

scripts/: Scripts SQL para inicialização dos bancos de dados 

### Notas
Certifique-se de que as portas 8080, 3000, 3320 e 5490 estão disponíveis em sua máquina antes de iniciar os serviços.
Para parar os serviços, use docker-compose down no diretório raiz do projeto.