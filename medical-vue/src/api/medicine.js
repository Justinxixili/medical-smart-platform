import request from '@/utils/request.js'
export const allMedicineService=(params)=>{
    return   request.get('/medicine/medicines',{params:params})
}

export class updateMedicineService {
}