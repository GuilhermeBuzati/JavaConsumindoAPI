package br.com.alura.orientacaoObjetoJava.principal;

import br.com.alura.orientacaoObjetoJava.modelos.Titulo;
import br.com.alura.orientacaoObjetoJava.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite o filme");
        var busca = leitura.nextLine();

        try{
            String endereco = "http://www.omdbapi.com/?t=" + busca + "&apikey=3f05bd88";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            System.out.println(json);

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();
            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(meuTituloOmdb);
            System.out.println(meuTituloOmdb);

            System.out.println("Titulo ja convertido");
            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println(meuTitulo);
        } catch (IllegalArgumentException e){
            System.out.println("Alguem erro de argumento na buscarm verifique o endereço");
        } catch (Exception e){
            System.out.println("Aconteceu um erro: ");
            System.out.println(e.getMessage());
        }

        System.out.println("Sistema finalizou corretamente");

    }
}
