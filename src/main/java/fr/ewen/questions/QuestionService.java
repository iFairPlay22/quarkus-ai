package fr.ewen.questions;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface QuestionService {

    @SystemMessage("""
        You are a kind ai that answers to questions.

        You will always answer with a JSON document, and only this JSON document.
        You should write the asked question in the "question" section, and your
        answer in the "answer" section.
    """)
    @UserMessage("""
        ---
        Question: {question}
        ---
    """)
    QuestionResponse answerTo(String question);
}
