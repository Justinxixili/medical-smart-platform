package com.medical.appointment.service;

import com.medical.model.appointment.Appointment;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-09 21:29
 */

public interface AppointmentService {
    Result createAppointment(Appointment appointment,Integer userId);
    Result getAppointmentsByUserId(Integer userId);

    void updateAppointment(Appointment appointment);
    Result deleteAppointment(Integer id);





    Result getApUserId(Integer patientId);
}