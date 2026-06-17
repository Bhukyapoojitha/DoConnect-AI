/** 

 * Auth Service 

 * Register and login API calls 

 */ 

import axiosInstance from '../api/axiosConfig'; 
const authService = { 
  register: async (userData) => { 
    const response = await axiosInstance 

              .post('/auth/register', userData); 
    return response.data; 

  }, 
 login: async (credentials) => {
  const response = await axiosInstance.post('/auth/login', credentials);

  // ✅ Keep BOTH formats so LoginPage doesn't break
  return {
    ...response.data,          // keeps existing structure (status, message, etc.)
    token: response.data.data?.token,
    role: response.data.data?.role,
    username: response.data.data?.username,
    email: response.data.data?.email,
  };
},

}; 
export default authService; 