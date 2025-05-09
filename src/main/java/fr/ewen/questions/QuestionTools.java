package fr.ewen.questions;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuestionTools {

    @Tool("Gives the favourite colour of the user")
    public String favouriteColour(String userName) {
        return switch (userName) {
            case "Ewen" -> "blue";
            default -> "unknown";
        };
    }
}
