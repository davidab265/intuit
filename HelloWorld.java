import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import java.sql.*;
import java.io.*;
import java.net.*;

public class HelloWorld {
  public static void main(String[] args) throws Exception {
    
    // Connect to the database
    String url = "jdbc:mysql://database-3.cluster-ces6wvluhglc.eu-central-1.rds.amazonaws.com:3306/intuit3?useSSL=false";
    String username = "admin";
    String password = "david9292intuit";
    Connection conn = DriverManager.getConnection(url, username, password);


    // Connect to the "intuit3" database
    url = "jdbc:mysql://database-3.cluster-ces6wvluhglc.eu-central-1.rds.amazonaws.com:3306/intuit3?useSSL=false";
    conn = DriverManager.getConnection(url, username, password);
    
    // Create the "messages" table if it doesn't exist
    String createTableSql = "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, message VARCHAR(255))";
    Statement stmt = conn.createStatement();
    stmt.executeUpdate(createTableSql);
    stmt.close();

    // Insert a row with an "id" of 1 and a "message" of "Hello World" if it doesn't already exist
    String insertRowSql = "INSERT INTO messages (id, message) SELECT 1, 'Hello World' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM messages WHERE id = 1)";
    stmt = conn.createStatement();
    stmt.executeUpdate(insertRowSql);
    stmt.close();

    // Retrieve the "Hello World" message from the database
    String query = "SELECT message FROM messages WHERE id = 1";
    stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    String message = "";
    if (rs.next()) {
      message = rs.getString("message");
    }
    stmt.close();
    conn.close();

    // Start the HTTP server
    HttpServer server = HttpServer.create(new InetSocketAddress(443), 0);
    server.createContext("/", new HelloWorldHandler(message));
    server.start();
  }
}

class HelloWorldHandler implements HttpHandler {
  private String message;

  public HelloWorldHandler(String message) {
    this.message = message;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String response = message;
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
