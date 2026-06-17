/** 

 * Answer Service 

 * All answer-related API calls 

 */ 

import axiosInstance from '../api/axiosConfig'; 

 

const answerService = { 

 

  getAnswersByQuestion: async (questionId) => { 

    const res = await axiosInstance 

                .get(`/answers/${questionId}`); 

    return res.data; 

  }, 

 

  postAnswer: async (data) => { 

    const res = await axiosInstance 

                    .post('/answers', data); 

    return res.data; 

  }, 

 

  updateAnswer: async (id, data) => { 

    const res = await axiosInstance 

                  .put(`/answers/${id}`, data); 

    return res.data; 

  }, 

 

  deleteAnswer: async (id) => { 

    const res = await axiosInstance 

                    .delete(`/answers/${id}`); 

    return res.data; 

  }, 

 

  acceptAnswer: async (id) => { 

    const res = await axiosInstance 

              .put(`/answers/${id}/accept`); 

    return res.data; 

  }, 

 

  voteAnswer: async (id, type) => { 

    const res = await axiosInstance 

        .put(`/answers/${id}/vote?type=${type}`); 

    return res.data; 

  }, 

}; 

 

export default answerService; 