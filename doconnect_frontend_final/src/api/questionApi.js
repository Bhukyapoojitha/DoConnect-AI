import axiosInstance from './axiosConfig'; 
 
export const questionApi = { 
  listAllQuestions: () => axiosInstance.get('/questions/list').then(res => res.data), 
  fetchById: (id) => axiosInstance.get(`/questions/${id}`).then(res => res.data), 
  createRecord: (payload) => axiosInstance.post('/questions/create', payload).then(res => res.data), 
}; 
