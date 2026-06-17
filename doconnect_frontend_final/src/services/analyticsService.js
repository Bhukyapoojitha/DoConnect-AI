/** 

 * Analytics Service 

 * Dashboard and moderation API calls 

 */ 

import axiosInstance from '../api/axiosConfig'; 

 

const analyticsService = { 

 

  getDashboard: async () => { 

    const res = await axiosInstance 

                .get('/analytics/dashboard'); 

    return res.data; 

  }, 

 

  getPendingContent: async () => { 

    const res = await axiosInstance 

                    .get('/moderation/pending'); 

    return res.data; 

  }, 

 

  approveContent: async (id) => { 

    const res = await axiosInstance 

              .put(`/moderation/${id}/approve`); 

    return res.data; 

  }, 

 

  rejectContent: async (id) => { 

    const res = await axiosInstance 

              .put(`/moderation/${id}/reject`); 

    return res.data; 

  }, 

 

  getAllUsers: async () => { 

    const res = await axiosInstance 

                        .get('/admin/users'); 

    return res.data; 

  }, 

 

  updateUserRole: async (id, role) => { 

    const res = await axiosInstance 

        .put(`/admin/users/${id}/role?role=${role}`); 

    return res.data; 

  }, 

 

  deleteUser: async (id) => { 

    const res = await axiosInstance 

                  .delete(`/admin/users/${id}`); 

    return res.data; 

  }, 

}; 

 

export default analyticsService;