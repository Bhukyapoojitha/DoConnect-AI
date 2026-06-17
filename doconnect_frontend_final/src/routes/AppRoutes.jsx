import React from 'react'; 
import { Routes, Route, Navigate } from 'react-router-dom'; 
import Welcome from '../pages/auth/Welcome'; 
import Login from '../pages/auth/Login'; 
import Register from '../pages/auth/Register'; 
import Dashboard from '../pages/dashboard/Dashboard'; 
import AskQuestion from '../pages/questions/AskQuestion'; 
import QuestionDetails from '../pages/questions/QuestionDetails'; 
import UserProfile from '../pages/profile/UserProfile'; 
import ChatRoomPage from '../pages/chat/ChatRoomPage'; 
import ProtectedRoute from '../components/common/ProtectedRoute'; 
import MainLayout from '../layouts/MainLayout'; 
import AdminDashboardPage from '../pages/admin/AdminDashboardPage';
import AdminRoute from '../components/AdminRoute';
export default function AppRoutes() { 
  return ( 
    <Routes> 
      
      <Route path="/" element={<Welcome />} /> 
      <Route path="/login" element={<Login />} /> 
      <Route path="/register" element={<Register />} /> 



         <Route path="/admin" element={
  <AdminRoute>
    <MainLayout><AdminDashboardPage /></MainLayout>
  </AdminRoute>
} />
 


      <Route path="/dashboard" element={ 
        <ProtectedRoute> 
          <MainLayout><Dashboard /></MainLayout> 
        </ProtectedRoute> 
      } /> 
 
      <Route path="/questions/ask" element={ 
        <ProtectedRoute> 
          <MainLayout><AskQuestion /></MainLayout> 
        </ProtectedRoute> 
      } /> 
 
      <Route path="/questions/:id" element={ 
        <ProtectedRoute> 
          <MainLayout><QuestionDetails /></MainLayout> 
        </ProtectedRoute> 
      } /> 
 
      <Route path="/profile" element={ 
        <ProtectedRoute> 
          <MainLayout><UserProfile /></MainLayout> 
        </ProtectedRoute> 
      } /> 
 
      <Route path="/chat" element={ 
        <ProtectedRoute> 
          <MainLayout><ChatRoomPage /></MainLayout> 
        </ProtectedRoute> 
      } /> 

   
 
      <Route path="*" element={<Navigate to="/dashboard" replace />} /> 
    </Routes> 
  ); 
} 
 
