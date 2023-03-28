import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
 
public class App {
    public static void main(String[] args) throws Exception {
        // Conexão HTTP
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        URI address = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(address).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        // System.out.println(body);

        // Dados importantes (titulo, poster, cassificação)
        // JsonParser parser = new JsonParser();
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        // Exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            float value = Float.parseFloat(filme.get("imDbRating"));

            System.out.println("\u001b[1mTitulo: \u001b[1m"+"\u001b[36m"+filme.get("title")+"\u001b[m");
            System.out.println("\u001b[1mPoster: \u001b[m"+"\u001b[34m"+filme.get("image")+"\u001b[m");
            System.out.print("\u001b[1mNota: \u001b[m"+"\u001b[33m"+filme.get("imDbRating"));
            for (int i = 1; i <= value; i++) {
                System.out.print(" \u2605");
            }
            System.out.print(" \u001b[m");
            System.out.println("\n");
        }
    }
}
