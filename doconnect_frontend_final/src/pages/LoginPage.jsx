/** 

 * LoginPage 

 * Split screen login with validation 

 */ 

import { useState } from 'react'; 

import { useDispatch } from 'react-redux'; 

import { useNavigate, Link } 

                    from 'react-router-dom'; 

import { toast } from 'react-toastify'; 

import { loginSuccess } 

            from '../redux/slices/authSlice'; 

import authService from 

                '../services/authService'; 

import { validateLogin } from 

                        '../utils/validate'; 

import SplitLayout from 

                '../components/SplitLayout'; 

 

const LoginPage = () => { 

  const dispatch = useDispatch(); 

  const navigate = useNavigate(); 

 

  const [formData, setFormData] = useState({ 

    email: '', 

    password: '', 

  }); 

  const [errors, setErrors] = useState({}); 

  const [loading, setLoading] = useState(false); 

 

  const handleChange = (e) => { 

    setFormData({ 

      ...formData, 

      [e.target.name]: e.target.value, 

    }); 

    // Clear error on change 

    if (errors[e.target.name]) { 

      setErrors({ 

        ...errors, 

        [e.target.name]: '' 

      }); 

    } 

  }; 

 

  const handleSubmit = async (e) => { 

  e.preventDefault(); 

 

  const validationErrors = 

                  validateLogin(formData); 

  if (Object.keys( 

              validationErrors).length > 0) { 

    setErrors(validationErrors); 

    return; 

  } 

 

  setLoading(true); 

  try { 

    const response = await authService 

                            .login(formData); 

 

    // Response structure is 

    // { status, message, data: { token, role, username, email } } 

    const userData = response.data || 

                     response; 

 

    console.log('Login response:', userData); 

 

    // Dispatch with all fields 

    dispatch(loginSuccess({ 

      token: userData.token, 

      role: userData.role, 

      username: userData.username, 

      email: userData.email, 

    })); 

 

    toast.success('Welcome back! 🎉'); 

    navigate('/home'); 

 

  } catch (err) { 

    toast.error( 

      err.response?.data?.message || 

      err.response?.data || 

      'Login failed!'); 

  } finally { 

    setLoading(false); 

  } 

};

 

  return ( 

    <SplitLayout 

      subtitle="Welcome back to DoConnect AI"> 

 

      <form onSubmit={handleSubmit}> 

 

        {/* Email */} 

        <div className="mb-3"> 

          <label className="dc-label"> 

            Email Address 

          </label> 

          <input 

            type="email" 

            name="email" 

            className={`dc-input ${ 

              errors.email ? 'error' : ''}`} 

            placeholder="you@example.com" 

            value={formData.email} 

            onChange={handleChange} 

          /> 

          {errors.email && ( 

            <div className="error-text"> 

              {errors.email} 

            </div> 

          )} 

        </div> 

 

        {/* Password */} 

        <div className="mb-4"> 

          <label className="dc-label"> 

            Password 

          </label> 

          <input 

            type="password" 

            name="password" 

            className={`dc-input ${ 

              errors.password ? 'error' : ''}`} 

            placeholder="••••••••" 

            value={formData.password} 

            onChange={handleChange} 

          /> 

          {errors.password && ( 

            <div className="error-text"> 

              {errors.password} 

            </div> 

          )} 

        </div> 

 

        {/* Submit */} 

        <button 

          type="submit" 

          className="btn-primary-dc w-100" 

          disabled={loading} 

          style={{ 

            padding:'0.9rem', 

            fontSize:'1rem' 

          }} 

        > 

          {loading ? 

            '⏳ Signing in...' : 

            '🚀 Sign In'} 

        </button> 

 

        {/* Register link */} 

        <p className="text-center mt-4" 

           style={{color:'var(--gray)'}}> 

          No account?{' '} 

          <Link to="/register" 

                style={{color:'var(--primary)', 

                        fontWeight:600}}> 

            Create one free 

          </Link> 

        </p> 

      </form> 

    </SplitLayout> 

  ); 

}; 

 

export default LoginPage; 