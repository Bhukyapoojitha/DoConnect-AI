/** 

 * Question Service 

 * All question-related API calls 

 */ 

import axiosInstance from '../api/axiosConfig'; 

 

const questionService = { 

 

  getAllQuestions: async () => { 

    const res = await axiosInstance 

                          .get('/questions'); 

    return res.data; 

  }, 

 

  getQuestionById: async (id) => { 

    const res = await axiosInstance 

                      .get(`/questions/${id}`); 

    return res.data; 

  }, 

 

  createQuestion: async (data) => { 

    const res = await axiosInstance 

                  .post('/questions', data); 

    return res.data; 

  }, 

 

  updateQuestion: async (id, data) => { 

    const res = await axiosInstance 

                .put(`/questions/${id}`, data); 

    return res.data; 

  }, 

 

  deleteQuestion: async (id) => { 

    const res = await axiosInstance 

                  .delete(`/questions/${id}`); 

    return res.data; 

  }, 

 

  searchQuestions: async (keyword) => { 

    const res = await axiosInstance 

        .get(`/questions/search?keyword=${keyword}`); 

    return res.data; 

  }, 

 

  getTrending: async () => { 

    const res = await axiosInstance 

                .get('/questions/trending'); 

    return res.data; 

  }, 

 

  getByTag: async (tagName) => { 

    const res = await axiosInstance 

            .get(`/questions/tag/${tagName}`); 

    return res.data; 

  }, 

 

  voteQuestion: async (id, type) => { 

    const res = await axiosInstance 

        .put(`/questions/${id}/vote?type=${type}`); 

    return res.data; 

  }, 

}; 

 

export default questionService; 