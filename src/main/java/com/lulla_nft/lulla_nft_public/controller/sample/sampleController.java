package com.lulla_nft.lulla_nft_public.controller.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample/")
public class sampleController {
    @GetMapping("1")
    public String sample1(){
        return "sample";
    }
}
