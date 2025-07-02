# 📘 Resumo das Anotações

#### Curso: Java: consumindo API, gravando arquivos e lidando com erros

---

## 🌐 HTTP vs HTTPS

### 📌 O que é HTTP?

- HTTP (HyperText Transfer Protocol) é o protocolo de comunicação utilizado entre clientes (como navegadores ou apps Java) e servidores web.
- Permite enviar e receber dados, como páginas HTML, JSON de APIs, imagens etc.
- Funciona na porta 80 por padrão.

### 📌 O que é HTTPS?

- HTTPS (HTTP Secure) é a versão segura do HTTP.
- Utiliza criptografia via TLS/SSL para proteger os dados transmitidos.
- Funciona na porta 443 por padrão.
- Garante:
  - 🔒 Confidencialidade dos dados
  - ✅ Autenticidade do servidor
  - 🛡️ Integridade das informações trocadas

| Protocolo | Segurança        | Porta padrão | Uso comum                    |
|-----------|------------------|--------------|------------------------------|
| HTTP      | Sem criptografia | 80           | Sites simples, testes locais |
| HTTPS     | Com criptografia | 443          | APIs, sites com login/senha  |

---

## 📡 HTTP com Java 11+ (HttpClient)

### 📦 Principais classes:

| Classe        | Função                                            |
|---------------|--------------------------------------------------|
| `HttpClient`  | Responsável por enviar requisições e receber respostas HTTP |
| `HttpRequest` | Representa uma requisição HTTP (GET, POST, etc.) |
| `HttpResponse`| Representa a resposta HTTP recebida do servidor  |

### 🧱 Exemplo de uso: GET simples

```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.exemplo.com/dados"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}
```

### 🔧 Detalhes importantes:

- O método send() é bloqueante e pode lançar exceções (IOException, InterruptedException).
- Você pode usar BodyHandlers.ofString() para retornar o corpo como String, mas há também opções para InputStream, arquivos, etc.
- O HttpClient pode ser configurado com headers, tempo limite, autenticação e mais.

--- 

## 📦 Como adicionar uma biblioteca .jar (ex: Gson) no IntelliJ

### 1️⃣ Baixar o .jar no mvnrepository.com

- Acesse https://mvnrepository.com
- Pesquise por Gson
- Escolha a versão desejada (ex: com.google.code.gson:gson:2.10.1)
- Clique em "jar" para baixar o arquivo .jar diretamente

### 2️⃣ Adicionar o .jar como dependência no IntelliJ (projeto sem Maven)

- Clique com o botão direito na pasta do projeto → Open Module Settings (ou F4)
- Vá na aba "Libraries"
- Clique no botão + → Java
- Selecione o arquivo .jar baixado
- Clique em "OK" e depois em "Apply"
- Pronto! Agora você pode importar e usar o Gson normalmente no seu projeto.

### ✅ Exemplo de uso do Gson após adicionar o .jar

```java
import com.google.gson.Gson;

public class Main {
  public static void main(String[] args) {
    Gson gson = new Gson();
    String json = gson.toJson(new Pessoa("João", 30));
    System.out.println(json);
  }
}
```

---

## 🏷️ @SerializedName – Mapeando nomes de campos JSON

### 📌 O que é?

- A anotação @SerializedName (da biblioteca Gson) permite associar um nome de campo JSON a um atributo da sua classe, mesmo que os nomes sejam diferentes.

## 🧱 Exemplo prático:

### 📥 JSON recebido:

```json
{
  "title": "Matrix",
  "release_year": 1999
}
```

### 📦 Classe Java com @SerializedName:

```java
import com.google.gson.annotations.SerializedName;

public class Filme {
    @SerializedName("title")
    private String titulo;

    @SerializedName("release_year")
    private int anoDeLancamento;
    // getters, setters, construtor etc.
}
```

### ✅ Vantagens:

- Permite seguir convenções de código em Java (ex: camelCase) mesmo quando o JSON tem nomes com snake_case, espaços ou outros formatos.
- Evita ter que renomear os campos JSON ou mudar a API.
- Também funciona ao serializar objetos Java para JSON.

---

## 🧾 record – Representando dados de forma simples

### 📌 O que é?

- Um record é uma classe imutável e concisa, ideal para transportar dados.
- Automaticamente fornece:
  - Construtor
  - getters (sem o prefixo get)
  - toString(), equals() e hashCode()

## 🧱 Exemplo com JSON (Gson + record):

### 📥 JSON:

```json
{
  "title": "Matrix",
  "release_year": 1999
}
```

### 📦 Record Java:

```java
//Exemplo com json da API Omdb
public record TituloOmdb(String title, String year, String runtime) 
{
}
```
- Agora você pode usar o Gson para fazer o mapeamento normalmente:

```java
//Alterado Builder do GSON para retornar os campos com UPPER_CAMEL_CASE
Gson gson = new GsonBuilder()
              .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
              .create();
TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
Titulo meuTitulo = new Titulo(meuTituloOmdb);
```
> Nesse exemplo, será necessário um construtor com record

