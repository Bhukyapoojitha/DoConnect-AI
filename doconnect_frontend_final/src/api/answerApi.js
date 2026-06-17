import axiosInstance from './axiosConfig'; 
 
export const answerApi = { 
  getAnswersForQuestion: (qId) => axiosInstance.get(`/answers/thread/${qId}`).then(res => res.data), 
  postAnswerRecord: (qId, content) => axiosInstance.post(`/answers/post/${qId}`, { content }).then(res => res.data), 
};