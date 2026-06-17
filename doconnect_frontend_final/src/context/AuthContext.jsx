import React, { createContext, useContext, useState, useEffect } from 'react'; 
import { tokenUtils } from '../utils/tokenUtils'; 
 
const AuthContext = createContext(null); 
 
export const AuthProvider = ({ children }) => { 
  const [user, setUser] = useState(null); 
  const [token, setToken] = useState(localStorage.getItem('token') || null); 
  const [loading, setLoading] = useState(true); 
 
  useEffect(() => { 
    if (token) { 
      localStorage.setItem('token', token); 
      const profile = tokenUtils.decodeTokenClaims(token); 
      setUser(profile); 
    } else { 
      localStorage.removeItem('token'); 
      setUser(null); 
    } 
    setLoading(false); 
  }, [token]); 
 
  const loginSession = (accessToken) => setToken(accessToken); 
  const logoutSession = () => setToken(null); 
 
  return ( 
    <AuthContext.Provider value={{ user, token, loading, loginSession, logoutSession }}> 
      {children} 
    </AuthContext.Provider> 
  ); 
}; 
 
export const useAuth = () => useContext(AuthContext);