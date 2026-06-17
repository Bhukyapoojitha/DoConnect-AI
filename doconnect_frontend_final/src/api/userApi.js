# File Path: src/api/userApi.js 
import axiosInstance from './axiosConfig'; 
 
export const userApi = { 
  getProfile: () => axiosInstance.get('/users/profile').then(res => res.data), 
  getAllUsers: () => axiosInstance.get('/users/all').then(res => res.data), 
};