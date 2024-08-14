package com.medical.feign.medicine;

import com.medical.model.medicine.Medicine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-10 11:10
 */
@FeignClient("medical-medicine")
public interface IMedicineClient {

    @GetMapping("/medicine/client/{id}")
    Medicine findMedicineById(@PathVariable("id") Integer id);

}
