package com.medical.user.mapper;

import com.medical.model.user.pojos.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("SELECT * FROM menu")
    List<Menu> getAllMenus();

    @Insert("INSERT INTO menu (parent_id, title, icon, route, `order`, is_active) VALUES (#{parentId}, #{title}, #{icon}, #{route}, #{order}, #{isActive})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveMenu(Menu menu);
    @Delete("DELETE FROM menu WHERE id = #{id}")
    void deleteMenu(Integer id);
}