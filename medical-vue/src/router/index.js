import {createRouter,createWebHashHistory} from 'vue-router'

import LayOut from "@/views/LayOut.vue";
import LoginVue from '@/views/Login.vue'
import ArticleCategory from "@/views/notice/NoticeCategory.vue";
import ArticleManage from "@/views/notice/NoticeManage.vue";
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
import NoticeCategory from "@/views/notice/NoticeCategory.vue";
import NoticeManage from "@/views/notice/NoticeManage.vue";


const routes=[
    {path:'/myindex/web',component: myindex},

    {path:'/',component:LayOut,redirect:'/webindex',meta:{title:"首页"},children:[
            {path:'user/date',component:myssdate },
            {path:'user/menu',component:Menu,meta:{title:"菜单"}},
            {path:'user/date',component:myssdate,meta:{title:"日期"}},

            {path:'article/category',component:ArticleCategory ,meta:{title:"公告分类"}},
            {path:'article/manage',component:ArticleManage, meta: { title: "公告管理" }},
            {path:'user/info',component:UserInfo,meta:{title:"用户信息"}},
            {path:'user/avatar',component:UserAvatarr,meta:{title:"更改头像"}},
            {path:'user/resetPassword',component:UserResetPassword ,    meta: { title: "重置密码" }},
            {path:'user/allUsers',component:AllUsers ,meta:{title:"用户信息"}},
            {path:'user/allDoctor',component:AllDoctor,meta:{title:"医生信息管理"} },
            {path:'user/allPatients',component:AllPatients ,meta:{title:"患者信息管理"}},
            {path:'user/allMedicine',component:AllMedicine ,meta:{title:"药品信息管理"}},

            {path: 'user/usercontro',component: UserContro, meta: { title: "用户管理" }},
            {path: 'appointment/department',component: AppointmentDepartment,   meta: { title: "预约管理" }},
            {path: 'appointment/manage',component: AppointmentManage,   },
            {path:'doctor/hiring',component: Employment,meta:{title:"医生辞职和聘用管理"}},
            {path: 'appointment/wwww',component: Report,  meta: { title: "报表" }},
            {path:'/noticeCategory',component: NoticeCategory,  meta: { title: "公告分类" } },
            {path:'/noticeManage',component: NoticeManage}

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