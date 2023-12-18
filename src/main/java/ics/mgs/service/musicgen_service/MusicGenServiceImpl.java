package ics.mgs.service.musicgen_service;

import ics.mgs.dto.FileResponse;
import ics.mgs.dto.InputForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class MusicGenServiceImpl implements MusicGenService {
    private final WebClient webClient;

    public FileResponse sendClueToModelServer(String userId, InputForm inputForm) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("user_id", userId);
        builder.part("texts", String.join("|", inputForm.getTexts()));
        builder.part("token_cnt", String.valueOf(inputForm.getTokenCnt()));

        return webClient.post()
                .uri("http://127.0.0.1:8000/getMelody")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(FileResponse.class)
                .block();
    }
}
