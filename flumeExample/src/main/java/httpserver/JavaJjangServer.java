package httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class JavaJjangServer {
    private Integer port = 8000;
    private HttpServer server = null;
    public JavaJjangServer() throws IOException {
        System.out.println("Java JJang Server On! port : 8000");
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new JavaJjangPostHandler());
        server.setExecutor(null);
        server.start();
        }
    }
