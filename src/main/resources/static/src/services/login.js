import request from '@/utils/request';
import {stringify} from "qs";

export async function accountLogin(params) {
  return request(`/api/login/getloginuser?${stringify(params)}`);
}


export async function getFakeCaptcha(mobile) {
  return request(`/api/login/captcha?mobile=${mobile}`);
}
