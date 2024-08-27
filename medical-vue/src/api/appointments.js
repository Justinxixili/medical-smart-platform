import request from '@/utils/request.js'
export const allAppointmentsService=(params)=>{
    return   request.get('/appointment/appointments',{params:params})
}

export const appointmentDepartmentService=()=>{
    return   request.get('/appointment/categoryDepartment')
}
export const AddDepartmentService=(categoryData)=>{
    return   request.post('/appointment/categoryDepartment',categoryData)
}

export const updatedepartmentService=(categoryData)=>{
    return   request.put('/appointment/categoryDepartment',categoryData)
}
export const deletedepartmentService=(id)=>{
    return   request.delete('/appointment/categoryDepartment?id='+id)
}

export const getUserAppointment=(patientId)=> {
    return request.get(`/appointment/appointments/client/detail?patientId=${patientId}`)
}

export const getAllAppointments = () => {
    return request.get(`/appointment/appointments/date`);

}



export const getUsernameAppointment = (username, identity) => {
    return request.get(`/appointment/appointments/username`, {
        params: { username, identity }
    });
};