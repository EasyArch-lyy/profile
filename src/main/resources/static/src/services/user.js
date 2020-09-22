import request from '@/utils/request';

export async function query() {
  return request('/api/users');
}
// 登录获取用户信息
export async function queryUser() {
  return request('/login/getloginuser');
}
export async function queryCurrent() {
  return request('/api/currentUser');
}
export async function queryNotices() {
  return request('/api/notices');
}
