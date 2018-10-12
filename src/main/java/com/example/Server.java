package com.example;

import fi.iki.elonen.NanoHTTPD;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import java.io.IOException;
import java.util.Map;

public class Server extends NanoHTTPD {
  public Server() throws IOException {
    super(8080);
    start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
  }

  public static void main(String[] args) {
    try {
      new Server();
    } catch (IOException e) {
      System.out.println("Server is not running!");
      System.exit(1);
    }

    try {
      DefaultExports.initialize();
      new HTTPServer(1234);
    } catch (IOException e) {
      System.out.println("Metrics server not running!");
    }
  }

  @Override
  public Response serve(IHTTPSession session) {
    Map<String, String> params = session.getParms();
    boolean wasHandledCorrectly = Handler.handle(params.get("foo"), params.get("bar"));

    if (wasHandledCorrectly) {
      return newFixedLengthResponse("All good");
    } else {
      return newFixedLengthResponse("Missing parameters");
    }
  }
}
