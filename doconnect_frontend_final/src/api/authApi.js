import axiosInstance from './axiosConfig'; 
 
export const authApi = { 
  login: (credentials) => axiosInstance.post('/auth/login', credentials).then(res => res.data), 
  register: (userData) => axiosInstance.post('/auth/register', userData).then(res => res.data), 
}; 
