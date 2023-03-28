import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
 
public class App {
    public static void main(String[] args) throws Exception {
        // Conexão HTTP
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        URI address = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(address).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Dados importantes (titulo, poster, cassificação)
        // JsonParser parser = new JsonParser();
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        // Exibir e manipular os dados
        var geradora = new GeradoradeFigurinhas();
        String pathName = "saida/";
        String texto = "Show";

        if (!new File(pathName).exists()) {
            new File(pathName).mkdir();
        }

        for (Map<String,String> filme : listaDeFilmes) {
            float value = Float.parseFloat(filme.get("imDbRating"));

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            double classificação = Double.parseDouble(filme.get("imDbRating"));
            String nomeArquivo = titulo+".jpg";
            InputStream inputStream = new URL(urlImagem).openStream();

            geradora.cria(inputStream, nomeArquivo, pathName, texto);

            System.out.println("\u001b[1mTitulo: \u001b[1m"+"\u001b[36m"+titulo+"\u001b[m");
            System.out.println("\u001b[1mPoster: \u001b[m"+"\u001b[34m"+urlImagem+"\u001b[m");
            System.out.print("\u001b[1mNota: \u001b[m"+"\u001b[33m"+classificação);
            for (int i = 1; i <= value; i++) {
                System.out.print(" \u2605");
            }
            System.out.print(" \u001b[m");
            System.out.println("\n");
        }
    }
}
