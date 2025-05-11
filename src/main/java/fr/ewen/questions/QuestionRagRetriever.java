package fr.ewen.questions;

import dev.langchain4j.data.message.*;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.List;

public class QuestionRagRetriever {

    @Produces
    @ApplicationScoped
    public RetrievalAugmentor retrieve(
        EmbeddingStore<TextSegment> store,
        EmbeddingModel model
    ) {
        Log.infof("Starting rag retrieval...");

        EmbeddingStoreContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
            .embeddingModel(model)
            .embeddingStore(store)
            .build();

        DefaultRetrievalAugmentor contentAugmenter = DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .contentInjector((contents, message) -> {
                    StringBuilder prompt = new StringBuilder(
                        switch (message) {
                            case null -> "";
                            case UserMessage u -> u.singleText();
                        }
                    );

                    prompt.append("\nPlease, use the following information:\n");
                    contents.forEach(content -> prompt.append("- ").append(content.textSegment().text()).append("\n"));

                    Log.infof("Using prompt '%s'", prompt.toString());
                    return new UserMessage(prompt.toString());
                })
                .build();

        Log.infof("Ending rag retrieval...");

        return contentAugmenter;
    }
}
