package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class JavaJjangPostHandler implements HttpHandler {
	
	@Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("JavaJJANG Post Handler-------");

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);

        String query = br.readLine();
        System.out.println("쿼리 : \"" + query + "\"");

        Integer seed = getSeedFromQuery(query);
        System.out.println(seed);

        RandomMaker randomMaker = new RandomMaker(seed);
        List<Integer> result = randomMaker.getRandomThree();

        System.out.println(result.toString());

        String response = "{\"result\":\"" + result.toString() + "\"}";
        System.out.println(response);
        exchange.getResponseHeaders().set("Content-Type", "appication/json");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private Integer getSeedFromQuery(String query) throws UnsupportedEncodingException {
        Map<String, Object> parameters = new HashMap<>();
        System.out.println("query : " + query);
        Integer result = 0;

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                System.out.println("0 : " + param[0]);
                System.out.println("1 : " + param[1]);
                result = Integer.parseInt(param[1]);
            }
            return result;
        }else return 0;
    }
}