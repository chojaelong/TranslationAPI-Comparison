package com.translation.naverapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateInfo {

    private Long id;
    private String name; //ko
    private String english; //en
    private String japanese; //ja
    private String chinese; //zh-CN
    private String vietnamese; //vi
    private String german; //de
    private String french; //fr
}
