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