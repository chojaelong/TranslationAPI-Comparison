package com.translation.naverapi.domain;

import lombok.Data;

@Data
public class Translate {
    private String word;

    private String result;

    private String lang;
}
