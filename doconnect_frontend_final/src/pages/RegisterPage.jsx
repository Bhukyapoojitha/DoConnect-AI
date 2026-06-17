/** 

 * RegisterPage 

 * Split screen registration with validation 

 */ 

import { useState } from 'react'; 

import { useNavigate, Link } 

                    from 'react-router-dom'; 

import { toast } from 'react-toastify'; 

import authService from 

                '../services/authService'; 

import { validateRegister } from 

                          '../utils/validate'; 

import SplitLayout from 

                '../components/SplitLayout'; 

 

const RegisterPage = () => { 

  const navigate = useNavigate(); 

 

  const [formData, setFormData] = useState({ 

    username: '', 

    email: '', 

    password: '', 

    role: 'USER', // Default role 

  }); 

  const [errors, setErrors] = useState({}); 

  const [loading, setLoading] = useState(false); 

 

  const handleChange = (e) => { 

    setFormData({ 

      ...formData, 

      [e.target.name]: e.target.value, 

    }); 

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

                  validateRegister(formData); 

    if (Object.keys( 

                validationErrors).length > 0) { 

      setErrors(validationErrors); 

      return; 

    } 

 

    setLoading(true); 

    try { 

      await authService.register(formData); 

      toast.success( 

        'Account created! Please login 🎉'); 

      navigate('/login'); 

    } catch (err) { 

      toast.error( 

        err.response?.data?.message || 

        'Registration failed!'); 

    } finally { 

      setLoading(false); 

    } 

  }; 

 

  return ( 

    <SplitLayout 

      subtitle="Join thousands of developers"> 

 

      <form onSubmit={handleSubmit}> 

 

        {/* Username */} 

        <div className="mb-3"> 

          <label className="dc-label"> 

            Username 

          </label> 

          <input 

            type="text" 

            name="username" 

            className={`dc-input ${ 

              errors.username ? 'error' : ''}`} 

            placeholder="cooldev123" 

            value={formData.username} 

            onChange={handleChange} 

          /> 

          {errors.username && ( 

            <div className="error-text"> 

              {errors.username} 

            </div> 

          )} 

        </div> 

 

        {/* Email */} 

        <div className="mb-3"> 

          <label className="dc-label"> 

            Email 

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

        <div className="mb-3"> 

          <label className="dc-label"> 

            Password 

          </label> 

          <input 

            type="password" 

            name="password" 

            className={`dc-input ${ 

              errors.password ? 'error' : ''}`} 

            placeholder="Min 6 chars" 

            value={formData.password} 

            onChange={handleChange} 

          /> 

          {errors.password && ( 

            <div className="error-text"> 

              {errors.password} 

            </div> 

          )} 

        </div> 

 

        {/* Role Selection */} 

        <div className="mb-4"> 

          <label className="dc-label"> 

            Register As 

          </label> 

          <select 

            name="role" 

            className="dc-input" 

            value={formData.role} 

            onChange={handleChange} 

          > 

            <option value="USER"> 

              👤 Regular User 

            </option> 

            <option value="MODERATOR"> 

              🛡️ Moderator 

            </option> 

            <option value="ADMIN"> 

              👑 Admin 

            </option> 

          </select> 

        </div> 

 

        <button 

          type="submit" 

          className="btn-primary-dc w-100" 

          disabled={loading} 

          style={{padding:'0.9rem', 

                  fontSize:'1rem'}} 

        > 

          {loading ? 

            '⏳ Creating...' : 

            '✨ Create Account'} 

        </button> 

 

        <p className="text-center mt-4" 

           style={{color:'var(--gray)'}}> 

          Already have account?{' '} 

          <Link to="/login" 

                style={{color:'var(--primary)', 

                        fontWeight:600}}> 

            Sign in 

          </Link> 

        </p> 

      </form> 

    </SplitLayout> 

  ); 

}; 
export default RegisterPage; 