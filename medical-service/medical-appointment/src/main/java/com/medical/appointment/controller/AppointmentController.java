package com.medical.appointment.controller;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-09 21:30
 */


import com.medical.appointment.service.AppointmentService;
import com.medical.appointment.service.Impl.AppointmentServiceImpl;
import com.medical.model.appointment.Appointment;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public Result createAppointment(@RequestBody Appointment appointment,@RequestHeader("id") Integer userId) {

        return appointmentService.createAppointment(appointment,userId);
    }


    @GetMapping("/detail")
    public Result getAppointmentsByUserId(@RequestParam Integer userId) {

        return appointmentService.getAppointmentsByUserId(userId);
    }

    @GetMapping("/client/detail")
    public Result getApUserId(@RequestParam Integer patientId) {

        return appointmentService.getApUserId(patientId);
    }

    @DeleteMapping
    public Result deleteAppointment(@RequestParam Integer id) {

        return appointmentService.deleteAppointment(id);
    }

}