package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

/**
 * リポジトリクラス
 */
@Repository
public class ScrapingRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs,i) -> {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setUrl(rs.getString("url"));
        item.setItemName(rs.getString("item_name"));
        item.setPriceOriginal(rs.getInt("price_original"));
        item.setPriceLatest(rs.getInt("price_latest"));
        item.setUserId(rs.getInt("user_id"));
        return item;
    };

    /**
     * 商品情報を挿入するメソッド
     * @param item
     */
    public void insert(Item item) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        String sql = "INSERT INTO items (url, item_name, price_original, price_latest, user_id) VALUES (:url, :itemName, :priceOriginal, :priceLatest, :userId)";
        template.update(sql, param);
    }

    /**
     * 最新価格を更新するメソッド
     * @param item
     * @return
     */
    public Item update(Item item) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        String sql = "UPDATE items SET price_latest = :priceLatest WHERE id = :id";
        template.update(sql, param);
        return item;
    }


}
