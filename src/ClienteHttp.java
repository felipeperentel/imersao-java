import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ClienteHttp {
    
    public String buscaDados (String url) {

        try {
            URI address = URI.create(url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(address).GET().build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
