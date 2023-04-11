package com.translation.naverapi.repository;

import com.translation.naverapi.domain.TranslateInfo;

import java.util.List;
import java.util.Optional;

public interface PapagoRepository {
    TranslateInfo save(TranslateInfo translateInfo);

    Optional<TranslateInfo> findById(Long id);

    Optional<TranslateInfo> findByName(String name);

    List<TranslateInfo> findAll();
}
