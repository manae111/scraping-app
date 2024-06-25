package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * 商品情報を削除するメソッド(物理削除)
     * 
     * @param id
     */
    public void delete(Integer id) {
        String sql = """
                    DELETE FROM items WHERE id = :id;
                    """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(sql, param);
    }

}
