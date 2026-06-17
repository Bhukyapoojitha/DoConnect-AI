import { BrowserRouter, Routes, 

         Route, Navigate } 

                    from 'react-router-dom'; 

import { Provider } from 'react-redux'; 

import { ToastContainer } 

                from 'react-toastify'; 

import 'react-toastify/dist/ReactToastify.css'; 

import 'bootstrap/dist/css/bootstrap.min.css'; 

import './styles/theme.css'; 

 import Footer from "./components/Footer";

import { store } from './redux/store'; 

import Navbar from './components/Navbar'; 

import ProtectedRoute from 

                './components/ProtectedRoute'; 

import AdminRoute from 

                './components/AdminRoute'; 

import ModeratorRoute from 

                './components/ModeratorRoute'; 

 

// Pages 

import WelcomePage from './pages/WelcomePage'; 

import LoginPage from './pages/LoginPage'; 

import RegisterPage from 

                './pages/RegisterPage'; 

import HomePage from './pages/HomePage'; 

import AskQuestionPage from 

                './pages/AskQuestionPage'; 

import QuestionDetailPage from 

                './pages/QuestionDetailPage'; 

import UserProfilePage from 

                './pages/UserProfilePage'; 

import ChatListPage from 

                './pages/ChatListPage'; 

import ChatRoomPage from 

                './pages/ChatRoomPage'; 

import AdminDashboardPage from 

                './pages/AdminDashboardPage'; 

import ModeratorDashboardPage from 

            './pages/ModeratorDashboardPage'; 

 

function App() { 

  return ( 

    <Provider store={store}> 

      <BrowserRouter> 

        <Navbar /> 

        <Routes> 

 

          {/* Public */} 

          <Route path="/" 

                 element={<WelcomePage />} /> 

          <Route path="/login" 

                 element={<LoginPage />} /> 

          <Route path="/register" 

                 element={<RegisterPage />} /> 

 

          {/* Protected — any logged in user */} 

          <Route path="/home" element={ 

            <ProtectedRoute> 

              <HomePage /> 

            </ProtectedRoute> 

          } /> 

          <Route path="/ask" element={ 

            <ProtectedRoute> 

              <AskQuestionPage /> 

            </ProtectedRoute> 

          } /> 

          <Route path="/questions/:id" 

                 element={ 

            <ProtectedRoute> 

              <QuestionDetailPage /> 

            </ProtectedRoute> 

          } /> 

          <Route path="/profile" element={ 

            <ProtectedRoute> 

              <UserProfilePage /> 

            </ProtectedRoute> 

          } /> 

          <Route path="/chat" element={ 

            <ProtectedRoute> 

              <ChatListPage /> 

            </ProtectedRoute> 

          } /> 

          <Route path="/chat/:roomId" 

                 element={ 

            <ProtectedRoute> 

              <ChatRoomPage /> 

            </ProtectedRoute> 

          } /> 

 

          {/* Admin only */} 

          <Route path="/admin" element={ 

            <AdminRoute> 

              <AdminDashboardPage /> 

            </AdminRoute> 

          } /> 

 

          {/* Moderator only */} 

          <Route path="/moderator" element={ 

            <ModeratorRoute> 

              <ModeratorDashboardPage /> 

            </ModeratorRoute> 

          } /> 

 

          {/* Fallback */} 

          <Route path="*" 

                 element={ 

                   <Navigate to="/" replace /> 

                 } /> 

        </Routes> 

 

        <ToastContainer 

          position="top-right" 

          autoClose={3000} 

          theme="colored" 

        /> 

        <Footer />

      </BrowserRouter> 

    </Provider> 

  ); 

} 

 

export default App; 