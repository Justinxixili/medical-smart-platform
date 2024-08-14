import request from '@/utils/request.js'
export const allDoctorService=(params)=>{
    return   request.get('/doctor/doctor',{params:params})
}

export const deleteDoctorService = (doctorId) => {
    return request.delete(`/doctor/doctor/${doctorId}`);
}
export const updateDoctorService = (AllDoctorModel) => {
    return request.put('/doctor/doctor/update',AllDoctorModel);
}