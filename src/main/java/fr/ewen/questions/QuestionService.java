package fr.ewen.questions;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import io.quarkiverse.langchain4j.response.ResponseAugmenter;

@RegisterAiService
public interface QuestionService {

    @SystemMessage("""
        You are a kind ai that answers to questions.
    """)
    @UserMessage("""
        ---
        Question: {question}
        ---
    """)
    @ToolBox(QuestionTools.class)
    @ResponseAugmenter(QuestionResponseAugmenter.class)
    String answerTo(String question);
}
