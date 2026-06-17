import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';

const ProtectedRoute = ({ children, allowedRoles }) => {
  const location = useLocation();

  const {
    token,
    role
  } = useSelector((state) => state.auth);

  if (!token) {
    return (
      <Navigate
        to="/login"
        state={{ from: location }}
        replace
      />
    );
  }

  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/home" replace />;
  }

  return children;
};

export default ProtectedRoute;
