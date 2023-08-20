package io.github.sashirestela.openai.demo;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.github.sashirestela.openai.domain.image.ImageEditsRequest;
import io.github.sashirestela.openai.domain.image.ImageRequest;
import io.github.sashirestela.openai.domain.image.ImageRespFmt;
import io.github.sashirestela.openai.domain.image.ImageResponse;
import io.github.sashirestela.openai.domain.image.ImageVariationsRequest;
import io.github.sashirestela.openai.domain.image.Size;

public class ImageServiceDemo extends AbstractDemo {

  public ImageServiceDemo() {
  }

  public void demoCallImageGeneration() {
    ImageRequest imageRequest = ImageRequest.builder()
        .prompt("A cartoon of a hummingbird that is flying around a flower.")
        .n(2)
        .size(Size.X256)
        .responseFormat(ImageRespFmt.URL)
        .build();
    CompletableFuture<List<ImageResponse>> futureImage = openAI.images().create(imageRequest);
    List<ImageResponse> imageResponse = futureImage.join();
    imageResponse.stream().forEach(img -> System.out.println("\n" + img.getUrl()));
  }

  public void demoCallImageEdits() {
    ImageEditsRequest imageEditsRequest = ImageEditsRequest.builder()
        .image(new File("src/demo/resources/little_cat_rgba.png"))
        .prompt("A cartoon of a little cute cat playing with a ball in the grass.")
        .n(1)
        .size(Size.X256)
        .responseFormat(ImageRespFmt.URL)
        .build();
    CompletableFuture<List<ImageResponse>> futureImage = openAI.images().createEdits(imageEditsRequest);
    List<ImageResponse> imageResponse = futureImage.join();
    System.out.println(imageResponse.get(0).getUrl());
  }

  public void demoCallImageVariations() {
    ImageVariationsRequest imageVariationsRequest = ImageVariationsRequest.builder()
        .image(new File("src/demo/resources/little_cat.png"))
        .n(1)
        .size(Size.X256)
        .responseFormat(ImageRespFmt.URL)
        .build();
    CompletableFuture<List<ImageResponse>> futureImage = openAI.images().createVariations(imageVariationsRequest);
    List<ImageResponse> imageResponse = futureImage.join();
    System.out.println(imageResponse.get(0).getUrl());
  }

  public static void main(String[] args) {
    ImageServiceDemo demo = new ImageServiceDemo();

    demo.addTitleAction("Call Image Generation", () -> demo.demoCallImageGeneration());
    demo.addTitleAction("Call Image Edits", () -> demo.demoCallImageEdits());
    demo.addTitleAction("Call Image Variations", () -> demo.demoCallImageVariations());

    demo.run();
  }
}