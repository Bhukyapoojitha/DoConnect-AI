import { useDispatch, useSelector } 

                            from 'react-redux'; 

import { Link, useNavigate } 

                        from 'react-router-dom'; 

import { logout } 

            from '../redux/slices/authSlice'; 

 

const Navbar = () => { 

  const dispatch = useDispatch(); 

  const navigate = useNavigate(); 

 

  const { 

    isAuthenticated, 

    role, 

    username 

  } = useSelector((state) => state.auth); 

 

  // Role checks 

  const isAdmin = role === 'ADMIN'; 

  const isModerator = role === 'MODERATOR'; 

 

  const handleLogout = () => { 

    dispatch(logout()); 

    navigate('/login'); 

  }; 

 

  return ( 

    <nav className="dc-navbar navbar 

                    navbar-expand-lg"> 

      <div className="container"> 

 

        <Link to="/" className="dc-brand"> 

          🔗 Do<span>Connect</span> AI 

        </Link> 

 

        <button 

          className="navbar-toggler" 

          type="button" 

          data-bs-toggle="collapse" 

          data-bs-target="#navMenu" 

        > 

          <span style={{color:'white'}}> 

            ☰ 

          </span> 

        </button> 

 

        <div className="collapse navbar-collapse" 

             id="navMenu"> 

 

          {isAuthenticated ? ( 

            <div className="ms-auto d-flex 

                            align-items-center 

                            gap-3 flex-wrap"> 

 

              <Link to="/home" 

                    className="text-white 

                               text-decoration-none"> 

                🏠 Home 

              </Link> 

 

              <Link to="/ask" 

                    className="text-white 

                               text-decoration-none"> 

                ❓ Ask 

              </Link> 

 

              <Link to="/chat" 

                    className="text-white 

                               text-decoration-none"> 

                💬 Chat 

              </Link> 

 

              <Link to="/profile" 

                    className="text-white 

                               text-decoration-none"> 

                👤 {username || 'Profile'} 

              </Link> 

 

              {/* Show role badge */} 

              {isAdmin && ( 

                <span style={{ 

                  background:'#FFD93D', 

                  color:'#1A1A2E', 

                  padding:'0.2rem 0.6rem', 

                  borderRadius:'20px', 

                  fontSize:'0.75rem', 

                  fontWeight:700 

                }}> 

                  👑 ADMIN 

                </span> 

              )} 

 

              {isModerator && ( 

                <span style={{ 

                  background:'#4CC9F0', 

                  color:'#1A1A2E', 

                  padding:'0.2rem 0.6rem', 

                  borderRadius:'20px', 

                  fontSize:'0.75rem', 

                  fontWeight:700 

                }}> 

                  🛡️ MOD 

                </span> 

              )} 

 

              {/* Admin Dashboard Link */} 

              {isAdmin && ( 

                <Link to="/admin" 

                      style={{ 

                        color:'#FFD93D', 

                        textDecoration:'none', 

                        fontWeight:600 

                      }}> 

                  📊 Admin Panel 

                </Link> 

              )} 

 

              {/* Moderator Dashboard Link */} 

              {isModerator && ( 

                <Link to="/moderator" 

                      style={{ 

                        color:'#4CC9F0', 

                        textDecoration:'none', 

                        fontWeight:600 

                      }}> 

                  🛡️ Mod Panel 

                </Link> 

              )} 

 

              <button 

                className="btn btn-sm" 

                style={{ 

                  background:'transparent', 

                  border:'1px solid white', 

                  color:'white', 

                  borderRadius:'8px', 

                  padding:'0.3rem 0.8rem' 

                }} 

                onClick={handleLogout} 

              > 

                Logout 

              </button> 

            </div> 

          ) : ( 

            <div className="ms-auto d-flex 

                            gap-3"> 

              <Link to="/login" 

                    className="btn-outline-dc" 

                    style={{color:'white', 

                            borderColor:'white'}}> 

                Login 

              </Link> 

              <Link to="/register" 

                    className="btn-primary-dc"> 

                Register 

              </Link> 

            </div> 

          )} 

        </div> 

      </div> 

    </nav> 

  ); 

}; 

 

export default Navbar; 