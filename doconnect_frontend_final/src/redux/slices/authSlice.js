import { createSlice } from '@reduxjs/toolkit'; 

 

// Load from localStorage on startup 

const token = localStorage.getItem('token'); 

const savedRole = localStorage.getItem('role'); 

const savedUsername = 

        localStorage.getItem('username'); 

const savedEmail = 

        localStorage.getItem('email'); 

 

const authSlice = createSlice({ 

  name: 'auth', 

  initialState: { 

    token: token || null, 

    role: savedRole || null, 

    username: savedUsername || null, 

    email: savedEmail || null, 

    isAuthenticated: !!token, 

  }, 

  reducers: { 

    loginSuccess: (state, action) => { 

      const { 

        token, 

        role, 

        username, 

        email 

      } = action.payload; 

 

      state.token = token; 

      state.role = role; 

      state.username = username; 

      state.email = email; 

      state.isAuthenticated = true; 

 

      // Save all to localStorage 

      localStorage.setItem('token', token); 

      localStorage.setItem('role', role); 

      localStorage.setItem('username', 

                            username); 

      localStorage.setItem('email', email); 

 

      console.log('✅ Login success, role:', 

                    role); 

    }, 

 

    logout: (state) => { 

      state.token = null; 

      state.role = null; 

      state.username = null; 

      state.email = null; 

      state.isAuthenticated = false; 

 

      localStorage.removeItem('token'); 

      localStorage.removeItem('role'); 

      localStorage.removeItem('username'); 

      localStorage.removeItem('email'); 

    }, 

  }, 

}); 

 

export const { loginSuccess, logout } = 

                          authSlice.actions; 

export default authSlice.reducer; 