package com.unicore.classevent_service.repository.httpclient;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.unicore.classevent_service.dto.response.FileResponse;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import org.springframework.core.io.buffer.DataBufferUtils;

@Component
public class FileClient {

    private final WebClient webClient;

    public FileClient(@Value("${application.service.file}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<List<FileResponse>> uploadFiles(List<FilePart> files, String userEmail) {
        // Convert each FilePart to a Tuple2 containing its ByteArrayResource and content type
        return Flux.fromIterable(files)
            .flatMap(filePart -> {
                MediaType contentType = filePart.headers().getContentType();
                return DataBufferUtils.join(filePart.content())
                    .map(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);
                        ByteArrayResource resource = new ByteArrayResource(bytes) {
                            @Override
                            public String getFilename() {
                                return filePart.filename();
                            }
                        };
                        return Tuples.of(resource, contentType);
                    });
            })
            .collectList()
            .flatMap(resourceTuples -> {
                MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
                // Add each file as a part
                for (Tuple2<ByteArrayResource, MediaType> tuple : resourceTuples) {
                    ByteArrayResource resource = tuple.getT1();
                    MediaType ct = tuple.getT2();
                    bodyBuilder.part("files", resource)
                        .filename(resource.getFilename())
                        .contentType(ct != null ? ct : MediaType.APPLICATION_OCTET_STREAM);
                }
                // Add the userEmail field
                bodyBuilder.part("userEmail", userEmail);

                return webClient.post()
                    .uri("/save")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<FileResponse>>() {});
            });
    }
}
