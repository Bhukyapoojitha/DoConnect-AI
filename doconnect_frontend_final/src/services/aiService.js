/** 

 * AI Service 

 * Gemini AI feature API calls 

 */ 

import axiosInstance from '../api/axiosConfig'; 

 

const aiService = { 

 

  generateAnswer: async (questionId) => { 

    const res = await axiosInstance 

              .post(`/ai/answer/${questionId}`); 

    return res.data; 

  }, 

 

  suggestTags: async (title, content) => { 

    const res = await axiosInstance 

              .post('/ai/tags', { 

                questionTitle: title, 

                questionContent: content, 

              }); 

    return res.data; 

  }, 

 

  moderateContent: async (content) => { 

    const res = await axiosInstance 

              .post('/ai/moderate', { content }); 

    return res.data; 

  }, 

 

  analyzeSentiment: async (content) => { 

    const res = await axiosInstance 

            .post('/ai/sentiment', { content }); 

    return res.data; 

  }, 

}; 

 

export default aiService; 