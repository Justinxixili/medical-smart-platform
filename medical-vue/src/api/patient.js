import request from '@/utils/request.js'
export const allPatientService=(params)=>{
    return   request.get('/patient/patients',{params:params})
}

export const getPatientList=()=>{
    return request.get('/patient/patients/list')
}


