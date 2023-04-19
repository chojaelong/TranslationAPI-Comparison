package com.translation.naverapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.translation.naverapi.domain.TranslateInfo;
import com.translation.naverapi.repository.MemoryPapagoRepository;
import com.translation.naverapi.repository.PapagoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class PapagoServiceTest {

    @Autowired
    PapagoService papagoService;
    @Autowired
    MemoryPapagoRepository memoryPapagoRepository;

    @AfterEach
    void afterEach() {
        memoryPapagoRepository.clearStore();
    }

    @Test
    void translate() throws JsonProcessingException {
        TranslateInfo target = new TranslateInfo(
                1L, "자동차", "Car", "自動車",
                "汽车", "xe hơi", "ein Auto", "Voiture");
        TranslateInfo translateInfo = papagoService.translate("자동차");

        Assertions.assertThat(target).isEqualTo(translateInfo);
    }
}