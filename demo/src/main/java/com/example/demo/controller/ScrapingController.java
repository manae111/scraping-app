package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Item;
import com.example.demo.service.ScrapingService;

@Controller
@RequestMapping("")
public class ScrapingController {

    private static final Logger logger = LoggerFactory.getLogger(ScrapingController.class);

    @Autowired
    private ScrapingService scrapingService;

    @RequestMapping("/toInsert")
    public String toInsert() {
        return "scraping_insert";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam("itemName") String itemNameOriginal,
            @RequestParam("url") String url,
            @RequestParam("itemPrice") String itemPriceStr,
            RedirectAttributes redirectAttributes) {
        // jsから値段等を取得してitemにセット
        if (itemNameOriginal.isEmpty() || url.isEmpty() || itemPriceStr.isEmpty()) {
            logger.error("解析が失敗しました");
            redirectAttributes.addFlashAttribute("message", "解析が失敗しました");
            return "redirect:/toInsert";
        } else {
            Item item = new Item();
            String itemName = itemNameOriginal.replace(" ", "");
            item.setItemName(itemName);
            item.setUrl(url);
            itemPriceStr = itemPriceStr.replace(",", "");
            int itemPrice = Integer.parseInt(itemPriceStr);
            item.setPriceOriginal(itemPrice);

            scrapingService.insert(item);
            redirectAttributes.addFlashAttribute("message", "解析が完了しました");
        }

        return "redirect:/toInsert";
    }
}