```java
public Titulo(TituloOmdb meuTituloOmdb) {
    this.nome = meuTituloOmdb.title();
    this.anoDeLancamento = Integer.valueOf(meuTituloOmdb.year());
    this.duracaoEmMinutos = Integer.valueOf(meuTituloOmdb.runtime().substring(0,2));
}
```

### ✅ Vantagens do record:

| Benefício              | Descrição                                                             |
|------------------------|------------------------------------------------------------------------|
| ✂️ Código conciso      | Reduz muito o código "boilerplate" (getters, construtores etc.)       |
| 🔒 Imutabilidade       | Os atributos são automaticamente `final`, favorecendo segurança       |
| 🔗 Ideal para JSON     | Ótima escolha para representar dados recebidos de APIs                |
| 💡 Integração com Gson | Funciona com `@SerializedName` e outras anotações                     |

---

## 🐛 Stack Trace – Rastreando Exceções em Java

### 📌 O que é?

- Um stack trace (rastreamento de pilha) é a mensagem de erro exibida pelo Java quando ocorre uma exceção (Exception ou Error).
- Mostra a cadeia de chamadas de métodos até o ponto onde o erro aconteceu.

### 🧱 Exemplo de stack trace:

```java
Exception in thread "main" java.lang.NullPointerException
  at com.exemplo.Main.main(Main.java:5)
```

### Interpretação:
- java.lang.NullPointerException: tipo da exceção
- com.exemplo.Main.main(Main.java:5): o erro aconteceu no método main da classe Main, linha 5

### 🔍 Como ler um stack trace:

| Parte do Stack Trace                     | O que indica                                  |
|------------------------------------------|-----------------------------------------------|
| Tipo da exceção (`NullPointerException`) | O tipo de erro ocorrido                       |
| Caminho da classe (`com.exemplo.Main`)   | Onde o erro aconteceu                         |
| Método (`main`)                          | Qual método estava sendo executado            |
| Linha (`Main.java:5`)                    | Linha exata do código onde a exceção surgiu   |


### ✅ Dicas:

- O primeiro item do stack trace normalmente é o ponto exato do erro.
- Os itens abaixo mostram a cadeia de chamadas anteriores, úteis para entender o caminho até o erro.
- Leia de cima para baixo!

---

## 🛡️ try-catch – Tratamento de Exceções em Java

### 📌 O que é?

- O bloco try-catch permite capturar exceções que podem ocorrer em tempo de execução, evitando que a aplicação quebre de forma inesperada.
- Útil para lidar com situações como: leitura de arquivos, chamadas HTTP, parsing, etc.

### 🧱 Sintaxe básica:

```java
try {
    // Código que pode gerar exceção
    int resultado = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Erro: divisão por zero.");
}
```
> O Java entra no bloco catch se uma exceção do tipo ArithmeticException for lançada.

### 🧠 Dicas práticas:

| Bloco     | Função                                                                 |
|-----------|------------------------------------------------------------------------|
| `try`     | Onde você coloca o código que pode lançar uma exceção                  |
| `catch`   | Captura e trata a exceção (pode haver múltiplos blocos `catch`)        |
| `finally` | (Opcional) Executado **sempre**, mesmo que haja erro ou não            |


### 🔁 Exemplo com finally:

```java
try {
    lerArquivo();
} catch (IOException e) {
    System.out.println("Erro ao ler o arquivo: " + e.getMessage());
} finally {
    System.out.println("Finalizando operação.");
}
```

---

## ❗ Exceções vs Erros em Java

### 📌 O que é uma Exceção (Exception)?

- São eventos anormais esperados que podem acontecer durante a execução do programa.
- Exemplo: tentar acessar um índice inexistente de uma lista, abrir um arquivo que não existe, divisão por zero, etc.
- Podem (e devem) ser tratadas com try-catch.

### 📌 O que é um Erro (Error)?

- São problemas graves que ocorrem fora do controle da aplicação e geralmente não devem ser tratados diretamente.
- Exemplo: OutOfMemoryError, StackOverflowError, falha na JVM.
- Normalmente indicam falhas em nível de sistema.

### 🧬 Hierarquia simplificada:

```php
Throwable
├── Error            ← Problemas graves (JVM, memória, etc.)
└── Exception
    ├── CheckedException ← Deve ser tratada (ex: IOException)
    └── RuntimeException ← Pode ser tratada (ex: NullPointerException)

```

### ✅ Resumo:

| Tipo        | Pode ser tratado? | Quando ocorre                      | Exemplo                      |
|-------------|-------------------|------------------------------------|------------------------------|
| `Exception` | Sim               | Durante execução normal do app     | `IOException`, `SQLException`|
| `Error`     | Não (em geral)    | Problemas graves na JVM ou sistema | `OutOfMemoryError`           |
