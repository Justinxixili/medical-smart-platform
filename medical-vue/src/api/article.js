import request from "@/utils/request.js";
import {userTokenStore} from "@/stores/token.js";

export const articleCategoryListService=()=>{
    // const tokenStore=userTokenStore();
    // tokenStore.token
    // return request.get('/category',{headers:{'Authorization':tokenStore.token}});
    return request.get('/category');

}
export const articleCategoryAddService=(categoryData)=>{
  return   request.post('/category',categoryData)
}