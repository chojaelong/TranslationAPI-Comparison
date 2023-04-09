package com.study.naverapi.service;

import com.study.naverapi.domain.Tran;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface PapagoService {
    public List<Tran> tran(String word) throws ParseException;
}
