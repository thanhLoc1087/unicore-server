package com.unicore.classevent_service.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {
    private String id;
    private String name;
    @JsonProperty("thumbnail_link")
    private String thumbnailLink;
    @JsonProperty("webview_link")
    private String webviewLink;
    @JsonProperty("download_link")
    private String downloadLink;
}

