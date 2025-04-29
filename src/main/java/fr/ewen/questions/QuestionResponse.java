package fr.ewen.questions;

import com.fasterxml.jackson.annotation.JsonCreator;

public record QuestionResponse(
    String question,
    String answer
) {

    @JsonCreator
    public QuestionResponse {}
}
