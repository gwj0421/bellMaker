package ics.mgs.controller;

import ics.mgs.dto.FileResponse;
import ics.mgs.dto.InputForm;
import ics.mgs.service.musicgen_service.MusicGenServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MusicGenController {
    private final MusicGenServiceImpl musicGenService;

    @PostMapping("/sendInfoToModel/{userId}")
    public String sendToModel(@ModelAttribute InputForm inputForm, @PathVariable String userId, Model model) {
        FileResponse response = musicGenService.sendClueToModelServer(userId, inputForm);
        if (response.getStatus().equals("OK")) {
            model.addAttribute("response", response);
        } else {
            model.addAttribute("response", FileResponse.makeEmptyResponse());
        }
        return "audio_form";
    }
}
