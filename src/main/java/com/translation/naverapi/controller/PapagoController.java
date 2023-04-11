package com.translation.naverapi.controller;

import com.translation.naverapi.domain.Translate;
import com.translation.naverapi.domain.TranslateForm;
import com.translation.naverapi.service.PapagoService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/papago")
@RequiredArgsConstructor
public class PapagoController {

    private final PapagoService papagoService;

    @GetMapping
    public String papago() {
        return "/papago/tran";
    }

    @PostMapping("/show")
    public String tran(TranslateForm tranForm, Model model) throws ParseException {
        List<Translate> trans = papagoService.tran(tranForm.getWord());
        model.addAttribute("trans", trans);

        return "/papago/show";
    }
}
