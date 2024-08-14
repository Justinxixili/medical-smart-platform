import request from '@/utils/request.js'
export const allEmploymentService=(params)=>{
    return   request.get('/doctor/doctorEmployment',{params:params})
}

export const UpdateEmploymentService=(AllEmploymentModle)=>{
    return request.put('/doctor/doctorEmployment',AllEmploymentModle);
}

export const DeleteEmploymentService=(id)=>{
    return request.delete(`/doctor/doctorEmployment/${id}`);
}