import { getProfile } from '@/services/profile';
//function getRule(req, res, u) {
//   let realUrl = u;
//
//   if (!realUrl || Object.prototype.toString.call(realUrl) !== '[object String]') {
//     realUrl = req.url;
//   }
//
//   const { current = 1, pageSize = 10 } = req.query;
//   const params = parse(realUrl, true).query;
//   let dataSource = [...tableListDataSource].slice((current - 1) * pageSize, current * pageSize);
//   const sorter = JSON.parse(params.sorter);
//
//   if (sorter) {
//     dataSource = dataSource.sort((prev, next) => {
//       let sortNumber = 0;
//       Object.keys(sorter).forEach((key) => {
//         if (sorter[key] === 'descend') {
//           if (prev[key] - next[key] > 0) {
//             sortNumber += -1;
//           } else {
//             sortNumber += 1;
//           }
//
//           return;
//         }
//
//         if (prev[key] - next[key] > 0) {
//           sortNumber += 1;
//         } else {
//           sortNumber += -1;
//         }
//       });
//       return sortNumber;
//     });
//   }
//
//   if (params.filter) {
//     const filter = JSON.parse(params.filter);
//
//     if (Object.keys(filter).length > 0) {
//       dataSource = dataSource.filter((item) => {
//         return Object.keys(filter).some((key) => {
//           if (!filter[key]) {
//             return true;
//           }
//
//           if (filter[key].includes(`${item[key]}`)) {
//             return true;
//           }
//
//           return false;
//         });
//       });
//     }
//   }
//
//   if (params.name) {
//     dataSource = dataSource.filter((data) => data.name.includes(params.name || ''));
//   }
//
//   const result = {
//     data: dataSource,
//     total: tableListDataSource.length,
//     success: true,
//     pageSize,
//     current: parseInt(`${params.currentPage}`, 10) || 1,
//   };
//   return res.json(result);
// }
const Model = {

  namespace: 'profile',

  state: {
    status: undefined,
    profiles: [],

  },

  effects: {
    *getPage(_, { call, put }) {
      const response = yield call(getProfile);
      yield put({
        type: 'changeProfileList',
        payload: response,
      });
    },
  },
  reducers: {
    changeProfileList(state, { payload }) {
      return {
        ...state,
        profiles: payload,
      };
    }
  },

};

export default Model;
