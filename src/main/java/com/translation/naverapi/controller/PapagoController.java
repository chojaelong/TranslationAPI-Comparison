package com.translation.naverapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.translation.naverapi.domain.Translate;
import com.translation.naverapi.domain.TranslateForm;
import com.translation.naverapi.domain.TranslateInfo;
import com.translation.naverapi.service.PapagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/papago")
@RequiredArgsConstructor
public class PapagoController {

    private final PapagoService papagoService;

    @GetMapping
    public String papago() {
        return "papago/tran";
    }

    @PostMapping("/show")
    public String tran(TranslateForm tranForm, Model model) throws JsonProcessingException {
        log.info("Translate Start, Target: {}", tranForm.getWord());
        TranslateInfo translateInfo = papagoService.translate(tranForm.getWord());
        model.addAttribute("translateInfo", translateInfo);

        return "papago/show";
    }
}
