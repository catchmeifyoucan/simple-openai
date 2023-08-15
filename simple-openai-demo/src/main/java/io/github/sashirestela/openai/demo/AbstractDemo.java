package io.github.sashirestela.openai.demo;

import java.util.ArrayList;
import java.util.List;

import io.github.sashirestela.openai.SimpleOpenAIApi;

public abstract class AbstractDemo {

  private String apiKey;
  protected SimpleOpenAIApi openAIApi;

  private static List<TitleAction> titleActions = new ArrayList<>();
  private int times = 80;

  public AbstractDemo() {
    apiKey = System.getenv("OPENAI_API_KEY");
    openAIApi = new SimpleOpenAIApi(apiKey);
  }

  public void addTitleAction(String title, Action action) {
    titleActions.add(new TitleAction(title, action));
  }

  public void run() {
    titleActions.forEach(ta -> {
      long startTime = System.currentTimeMillis();
      System.out.println("=".repeat(times));
      System.out.println(ta.title);
      System.out.println("-".repeat(times));
      ta.action.execute();
      System.out.println("~".repeat(times/2));
      long endTime = System.currentTimeMillis();
      long duration = endTime - startTime;
      System.out.println("Duration in milliseconds: " + duration);
      System.out.println();
    });
  }

  @FunctionalInterface
  static interface Action {
    void execute();
  }

  static class TitleAction {
    public String title;
    public Action action;

    public TitleAction(String title, Action action) {
      this.title = title;
      this.action = action;
    }
  }
}