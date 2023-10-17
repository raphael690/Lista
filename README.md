# Projeto TODO LIST em Java
Neste projeto refere-se como funciona a base de uma API REST em Java e em como trabalhar os métodos das requisições e rotas de maneira fácil e intuitiva.

## Primeiro Ponto
Trabalhar com os principais conceitos com métodos e encaminhar rotas da aplicação com seus devidos atributos para recolhimento de dados de um usuário, criando modelo e requisição com api.http.

## Segundo Ponto
Trabalhando em como buscar as informações de uma classe com os métodos getters e setters.

### Getters e Setters
- Getters: Tras as informações de uma classe
- getName
  ```Java
  public String getName(){
    return name;
  }
  ```
-Setters: Insere o valor privado de uma classe
-seName
```Java
public void setName(String name){
  this.name = name;
}
```
Podemos otimizar a criação do getters e setters da aplicação por meio de uma lib chamada [lombok] faço o download da dependencia(https://projectlombok.org/)
por meio de um decorator @Data em cima do modelo, já estará tudo configurado automaticamente o getters e setters.

### Banco de Dados
Foi mostrado um ORM que é um projeto do próprio Spring Boot chamado [Spring Data JPA](https://spring.io/project/spring-data-jpa) onde será usada como camada de comunicação na aplicação para o banco de dados. E o banco de dados que será utilizado nessa aplicação é o [H2 Database Engine](https://www.h2.com/html/main.html) que é um banco de dados em memória, que não precisa de instalação alguma na aplicação, ele é apenas recomendado para a parte de development de um projeto, para produção é preciso um banco de dados em nuvem ou algo do tipo.

Para isso, é necessario ir em resources/aplication.properties e inserir as variaveis de ambientes e suas configurações e conseguir logar no console de [http://localhost:8080/h2-console] onde teremos acesso visualmente ao banco de dados e suas tabelas.

A conversão do objeto para dentro do banco de dados, é usado o Repository como interface extendendo do JpaRepository que é uma classe vinda do Spring Boot.
Em seguinda teremos que configurar no UserController a interface IUserRepository para ser userRepository e armazena o resultado do usuário salvo numa variável para retorna-lá quando o usuário for criado.

### Validando Dados
- Username
 - Usando o decorator @Column com atributo unique true toda vez que for criado um usuário com os mesmos dados ele retornará o erro 500.
  Com o erro 500 não é uma boa conduta para informar algum erro, foi feito a verificação dentro do IUserRepository criando o metodo para buscar se há o mesmo username no banco de dados(findByUsername), depois disto foi usado o ResponseEntity.

  ## Ponto Três
  Vamos aprender a criptografar senhas dentro do banco de dados com a lib [bcript](https://github.com/patrickfav/bcrypt).
  Então iniciamos as tarefas criando uma pasta separada em todolist e um modelo e suas regras de negócio que serão:


- ID
- Usuário (ID_USUARIO)
- Descrição
- Título (limite de 50 caracteres)
- Status ( teste)
- Data de Início
- Data de término
- Data de última atualização
- Prioridade

### Filtro
Filtro um conceito para delimitarmos acessos e restrições para criação de uma task, esse modelo existe dentro do Spring Boot chamado OncePerRequestFilter e como FilterTaskAuth segue uma interface que quando importado aponta para criar dando CTRL + .

### Validação dentro do Filtro
O processo de validação começou desde a utilização do servletPatch onde captamos o caminho utilizados e se caso for coincidente com o que queremos damos continuidade as verificações, isto é importante pois se não ele irá buscar em todas rotas. Após isso tivemos que adaptar o que é recebido pelo auth, vindo de uma string com o nome basic e uma série de caracteres as variantes como username index [0] e password [1] assim como separamos. Com isso feito, partimos com o auxilio do Bcripty para decodificar o password e conseguir a senha para finalmente permitir ou não o usuário de cadastrar sua task.

## Quarto Ponto
#### Depreciando o Iduser e injetando para mostrar dentro do auth
- Nesta fase dentro do TaskController e Filter configuramos um atributo ao auth com o nome de Iduser e recuperamos ele dentro do controller atribuindo-o com o get.Iduser e assim podendo o Iduser ser passado dentro do auth e não no body da requisição.

### Validação das horas

- StartAt
  - Com a validação de horas usamos a data atual dentro de uma variavel para checar se o startAt é depois desta
- EndAt
  - Fazemos o uso dp pipe e utilizamos a mesma lógica
- Sentido entre as datas
  - Também validamos se a data de inicio é menor que a de término para ter sentido a criação da task.

### Listando todas as tarefas
- Para listar todas as tarefas apenas captamos o id do usuário dentro do auth e retornamos as tasks linkadas ao mesmo utilizando do método findByIdUser.

### Fazendo o update das tarefas
- Com o put foi ussado o parâmetro do @PathVariable que pega o id da task a rota apresentada. Além disso tivemos que fazer algumas validações se a tarefa existe, depois captamos o id do usuário ou não que ele edite aquela tarefa, baseado que só o usuário dono da tarefa pode altera-lá.
- Com isso apresentou uma forma de maximizar nosso trabalho evitando o uso interno de ifs a todo canto, criando o utils podemos usar o Beans e o Util do própio java e spring boot para checar os campos nulos da requisição e copiar os que foram alterados, tornando facíl a edição pelo lado do usuário.

## Cinco Ponto

### Tratando erros
- Uma forma mais indicada que é configurando um handler que irá pegar o throw exception error e irá para o body do usuário de forma mais amigável com um texto explicando o motivo do erro.

## Dependência Devtools
- O [devtools](https://www.baeldung.com/spring-boot-devtools) é um conjunto de ferramentas para facilitar o desenvolvimento dentro do spring boot/java.
- Recomendo a tirar a versão e deixar a que foi colocada no início do projeto também e colocar o optional como true.

✨ mvn spring-boot: run para dar start mais fácil na aplicação

## Regras de negócio
- O Gerenciador de tarefas deve conter um método para cadastrar onde nele:
   - Não se pode ter dois usuários como o mesmo nick
   - A senha deve ser criptografada

- Também deve ter tarefas onde:
   - O título não pode conter mais de 50 caracters
   - A tarefa deve ser atrelada e somente editada pelo usuário que o criou
   - A tarefa deve conter os seguintes campos:
- - Id
  - Título
  - Descrição
  - Status de conclusão
    - A Fazer
    - EM ANDAMENTO
    - FEITO
  - Data de Início
  - Data de Término
  - Data da última atualização
  - Prioridade
- A tarefa deve ser:
  - Criada
  - Editada
  - Deletada - Feito fora do curso
  - Encontrada (todas)
  - Encontrada individualmente - Feito fora do curso
  - Encontrada por status
  - Encontrada por prioridade

## Considerações finais.
Aprender java é uma oportunidade que requer empenho e dedicação. #FogueteNãoTemRé 🚀

   





  
