package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.ItemService;

@Controller
@RequestMapping("")
public class ItemController {

    @Autowired
    private ItemService itemService;

    

}
