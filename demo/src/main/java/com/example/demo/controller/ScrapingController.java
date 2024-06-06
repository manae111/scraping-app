package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Item;
import com.example.demo.service.ScrapingService;

@Controller
@RequestMapping("")
public class ScrapingController {

    @Autowired
    private ScrapingService scrapingService;

    @RequestMapping("/toInsert")
    public String toInsert() {
        return "scraping_insert";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam("itemName") String itemNameOriginal,
                        @RequestParam("url") String url,
                        @RequestParam("itemPrice") String itemPriceStr
                        ){
        //jsから値段等を取得してitemにセット
        if(itemNameOriginal == null || url == null || itemPriceStr == null){
            System.out.println("解析不可");
            return "redirect:/toInsert";
        }
        Item item = new Item();
        String itemName = itemNameOriginal.replace(" ", "");
        item.setItemName(itemName);
        item.setUrl(url);
        itemPriceStr = itemPriceStr.replace(",", "");
        int itemPrice = Integer.parseInt(itemPriceStr); 
        item.setPriceOriginal(itemPrice);

        scrapingService.insert(item);
        return "redirect:/toInsert";
    }
}
