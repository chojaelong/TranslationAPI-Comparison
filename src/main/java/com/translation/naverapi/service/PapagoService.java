package com.translation.naverapi.service;

import com.translation.naverapi.domain.Translate;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface PapagoService {
    List<Translate> tran(String word) throws ParseException;
}
