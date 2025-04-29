package fr.ewen.ocr;

import dev.langchain4j.data.image.Image;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.server.multipart.FileItem;
import org.jboss.resteasy.reactive.server.multipart.FormValue;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

@Path("/ocr")
public class OcrResource {

    @Inject OcrService ocrService;

    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> ocr(MultipartFormDataInput input) {
       return extractFiles(input)
           .map(OcrResource::toBytes)
           .map(OcrResource::toImage)
           .map(ocrService::process)
           .toList();
    }

    private static Stream<java.nio.file.Path> extractFiles(MultipartFormDataInput file) {
        return Optional.ofNullable(file)
                .stream()
                .map(MultipartFormDataInput::getValues)
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .filter(FormValue::isFileItem)
                .map(FormValue::getFileItem)
                .map(FileItem::getFile);
    }

    private static byte[] toBytes(java.nio.file.Path filePath) {
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to read file", ex);
        }
    }

    private static final String MIME_TYPE = "image/jpeg";

    private static Image toImage(byte[] path) {
        return Optional.ofNullable(path)
            .map(Base64.getEncoder()::encodeToString)
            .map(Image.builder()::base64Data)
            .map(b -> b.mimeType(MIME_TYPE))
            .map(Image.Builder::build)
            .orElseThrow(() -> new IllegalStateException("Unable to convert byte[] to image"));
    }
}
