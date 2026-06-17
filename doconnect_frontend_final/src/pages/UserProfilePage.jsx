/** 

 * UserProfilePage 

 * View and edit user profile 

 * Change password, see questions/answers 

 */ 

import { useEffect, useState } from 'react'; 

import { useSelector } from 'react-redux'; 

import { Link } from 'react-router-dom'; 

import { toast } from 'react-toastify'; 

import userService from 

                '../services/userService'; 

import { validateProfile, 

         validatePassword } from 

                          '../utils/validate'; 

 

const UserProfilePage = () => { 

  const { user } = 

    useSelector((state) => state.auth); 

 

  const [profile, setProfile] = useState(null); 

  const [questions, setQuestions] = useState([]); 

  const [answers, setAnswers] = useState([]); 

  const [activeTab, setActiveTab] = 

                              useState('profile'); 

 

  const [editData, setEditData] = useState({ 

    username: '', 

    bio: '', 

  }); 

  const [pwData, setPwData] = useState({ 

    currentPassword: '', 

    newPassword: '', 

    confirmPassword: '', 

  }); 

  const [editErrors, setEditErrors] = 

                                    useState({}); 

  const [pwErrors, setPwErrors] = useState({}); 

 

  useEffect(() => { 

    loadProfile(); 

  }, []); 

 

  const loadProfile = async () => { 

    try { 

      const data = await userService 

                              .getProfile(); 

      setProfile(data.data); 

      setEditData({ 

        username: data.data.username, 

        bio: data.data.bio || '', 

      }); 

 

      const [qData, aData] = await Promise.all([ 

        userService.getUserQuestions( 

                                data.data.id), 

        userService.getUserAnswers( 

                                data.data.id), 

      ]); 

      setQuestions(qData.data || []); 

      setAnswers(aData.data || []); 

    } catch { 

      toast.error('Failed to load profile'); 

    } 

  }; 

 

  const handleUpdateProfile = async (e) => { 

    e.preventDefault(); 

    const errors = validateProfile(editData); 

    if (Object.keys(errors).length > 0) { 

      setEditErrors(errors); 

      return; 

    } 

    try { 

      await userService.updateProfile(editData); 

      toast.success('Profile updated! ✅'); 

      loadProfile(); 

    } catch (err) { 

      toast.error( 

        err.response?.data?.message || 

        'Update failed'); 

    } 

  }; 

 

  const handleChangePassword = async (e) => { 

    e.preventDefault(); 

    const errors = validatePassword(pwData); 

    if (Object.keys(errors).length > 0) { 

      setPwErrors(errors); 

      return; 

    } 

    try { 

      await userService.changePassword(pwData); 

      toast.success('Password changed! ✅'); 

      setPwData({ 

        currentPassword: '', 

        newPassword: '', 

        confirmPassword: '', 

      }); 

    } catch (err) { 

      toast.error( 

        err.response?.data?.message || 

        'Password change failed'); 

    } 

  }; 

 

  if (!profile) return ( 

    <div className="dc-container"> 

      <div className="dc-spinner"/> 

    </div> 

  ); 

 

  return ( 

    <div className="dc-container"> 

 

      {/* Profile Header */} 

      <div className="dc-card mb-4"> 

        <div className="d-flex 

                        align-items-center 

                        gap-4"> 

          <div style={{ 

            width:'80px', 

            height:'80px', 

            borderRadius:'50%', 

            background:'linear-gradient(135deg, var(--primary), var(--primary-dark))', 

            display:'flex', 

            alignItems:'center', 

            justifyContent:'center', 

            fontSize:'2rem', 

            color:'white', 

            fontWeight:700 

          }}> 

            {profile.username 

                    .charAt(0).toUpperCase()} 

          </div> 

          <div> 

            <h3 style={{fontWeight:700, 

                        margin:0}}> 

              {profile.username} 

            </h3> 

            <p style={{color:'var(--gray)', 

                       margin:0}}> 

              {profile.email} 

            </p> 

            <span className="dc-tag mt-1"> 

              {profile.role} 

            </span> 

          </div> 

        </div> 

 

        {/* Stats */} 

        <div className="row g-3 mt-3"> 

          {[ 

            { 

              label:'Questions', 

              value:profile.totalQuestions, 

              icon:'❓' 

            }, 

            { 

              label:'Answers', 

              value:profile.totalAnswers, 

              icon:'💡' 

            }, 

            { 

              label:'Member Since', 

              value: new Date(profile.createdAt) 

                      .toLocaleDateString(), 

              icon:'📅' 

            }, 

          ].map(stat => ( 

            <div key={stat.label} 

                 className="col-md-4"> 

              <div className="stat-card"> 

                <div style={{fontSize:'2rem'}}> 

                  {stat.icon} 

                </div> 

                <h3 style={{fontWeight:800}}> 

                  {stat.value} 

                </h3> 

                <small>{stat.label}</small> 

              </div> 

            </div> 

          ))} 

        </div> 

      </div> 

 

      {/* Tabs */} 

      <div className="d-flex gap-2 mb-4 

                      flex-wrap"> 

        {['profile', 'password', 

          'questions', 'answers'].map((tab) => ( 

          <button 

            key={tab} 

            onClick={() => setActiveTab(tab)} 

            style={{ 

              padding:'0.5rem 1.2rem', 

              borderRadius:'20px', 

              border:'none', 

              background: 

                activeTab === tab ? 

                  'var(--primary)' : 

                  'var(--gray-light)', 

              color: 

                activeTab === tab ? 

                  'white' : 'var(--gray)', 

              fontWeight:600, 

              cursor:'pointer', 

            }} 

          > 

            {tab === 'profile' && '👤 Profile'} 

            {tab === 'password' && '🔒 Password'} 

            {tab === 'questions' && 

              `❓ Questions (${questions.length})`} 

            {tab === 'answers' && 

              `💡 Answers (${answers.length})`} 

          </button> 

        ))} 

      </div> 

 

      {/* Edit Profile Tab */} 

      {activeTab === 'profile' && ( 

        <div className="dc-card"> 

          <h5 style={{fontWeight:700, 

                      marginBottom:'1.5rem'}}> 

            Edit Profile 

          </h5> 

          <form onSubmit={handleUpdateProfile}> 

            <div className="mb-3"> 

              <label className="dc-label"> 

                Username 

              </label> 

              <input 

                className={`dc-input ${ 

                  editErrors.username ? 

                    'error' : ''}`} 

                value={editData.username} 

                onChange={(e) => 

                  setEditData({ 

                    ...editData, 

                    username: e.target.value 

                  })} 

              /> 

              {editErrors.username && ( 

                <div className="error-text"> 

                  {editErrors.username} 

                </div> 

              )} 

            </div> 

            <div className="mb-4"> 

              <label className="dc-label"> 

                Bio 

              </label> 

              <textarea 

                className={`dc-input ${ 

                  editErrors.bio ? 'error' : ''}`} 

                rows={3} 

                placeholder="Tell us about yourself..." 

                value={editData.bio} 

                onChange={(e) => 

                  setEditData({ 

                    ...editData, 

                    bio: e.target.value 

                  })} 

              /> 

              {editErrors.bio && ( 

                <div className="error-text"> 

                  {editErrors.bio} 

                </div> 

              )} 

            </div> 

            <button 

              type="submit" 

              className="btn-primary-dc"> 

              Save Changes ✅ 

            </button> 

          </form> 

        </div> 

      )} 

 

      {/* Change Password Tab */} 

      {activeTab === 'password' && ( 

        <div className="dc-card"> 

          <h5 style={{fontWeight:700, 

                      marginBottom:'1.5rem'}}> 

            Change Password 

          </h5> 

          <form onSubmit={handleChangePassword}> 

            {['currentPassword', 

              'newPassword', 

              'confirmPassword'].map((field) => ( 

              <div key={field} className="mb-3"> 

                <label className="dc-label"> 

                  {field === 'currentPassword' ? 

                    'Current Password' : 

                   field === 'newPassword' ? 

                    'New Password' : 

                    'Confirm Password'} 

                </label> 

                <input 

                  type="password" 

                  className={`dc-input ${ 

                    pwErrors[field] ? 

                      'error' : ''}`} 

                  value={pwData[field]} 

                  onChange={(e) => 

                    setPwData({ 

                      ...pwData, 

                      [field]: e.target.value 

                    })} 

                /> 

                {pwErrors[field] && ( 

                  <div className="error-text"> 

                    {pwErrors[field]} 

                  </div> 

                )} 

              </div> 

            ))} 

            <button 

              type="submit" 

              className="btn-primary-dc"> 

              Change Password 🔒 

            </button> 

          </form> 

        </div> 

      )} 

 

      {/* Questions Tab */} 

      {activeTab === 'questions' && ( 

        <div> 

          {questions.length === 0 ? ( 

            <div className="dc-card text-center"> 

              <p>No questions yet.</p> 

              <Link to="/ask" 

                    className="btn-primary-dc"> 

                Ask First Question 

              </Link> 

            </div> 

          ) : ( 

            questions.map((q) => ( 

              <Link key={q.id} 

                    to={`/questions/${q.id}`} 

                    className="text-decoration-none"> 

                <div className="question-card"> 

                  <h6 style={{ 

                    color:'var(--primary)', 

                    fontWeight:700 

                  }}> 

                    {q.title} 

                  </h6> 

                  <small 

                    style={{color:'var(--gray)'}}> 

                    {q.views} views · 

                    {q.votes} votes 

                  </small> 

                </div> 

              </Link> 

            )) 

          )} 

        </div> 

      )} 

 

      {/* Answers Tab */} 

      {activeTab === 'answers' && ( 

        <div> 

          {answers.length === 0 ? ( 

            <div className="dc-card text-center"> 

              <p>No answers yet.</p> 

            </div> 

          ) : ( 

            answers.map((a) => ( 

              <div key={a.id} 

                   className="dc-card mb-3"> 

                {a.isAccepted && ( 

                  <span className="badge 

                                   bg-success 

                                   mb-2"> 

                    ✅ Accepted 

                  </span> 

                )} 

                {a.isAiGenerated && ( 

                  <span className="dc-tag 

                                   dc-tag-ai 

                                   mb-2"> 

                    🤖 AI 

                  </span> 

                )} 

                <p style={{ 

                  fontSize:'0.95rem', 

                  marginBottom:'0.5rem' 

                }}> 

                  {a.content.substring(0, 150)}... 

                </p> 

                <small 

                  style={{color:'var(--gray)'}}> 

                  {a.votes} votes 

                </small> 

              </div> 

            )) 

          )} 

        </div> 

      )} 

    </div> 

  ); 

}; 

 

export default UserProfilePage; 