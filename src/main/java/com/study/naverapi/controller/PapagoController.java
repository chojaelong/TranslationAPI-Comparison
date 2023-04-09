package com.study.naverapi.controller;

import com.study.naverapi.domain.Tran;
import com.study.naverapi.service.PapagoService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PapagoController {

    private final PapagoService papagoService;

    @GetMapping("/papago")
    public String papago() {
        return "/papago/tran";
    }

    @PostMapping("/papago/show")
    public String tran(TranForm tranForm, Model model) throws ParseException {
        List<Tran> trans = papagoService.tran(tranForm.getWord());
        model.addAttribute("trans", trans);

        return "/papago/show";
    }
}
