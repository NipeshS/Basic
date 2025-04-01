import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
//HttpServer: This is the main class for creating an HTTP server. It allows you to bind the server to a specific port, define request handlers, and start the server.
// HttpHandler: This is an interface that you implement to define how the server should process incoming HTTP requests. Each handler is associated with a specific URI path.
// HttpExchange: This class provides methods to access details of the HTTP request (like headers, method, and body) and to send a response back to the client.



import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(8080), 0);
        server.createContext("/", new StaticFileHandler("src/web/index.html"));
        server.createContext("/about", new StaticFileHandler("src/web/about.html"));
        server.createContext("/contact", new StaticFileHandler("src/web/contact.html"));
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on http://localhost:8080");
    }

    static class StaticFileHandler implements HttpHandler {
        private final String filePath;

        public StaticFileHandler(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = Files.readAllBytes(Paths.get(filePath));
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}