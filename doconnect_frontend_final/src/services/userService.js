/** 

 * User Service 

 * Profile management API calls 

 */ 

import axiosInstance from '../api/axiosConfig'; 

 

const userService = { 

 

  getProfile: async () => { 

    const res = await axiosInstance 

                      .get('/users/profile'); 

    return res.data; 

  }, 

 

  updateProfile: async (data) => { 

    const res = await axiosInstance 

                .put('/users/profile', data); 

    return res.data; 

  }, 

 

  changePassword: async (data) => { 

    const res = await axiosInstance 

                .put('/users/password', data); 

    return res.data; 

  }, 

 

  getUserById: async (id) => { 

    const res = await axiosInstance 

                      .get(`/users/${id}`); 

    return res.data; 

  }, 

 

  getUserQuestions: async (id) => { 

    const res = await axiosInstance 

              .get(`/users/${id}/questions`); 

    return res.data; 

  }, 

 

  getUserAnswers: async (id) => { 

    const res = await axiosInstance 

                .get(`/users/${id}/answers`); 

    return res.data; 

  }, 

 

  deleteAccount: async () => { 

    const res = await axiosInstance 

                    .delete('/users/account'); 

    return res.data; 

  }, 

}; 

 

export default userService;