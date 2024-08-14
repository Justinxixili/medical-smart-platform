import request from '@/utils/request.js'

export const userRegisterService=(registerData)=>{

   return  request.post('/user/user/register',registerData);
}

export const userLoginServer=(loginData)=>{

    return request.post('/user/user/login',loginData)
}
export  const AllUsersServer=(params)=>{
    return request.get('/user/user/allUsers',{params:params})

}

//获取用户详细信息
export  const UserInfoServer=()=>{
    return request.get('/user/user/userInfo')

}

export  const UpdataAuatarServer=(avatarUrl)=>{
  const params= new URLSearchParams;
  params.append('avatarUrl',avatarUrl)
    return request.patch('/user/user/updateAvatar',params)

}

export const UserDeleteServer = (id) => {
    return request.delete(`/user/user/delete/${id}`);
}
export const UserUpdateServer = (AllUserModel) => {
    return request.put('/user/user/update', AllUserModel)
}