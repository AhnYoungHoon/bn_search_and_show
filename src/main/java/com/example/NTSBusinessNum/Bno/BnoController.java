package com.example.NTSBusinessNum.Bno;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class BnoController {
    private final BnoService bnoService;

    public BnoController(BnoService bnoService){
        this.bnoService=bnoService;
    }

    @GetMapping("/bnoSearch")
    public String bnoSearchMain(){
        return "bnoSearch";
    }

    @PostMapping("/bnoSearch")
    public String bnoSearch(BnoSearchForm bnoSearchForm, Model model) throws IOException {
        String b_no = bnoSearchForm.getB_no();
        JSONObject jsonObject = bnoService.searchBno(b_no);
        model.addAttribute("businessNumber", jsonObject.get("businessNumber"));
        model.addAttribute("state", jsonObject.get("state"));

        return "result";

    }
}
