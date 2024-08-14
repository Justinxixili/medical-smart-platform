import {defineStore} from "pinia";
import {ref} from "vue";

const userUserINfoStore =defineStore('userInfo',()=>{
    //定义状态
    const info=ref({})
    const setInfo=(newInfo)=>{
        info.value=newInfo
    }

    const removeInfo=()=>{
        info.value={}
    }
    return {info,setInfo,removeInfo}
},{persist:true})
export default userUserINfoStore;