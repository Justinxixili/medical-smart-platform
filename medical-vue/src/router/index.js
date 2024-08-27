import {createRouter,createWebHashHistory} from 'vue-router'

import LayOut from "@/views/LayOut.vue";
import LoginVue from '@/views/Login.vue'
import ArticleCategory from "@/views/article/ArticleCategory.vue";
import ArticleManage from "@/views/article/ArticleManage.vue";
import UserAvatarr from "@/views/user/UserAvatar.vue";
import UserInfo from "@/views/user/UserInfo.vue";
import UserResetPassword from "@/views/user/UserResetPassword.vue";
import AllUsers from '@/views/user/allUsers.vue';
import AllPatients from "@/views/user/allPatients.vue";
import AllDoctor from "@/views/user/AllDoctor.vue";
import AllMedicine from "@/views/medicine/AllMedicine.vue";
import myssdate from "@/views/date/date.vue";
import UserContro from "@/views/user/UserContro.vue";
import AppointmentDepartment from "@/views/appointment/AppointmentDepartment.vue";
import AppointmentManage from "@/views/appointment/AppointmentManage.vue";
import Employment from "@/views/employment/Employment.vue";
import Report from "@/views/report/Report.vue";
import  myindex from "@/views/myindex.vue";
import webindex from "@/views/webitem/webindex.vue"
import doctorindex from '@/views/webitem/doctorindex.vue'
import Menu from '@/views/user/Menu.vue'
import webAappointment from '@/views/webitem/webAppointment.vue'
const routes=[
    {path:'/myindex/web',component: myindex},

    {path:'/',component:LayOut,redirect:'/webindex',meta:{title:"首页"},children:[
            {path:'user/menu',component:Menu},
            {path:'article/category',component:ArticleCategory},
            {path:'article/manage',component:ArticleManage},
            {path:'user/info',component:UserInfo,meta:{title:"用户信息"}},
            {path:'user/avatar',component:UserAvatarr,meta:{title:"更改头像"}},
            {path:'user/resetPassword',component:UserResetPassword },
            {path:'user/allUsers',component:AllUsers ,meta:{title:"首页"}},
            {path:'user/allDoctor',component:AllDoctor,meta:{title:"医生信息管理"} },
            {path:'user/allPatients',component:AllPatients ,meta:{title:"患者信息管理"}},
            {path:'user/allMedicine',component:AllMedicine ,meta:{title:"药品信息管理"}},
            {path:'user/date',component:myssdate },
            {path: 'user/usercontro',component: UserContro},
            {path: 'appointment/department',component: AppointmentDepartment},
            {path: 'appointment/manage',component: AppointmentManage},
            {path:'doctor/hiring',component: Employment,meta:{title:"医生辞职和聘用管理"}},
            {path: 'appointment/wwww',component: Report}

        ]},
        {path: '/',component: myindex,redirect: '/webindex',children: [
                {path: '/webindex',component: webindex},
                {path: '/doctorindex',component: doctorindex},
                {path:'/adminLogin',component:LoginVue},
                {path: '/webAappointment', component: webAappointment}
            ]},
]

const router= createRouter({
    history:createWebHashHistory(),
    routes:routes
})

export default router