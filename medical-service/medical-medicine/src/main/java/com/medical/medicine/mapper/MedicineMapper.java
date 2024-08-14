package com.medical.medicine.mapper;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 1:41
 */

import com.medical.model.medicine.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MedicineMapper {

    @Select("SELECT * FROM medicines")
    List<Medicine> findAll();
    @Select("SELECT * FROM medicines WHERE id=#{id}")
    Medicine getById(Integer id);
}
