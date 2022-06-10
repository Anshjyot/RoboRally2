package dk.dtu.compute.se.pisd.roborally.RESTfulAPI;

import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Board;

import java.awt.font.ShapeGraphicAttribute;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Web {
    String string;
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void transferBoard(Board board, String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(LoadBoard.saveBoardToJson(board)))
                .uri(URI.create("http://localhost:8081/transferBoard"))
                .setHeader("User-Agent", "Roborally Client")
                .header("Content-Type", "application/json")
                .build();

        CompletableFuture<HttpResponse<String>> response =
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    }
    public void saveBoard(Board board) {
        System.out.println(board.getPhase());
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(LoadBoard.saveBoardToJson(board)))
                .uri(URI.create("http://localhost:8081/savegame"))
                .setHeader("User-Agent", "Roborally Client")
                .header("Content-Type", "application/json")
                .build();

        CompletableFuture<HttpResponse<String>> response =
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    }
    public String loadBoard() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8081/loadgame"))
                .setHeader("User-Agent", "Roborally Client")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response =  httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response.body();
    }
}
