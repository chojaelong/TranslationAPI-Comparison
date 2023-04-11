package com.translation.naverapi.repository;

import com.translation.naverapi.domain.TranslateInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class MemoryPapagoRepository implements PapagoRepository{

    private static Map<String, TranslateInfo> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public TranslateInfo save(TranslateInfo translateInfo) {
        translateInfo.setId(++sequence);
        store.put(translateInfo.getName(), translateInfo);
        return translateInfo;
    }

    @Override
    public Optional<TranslateInfo> findById(Long id) {
        return store.values().stream()
                .filter(translateInfo -> translateInfo.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<TranslateInfo> findByName(String name) {
        return Optional.ofNullable(store.get(name));
    }

    @Override
    public List<TranslateInfo> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
