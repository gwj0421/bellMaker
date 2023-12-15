package ics.mgs.service.musicgen_service;

import ics.mgs.dto.FileResponse;
import ics.mgs.dto.InputForm;

public interface MusicGenService {
    public FileResponse sendClueToModelServer(String userId, InputForm inputForm);
}
