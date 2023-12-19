package ics.mgs.controller;

import ics.mgs.dto.FileResponse;
import ics.mgs.dto.InputForm;
import ics.mgs.service.database.bell.BellRepositoryService;
import ics.mgs.service.database.site_user.SiteUserRepositoryService;
import ics.mgs.service.musicgen_service.MusicGenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MusicGenController {
    private final MusicGenService musicGenService;
    private final SiteUserRepositoryService userRepositoryService;
    private final BellRepositoryService bellRepositoryService;

    @PostMapping("/sendInfoToModel/{userId}")
    public String sendToModel(@ModelAttribute InputForm inputForm, @PathVariable String userId) {
        FileResponse response = musicGenService.sendClueToModelServer(userId, inputForm);

        bellRepositoryService.saveBellToUser(userId, response);

        return "redirect:/showMusic/" + userId;
    }

    @GetMapping("/showMusic/{userId}")
    public String showMusic(@PathVariable String userId, Model model) {
        model.addAttribute("userContent", userRepositoryService.getSiteUserByUserId(userId).getBells());

        return "user_audio_form";
    }
}
