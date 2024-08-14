import request from '@/utils/request.js'
export const alldepartmentService=()=>{
    return   request.get('/department/departments')
}

