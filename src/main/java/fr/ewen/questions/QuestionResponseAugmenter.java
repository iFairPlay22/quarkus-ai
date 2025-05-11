package fr.ewen.questions;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.AugmentationResult;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.store.embedding.CosineSimilarity;
import io.quarkiverse.langchain4j.response.AiResponseAugmenter;
import io.quarkiverse.langchain4j.response.ResponseAugmenterParams;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionResponseAugmenter implements AiResponseAugmenter<String> {

    private static final String DIR_PATH = "absolute_directory_path";
    private static final String FILE_PATH = "file_name";
    private static final double SOURCE_PRECISION = 0.75;

    @Inject EmbeddingModel embeddingModel;

    @Override
    public Multi<String> augment(Multi<String> responses, ResponseAugmenterParams params) {
        if (responses == null) return null;

        StringBuilder response = new StringBuilder();
        return responses
            .invoke(response::append)
            .onCompletion()
            .continueWith(() -> List.of(augment(response.toString(), params)));
    }

    @Override
    public String augment(String response, ResponseAugmenterParams params) {
        if (response == null) return null;

        return response + "\n\nSources: " + String.join(", ",  retrieveSources(response, params)) ;
    }


    private Set<String> retrieveSources(String response, ResponseAugmenterParams params) {
        Log.infof("Augmenting response %s", response);

        // Params validation
        Optional<String> maybeResponse = Optional.ofNullable(response)
            .filter(e -> !e.isBlank());

        Optional<List<Content>> maybeAugmentationParams = Optional.ofNullable(params)
            .map(ResponseAugmenterParams::augmentationResult)
            .map(AugmentationResult::contents)
            .filter(e -> !e.isEmpty());
        if (maybeResponse.isEmpty() || maybeAugmentationParams.isEmpty()) return Collections.emptySet();

        // Sources retrieval
        Embedding responseEmbedding = embeddingModel.embed(maybeResponse.orElseThrow()).content();
        return maybeAugmentationParams.orElseThrow().stream()
            .map(augmenterParam -> {
                Embedding augmenterEmbedding = embeddingModel.embed(augmenterParam.textSegment().text()).content();
                
                if (CosineSimilarity.between(responseEmbedding, augmenterEmbedding) < SOURCE_PRECISION) return null;
                return String.format(
                    "%s/%s",
                    augmenterParam.textSegment().metadata().getString(DIR_PATH),
                    augmenterParam.textSegment().metadata().getString(FILE_PATH)
                );
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }
}
