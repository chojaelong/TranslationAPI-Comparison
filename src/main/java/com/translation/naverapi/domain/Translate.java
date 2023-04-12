package com.translation.naverapi.domain;

import lombok.Data;

@Data
public class Translate {
    //번역할 언어 코드
    private String srcLangType;
    //번역된 언어 코드
    private String tarLangType;
    //번역 결과
    private String translatedText;

    private String engineType;
    private String pivot;
    private String dict;
    private String tarDict;
}
