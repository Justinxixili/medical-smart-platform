package com.medical.appointment.controller;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-09 21:30
 */


import com.medical.appointment.service.AppointmentService;
import com.medical.model.appointment.Appointment;
import com.medical.model.common.dtos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/username")
    public Result getAppointmentsByUsername(@RequestParam String username,
                                            @RequestParam String identity ) {
        return appointmentService.getAppointmentUsername(username,identity);
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