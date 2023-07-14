package com.example.NTSBusinessNum.Site;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class SiteController {
    private final SiteService siteService;

    public SiteController(SiteService siteService){
        this.siteService = siteService;
    }

    @GetMapping("/siteSearch")
    public String siteSearchMain(){
        return "siteSearch";
    }

    @PostMapping("/siteSearch")
    public String siteSearch(SiteSearchForm siteSearchForm, Model model) throws IOException {
        String siteUrl = siteSearchForm.getSiteUrl();
        JSONObject jsonObject =  siteService.searchSiteUrl(siteUrl);

        model.addAttribute("businessNumber", jsonObject.get("businessNumber"));
        model.addAttribute("state", jsonObject.get("state"));

        return "result";
    }

//    @GetMapping("/site")
//    public String site(Model model) throws Exception{
//        List<Site> siteList = SiteService.getDatas();
//        model.addAttribute("site", siteList);
//
//        return "result";
//    }

}
