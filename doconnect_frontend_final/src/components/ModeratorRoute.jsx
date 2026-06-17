/** 

 * ModeratorRoute.jsx 

 * Allows MODERATOR and ADMIN access 

 */ 

import { useSelector } from 'react-redux'; 

import { Navigate } from 'react-router-dom'; 

 

const ModeratorRoute = ({ children }) => { 

  const { isAuthenticated, role } = 

    useSelector((state) => state.auth); 

 

  if (!isAuthenticated) 

    return <Navigate to="/login" replace />; 

 

  if (role !== 'MODERATOR' && 

      role !== 'ADMIN') 

    return <Navigate to="/home" replace />; 

 

  return children; 

}; 

 

export default ModeratorRoute; 