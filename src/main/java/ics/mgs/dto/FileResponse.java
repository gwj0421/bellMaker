package ics.mgs.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class FileResponse {
    private final String status;
    @JsonProperty(value = "content")
    private final List<List<String>> content;

    public FileResponse(String status, List<List<String>> content) {
        this.status = status;
        this.content = content;
    }

    @JsonIgnore
    public static FileResponse makeEmptyResponse() {
        return new FileResponse("NOT_FOUND", null);
    }
}
