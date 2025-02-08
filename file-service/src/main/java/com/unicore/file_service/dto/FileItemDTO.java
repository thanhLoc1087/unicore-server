package com.unicore.file_service.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.services.drive.model.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileItemDTO {
    private String id;
    private String name;
    @JsonProperty("thumbnail_link")
    private String thumbnailLink;
    @JsonProperty("webview_link")
    private String webviewLink;
    @JsonProperty("download_link")
    private String downloadLink;

    public FileItemDTO(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.downloadLink = file.getWebContentLink();
        this.webviewLink = file.getWebViewLink();
        this.thumbnailLink = file.getThumbnailLink();
    }
}
