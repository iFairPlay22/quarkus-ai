package fr.ewen.ocr;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
public interface OcrService {

    @SystemMessage("""
        You are a kind OCR assistant.
    """)
    @UserMessage("""
        You take an image in and output the text extracted from the image.
    """)
    String process(Image image);

}
