import request from '@/utils/request.js'
export const allPatientService=(params)=>{
    return   request.get('/patient/patient',{params:params})
}

export const getPatientList=()=>{
    return request.get('/patient/patient/list')
}


