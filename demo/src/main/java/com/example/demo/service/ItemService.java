package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 商品情報を削除するメソッド(物理削除)
     * 
     * @param id
     */
    public void delete(Integer id) {
        itemRepository.delete(id);
    }

}
