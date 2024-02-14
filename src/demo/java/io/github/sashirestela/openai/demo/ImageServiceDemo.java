package io.github.sashirestela.openai.demo;

import java.nio.file.Paths;

import io.github.sashirestela.openai.domain.image.ImageEditsRequest;
import io.github.sashirestela.openai.domain.image.ImageRequest;
import io.github.sashirestela.openai.domain.image.ImageRespFmt;
import io.github.sashirestela.openai.domain.image.ImageVariationsRequest;
import io.github.sashirestela.openai.domain.image.Size;

public class ImageServiceDemo extends AbstractDemo {

    public void demoCallImageGeneration() {
        var imageRequest = ImageRequest.builder()
                .prompt("A cartoon of a hummingbird that is flying around a flower.")
                .n(2)
                .size(Size.X256)
                .responseFormat(ImageRespFmt.URL)
                .model("dall-e-2")
                .build();
        var futureImage = openAI.images().create(imageRequest);
        var imageResponse = futureImage.join();
        imageResponse.stream().forEach(img -> System.out.println("\n" + img.getUrl()));
    }

    public void demoCallImageEdits() {
        var imageEditsRequest = ImageEditsRequest.builder()
                .image(Paths.get("src/demo/resources/little_cat_rgba.png"))
                .prompt("A cartoon of a little cute cat playing with a ball in the grass.")
                .n(1)
                .size(Size.X256)
                .responseFormat(ImageRespFmt.URL)
                .model("dall-e-2")
                .build();
        var futureImage = openAI.images().createEdits(imageEditsRequest);
        var imageResponse = futureImage.join();
        System.out.println(imageResponse.get(0).getUrl());
    }

    public void demoCallImageVariations() {
        var imageVariationsRequest = ImageVariationsRequest.builder()
                .image(Paths.get("src/demo/resources/little_cat.png"))
                .n(1)
                .size(Size.X256)
                .responseFormat(ImageRespFmt.URL)
                .model("dall-e-2")
                .build();
        var futureImage = openAI.images().createVariations(imageVariationsRequest);
        var imageResponse = futureImage.join();
        System.out.println(imageResponse.get(0).getUrl());
    }

    public static void main(String[] args) {
        var demo = new ImageServiceDemo();

        demo.addTitleAction("Call Image Generation", demo::demoCallImageGeneration);
        demo.addTitleAction("Call Image Edits", demo::demoCallImageEdits);
        demo.addTitleAction("Call Image Variations", demo::demoCallImageVariations);

        demo.run();
    }
}