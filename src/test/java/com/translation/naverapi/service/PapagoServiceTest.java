package com.translation.naverapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.translation.naverapi.domain.TranslateInfo;
import com.translation.naverapi.repository.MemoryPapagoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PapagoServiceTest {

    PapagoService papagoService = new PapagoService(new MemoryPapagoRepository());

    @AfterEach
    void afterEach() {

    }

    @Test
    void translate() throws JsonProcessingException {

    }

}