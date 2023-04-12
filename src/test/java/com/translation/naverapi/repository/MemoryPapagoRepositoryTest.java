package com.translation.naverapi.repository;

import com.translation.naverapi.domain.TranslateInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryPapagoRepositoryTest {

    MemoryPapagoRepository memoryPapagoRepository = new MemoryPapagoRepository();

    @AfterEach
    void afterEach() {
        memoryPapagoRepository.clearStore();
    }

    @Test
    void save() {
        TranslateInfo translateInfo = new TranslateInfo();
        translateInfo.setName("조재호");
        TranslateInfo saved = memoryPapagoRepository.save(translateInfo);

        TranslateInfo searched = memoryPapagoRepository.findById(saved.getId()).get();
        Assertions.assertThat(saved).isEqualTo(searched);
    }

    @Test
    void findAll() {
        TranslateInfo translateInfo1 = new TranslateInfo();
        translateInfo1.setName("조재호");
        TranslateInfo translateInfo2 = new TranslateInfo();
        translateInfo2.setName("박찬진");

        memoryPapagoRepository.save(translateInfo1);
        memoryPapagoRepository.save(translateInfo2);

        List<TranslateInfo> searched = memoryPapagoRepository.findAll();

        Assertions.assertThat(searched.size()).isEqualTo(2);
        Assertions.assertThat(searched).contains(translateInfo1, translateInfo2);
    }
}