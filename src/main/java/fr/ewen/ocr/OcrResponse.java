package fr.ewen.ocr;

import com.fasterxml.jackson.annotation.JsonCreator;

public record OcrResponse(
    String question,
    String answer
) {

    @JsonCreator
    public OcrResponse {}
}
