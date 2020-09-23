import request from '@/utils/request';

export async function query() {
  return request('/api/users');
}
// 登录获取用户信息
export async function queryUser() {
  return request('/api/login/getloginuser');
}
export async function queryCurrent() {
  return request('/api/login/currentUser');
}
export async function queryNotices() {
  return request('/api/notices');
}
export async function checkUser() {

}
// 校验
//'POST /api/login/account': (req, res) => {
//     const { password, userName, type } = req.body;
//
//     if (password === 'ant.design' && userName === 'admin') {
//       res.send({
//         status: 'ok',
//         type,
//         currentAuthority: 'admin',
//       });
//       return;
//     }
//
//     if (password === 'ant.design' && userName === 'user') {
//       res.send({
//         status: 'ok',
//         type,
//         currentAuthority: 'user',
//       });
//       return;
//     }
//
//     if (type === 'mobile') {
//       res.send({
//         status: 'ok',
//         type,
//         currentAuthority: 'admin',
//       });
//       return;
//     }
//
//     res.send({
//       status: 'error',
//       type,
//       currentAuthority: 'guest',
//     });
//   },
