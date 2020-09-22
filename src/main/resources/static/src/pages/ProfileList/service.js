import request from '@/utils/request';

export async function queryRule(params) {
  return request('/api/rule', {
    params,
  });
}
// 获取全部配置数据
export async function getProfile() {
  return request('/api/profile/getProfile');
}

export async function removeRule(params) {
  return request('/api/rule', {
    method: 'POST',
    data: { ...params, method: 'delete' },
  });
}
export async function addRule(params) {
  return request('/api/rule', {
    method: 'POST',
    data: { ...params, method: 'post' },
  });
}
export async function updateRule(params) {
  return request('/api/rule', {
    method: 'POST',
    data: { ...params, method: 'update' },
  });
}
