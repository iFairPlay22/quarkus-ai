package fr.ewen.questions;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.file.Path;
import java.util.List;

import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@ApplicationScoped
public class QuestionRagIngestion {

    public void ingest(
        @Observes StartupEvent ev,
        EmbeddingStore<TextSegment> store,
        EmbeddingModel model,
        @ConfigProperty(name = "ai.rag.location") Path path
    ) {
        Log.infof("Starting rag ingestion...");

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
            .embeddingStore(store)
            .embeddingModel(model)
            .documentSplitter(recursive(100, 25))
            .build();

        List<Document> documents = FileSystemDocumentLoader.loadDocumentsRecursively(path);
        documents.forEach(document -> Log.infof("Ingesting: %s", document));
        ingestor.ingest(documents);

        Log.info("Ending rag ingestion...");
    }
}
