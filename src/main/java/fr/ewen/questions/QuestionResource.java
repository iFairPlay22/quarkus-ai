package fr.ewen.questions;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/questions")
public class QuestionResource {

    @Inject QuestionService questionService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String answer(@QueryParam("q") String question) {
        return questionService.answerTo(question);
    }
}
