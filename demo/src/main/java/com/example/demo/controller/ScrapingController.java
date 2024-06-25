package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.CustomUserDetails;
import com.example.demo.domain.Item;
import com.example.demo.domain.User;
import com.example.demo.form.UserForm;
import com.example.demo.service.ItemService;
import com.example.demo.service.ScrapingService;

@Controller
@RequestMapping("")
public class ScrapingController {

    private static final Logger logger = LoggerFactory.getLogger(ScrapingController.class);

    @Autowired
    private ScrapingService scrapingService;

    @Autowired
    private ItemService itemService;

    //ログイン
    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    //新規登録
    @GetMapping("/toRegister")
    public String toRegister(UserForm userForm) {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserForm userform,
                        RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setUsername(userform.getUsername());
        user.setPassword(userform.getPassword());
        scrapingService.insertUser(user);
        return "redirect:/toLogin";
    }

    //スクレイピングURL登録画面
    @RequestMapping("/toInsert")
    public String toInsert(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Integer userId = userDetails.getId();
        List<Item> itemList = scrapingService.findItemByUserId(userId);
        model.addAttribute("itemList", itemList);
        return "scraping_insert";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam("itemName") String itemNameOriginal,
                        @RequestParam("url") String url,
                        @RequestParam("itemPrice") String itemPriceStr,
                        @AuthenticationPrincipal CustomUserDetails userDetails,
                        RedirectAttributes redirectAttributes) {
        // jsから値段等を取得してitemにセット
        if (itemNameOriginal.isEmpty() || url.isEmpty() || itemPriceStr.isEmpty() || userDetails.getId()==null) {
            logger.error("解析が失敗しました");
            redirectAttributes.addFlashAttribute("message", "解析が失敗しました");
            return "redirect:/toInsert";
        } else {
            Item item = new Item();
            String itemName = itemNameOriginal.replace(" ", "");
            item.setItemName(itemName);
            item.setUrl(url);
            item.setUserId(userDetails.getId());
            itemPriceStr = itemPriceStr.replace(",", "");
            int itemPrice = Integer.parseInt(itemPriceStr);
            item.setPriceOriginal(itemPrice);
            
            scrapingService.insert(item);
            redirectAttributes.addFlashAttribute("message", "解析が完了しました");
        }
        return "redirect:/toInsert";
    }

    /**
     * 商品削除
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        itemService.delete(id);
        return "redirect:/toInsert";
    }



}
