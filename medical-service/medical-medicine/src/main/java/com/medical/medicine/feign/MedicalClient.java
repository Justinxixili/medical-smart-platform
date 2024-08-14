package com.medical.medicine.feign;

import com.medical.feign.medicine.IMedicineClient;
import com.medical.medicine.service.MedicineService;
import com.medical.model.medicine.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-10 11:16
 */
@RestController
public class MedicalClient implements IMedicineClient {
    @Autowired
    private MedicineService medicineService;

    @Override
    @GetMapping("/medicine/client/{id}")
    public Medicine findMedicineById(@PathVariable("id") Integer id) {
        return medicineService.getById(id);
    }
}
