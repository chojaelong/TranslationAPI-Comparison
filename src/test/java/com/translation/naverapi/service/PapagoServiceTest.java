package com.translation.naverapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.translation.naverapi.domain.TranslateInfo;
import com.translation.naverapi.repository.MemoryPapagoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PapagoServiceTest {

    @Autowired
    PapagoService papagoService;

    @AfterEach
    void afterEach() {

    }

    @Test
    void translate() throws JsonProcessingException {
        String word = "자동차";
        TranslateInfo translateInfo = papagoService.translate(word);
    }

}