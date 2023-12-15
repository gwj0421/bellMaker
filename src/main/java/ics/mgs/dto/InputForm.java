package ics.mgs.dto;

import java.util.List;

public class InputForm {
    private final List<String> texts;
    private final int tokenCnt;

    public InputForm(List<String> texts, int tokenCnt) {
        this.texts = texts;
        this.tokenCnt = tokenCnt;
    }

    public List<String> getTexts() {
        return texts;
    }

    public int getTokenCnt() {
        return tokenCnt;
    }
}
