import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
 
public class App {
    public static void main(String[] args) throws Exception {
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        // var extrator = new ExtratorDeConteudoDoIMDB();

        String url = "https://api.nasa.gov/planetary/apod?start_date=2022-03-25&end_date=2022-03-25&api_key=DEMO_KEY";
        var extrator = new ExtradorDeConteudoDaNasa();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);
        String pathName = "saida/";
        String texto = "Show";

        if (!new File(pathName).exists()) {
            new File(pathName).mkdir();
        }

        var geradora = new GeradoradeFigurinhas();
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        for (int i = 0; i < conteudos.size(); i++) {
            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = conteudo.getTitulo()+".jpg";

            geradora.cria(inputStream, nomeArquivo, pathName, texto);

            System.out.println("\u001b[1mTitulo: \u001b[1m"+"\u001b[36m"+conteudo.getTitulo()+"\u001b[m");
            System.out.println();
        }
    }
}
