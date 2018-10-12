package com.example;

import io.prometheus.client.Counter;

public class Handler {
  private static Counter allRequestsCounter = Counter.build().name("all_requests").help("Counts all requests").register();
  private static Counter allGoodCounter = Counter.build().name("all_good").help("Counts all requests that didn't have parameters missing").register();
  private static Counter missingParametersCounter = Counter.build().name("missing_parameters").help("Counts all requests that had parameters missing").register();

  public static boolean handle(String foo, String bar) {
    allRequestsCounter.inc();
    if (foo != null && bar != null) {
      allGoodCounter.inc();
      return true;
    } else {
      missingParametersCounter.inc();
      return false;
    }
  }
}
