/** 

 * AdminDashboardPage 

 * Analytics, charts, and moderation panel 

 * Admin only access 

 */ 

import { useEffect, useState } from 'react'; 

import { 

  BarChart, Bar, XAxis, YAxis, 

  CartesianGrid, Tooltip, Legend, 

  PieChart, Pie, Cell, 

  ResponsiveContainer, 

} from 'recharts'; 

import { toast } from 'react-toastify'; 

import analyticsService from 

                '../services/analyticsService'; 

 

const COLORS = [ 

  '#6C63FF','#FF6584', 

  '#43E97B','#FFD93D','#4CC9F0' 

]; 

 

const AdminDashboardPage = () => { 

  const [stats, setStats] = useState(null); 

  const [flagged, setFlagged] = useState([]); 

  const [users, setUsers] = useState([]); 

  const [loading, setLoading] = useState(true); 

  const [activeTab, setActiveTab] = 

                              useState('overview'); 

 

  useEffect(() => { 

    loadData(); 

  }, []); 

 

  const loadData = async () => { 

    try { 

      const [dashData, flagData, userData] = 

        await Promise.all([ 

          analyticsService.getDashboard(), 

          analyticsService.getPendingContent(), 

          analyticsService.getAllUsers(), 

        ]); 

      setStats(dashData.data); 

      setFlagged(flagData.data || []); 

      setUsers(userData.data || []); 

    } catch { 

      toast.error('Failed to load dashboard'); 

    } finally { 

      setLoading(false); 

    } 

  }; 

 

  const handleApprove = async (id) => { 

    try { 

      await analyticsService.approveContent(id); 

      setFlagged( 

        flagged.filter((f) => f.id !== id)); 

      toast.success('✅ Content approved'); 

    } catch { 

      toast.error('Failed to approve'); 

    } 

  }; 

 

  const handleReject = async (id) => { 

    try { 

      await analyticsService.rejectContent(id); 

      setFlagged( 

        flagged.filter((f) => f.id !== id)); 

      toast.success('🗑️ Content rejected'); 

    } catch { 

      toast.error('Failed to reject'); 

    } 

  }; 

 

  const handleDeleteUser = async (id) => { 

    try { 

      await analyticsService.deleteUser(id); 

      setUsers( 

        users.filter((u) => u.id !== id)); 

      toast.success('User deleted'); 

    } catch { 

      toast.error('Failed to delete user'); 

    } 

  }; 

 

  const handleRoleChange = async (id, role) => { 

    try { 

      await analyticsService 

                    .updateUserRole(id, role); 

      toast.success('Role updated!'); 

      loadData(); 

    } catch { 

      toast.error('Failed to update role'); 

    } 

  }; 

 

  if (loading) return ( 

    <div className="dc-container"> 

      <div className="dc-spinner"/> 

    </div> 

  ); 

 

  const tabs = [ 

    { id:'overview', label:'📈 Overview' }, 

    { id:'trending', label:'🔥 Trending' }, 

    { id:'users',    label:'👥 Users' }, 

    { 

      id:'moderation', 

      label:`⚠️ Moderation ${ 

        flagged.length > 0 ? 

          `(${flagged.length})` : ''}` 

    }, 

  ]; 

 

  return ( 

    <div className="dc-container"> 

 

      <h2 style={{ 

        fontWeight:800, 

        color:'var(--dark)', 

        marginBottom:'1.5rem' 

      }}> 

        📊 Admin Dashboard 

      </h2> 

 

      {/* Tabs */} 

      <div className="d-flex gap-2 mb-4 

                      flex-wrap"> 

        {tabs.map((tab) => ( 

          <button 

            key={tab.id} 

            onClick={() => 

                        setActiveTab(tab.id)} 

            style={{ 

              padding:'0.5rem 1.2rem', 

              borderRadius:'20px', 

              border:'none', 

              background: 

                activeTab === tab.id ? 

                  'var(--primary)' : 

                  'var(--gray-light)', 

              color: 

                activeTab === tab.id ? 

                  'white' : 'var(--gray)', 

              fontWeight:600, 

              cursor:'pointer', 

            }} 

          > 

            {tab.label} 

          </button> 

        ))} 

      </div> 

 

      {/* OVERVIEW TAB */} 

      {activeTab === 'overview' && ( 

        <> 

          {/* Stats Cards */} 

          <div className="row g-3 mb-4"> 

            {[ 

              { 

                label:'Total Users', 

                value:stats?.totalUsers, 

                icon:'👥', 

                cls:'stat-card' 

              }, 

              { 

                label:'Questions', 

                value:stats?.totalQuestions, 

                icon:'❓', 

                cls:'stat-card success' 

              }, 

              { 

                label:'Answers', 

                value:stats?.totalAnswers, 

                icon:'💡', 

                cls:'stat-card info' 

              }, 

              { 

                label:'Chat Messages', 

                value:stats?.totalMessages, 

                icon:'💬', 

                cls:'stat-card warning' 

              }, 

              { 

                label:'Flagged Content', 

                value:stats?.flaggedContentCount, 

                icon:'⚠️', 

                cls:'stat-card danger' 

              }, 

              { 

                label:'Avg Sentiment', 

                value:stats?.averageSentiment, 

                icon:'😊', 

                cls:'stat-card' 

              }, 

            ].map((card) => ( 

              <div key={card.label} 

                   className="col-md-2"> 

                <div className={card.cls}> 

                  <div style={{ 

                    fontSize:'2rem', 

                    marginBottom:'0.5rem' 

                  }}> 

                    {card.icon} 

                  </div> 

                  <h3 style={{fontWeight:800}}> 

                    {card.value ?? 0} 

                  </h3> 

                  <small>{card.label}</small> 

                </div> 

              </div> 

            ))} 

          </div> 

 

          {/* Sentiment Bar */} 

<div className="dc-card">
  <div className="d-flex justify-content-between align-items-center mb-3">
    <h6 style={{ fontWeight: 700, margin: 0 }}>
      😊 Platform Sentiment
    </h6>

    {/* Status badge */}
    <span
      style={{
        padding: '4px 10px',
        borderRadius: '20px',
        fontSize: '0.8rem',
        fontWeight: 600,
        background:
          (stats?.averageSentiment || 0) > 0.3
            ? 'rgba(67,233,123,0.15)'
            : (stats?.averageSentiment || 0) < -0.3
            ? 'rgba(255,101,132,0.15)'
            : 'rgba(255,217,61,0.2)',
        color:
          (stats?.averageSentiment || 0) > 0.3
            ? 'var(--success)'
            : (stats?.averageSentiment || 0) < -0.3
            ? 'var(--danger)'
            : '#b08900',
      }}
    >
      {(stats?.averageSentiment || 0) > 0.3
        ? '😊 Positive'
        : (stats?.averageSentiment || 0) < -0.3
        ? '😞 Negative'
        : '😐 Neutral'}
    </span>
  </div>

  {/* PROGRESS BAR */}
  <div
    style={{
      position: 'relative',
      height: '14px',
      borderRadius: '10px',
      background: 'linear-gradient(to right, #FF6584, #FFD93D, #43E97B)',
      overflow: 'hidden',
    }}
  >
    {/* pointer */}
    <div
      style={{
        position: 'absolute',
        left: `calc(${((stats?.averageSentiment || 0) + 1) * 50}% - 6px)`,
        top: '-4px',
        width: '12px',
        height: '22px',
        background: '#111',
        borderRadius: '4px',
        transition: 'left 0.5s ease',
      }}
    />
  </div>

  {/* labels */}
  <div
    className="d-flex justify-content-between mt-2"
    style={{ fontSize: '0.75rem', color: 'var(--gray)' }}
  >
    <span>😞 Negative</span>
    <span>😐 Neutral</span>
    <span>😊 Positive</span>
  </div>
</div>

        </> 

      )} 

 

      {/* TRENDING TAB */} 

      {activeTab === 'trending' && ( 

        <div className="dc-card"> 

          <h5 style={{fontWeight:700, 

                      marginBottom:'1.5rem'}}> 

            🔥 Top Questions by Views 

          </h5> 

          <ResponsiveContainer 

            width="100%" height={350}> 

            <BarChart 

              data={stats?.trendingTopics}> 

              <CartesianGrid 

                strokeDasharray="3 3"/> 

              <XAxis 

                dataKey="title" 

                tick={{fontSize:10}} 

                tickFormatter={(v) => 

                  v.substring(0,15) + '...'} 

              /> 

              <YAxis /> 

              <Tooltip /> 

              <Legend /> 

              <Bar dataKey="views" 

                   fill="#6C63FF" 

                   name="Views" 

                   radius={[4,4,0,0]}/> 

              <Bar dataKey="votes" 

                   fill="#FF6584" 

                   name="Votes" 

                   radius={[4,4,0,0]}/> 

            </BarChart> 

          </ResponsiveContainer> 

        </div> 

      )} 

 

      {/* USERS TAB */} 

      {activeTab === 'users' && ( 

        <div className="row g-4"> 

          <div className="col-md-5"> 

            <div className="dc-card"> 

              <h5 style={{fontWeight:700, 

                          marginBottom:'1rem'}}> 

                👥 Active Users 

              </h5> 

              <ResponsiveContainer 

                width="100%" height={280}> 

                <PieChart> 

                  <Pie 

                    data={stats?.activeUsers} 

                    dataKey="questions" 

                    nameKey="username" 

                    cx="50%" 

                    cy="50%" 

                    outerRadius={100} 

                    label={(e) => e.username} 

                  > 

                    {stats?.activeUsers?.map( 

                      (_, i) => ( 

                        <Cell 

                          key={i} 

                          fill={ 

                            COLORS[ 

                              i % COLORS.length]} 

                        /> 

                    ))} 

                  </Pie> 

                  <Tooltip /> 

                </PieChart> 

              </ResponsiveContainer> 

            </div> 

          </div> 

 

          <div className="col-md-7"> 

            <div className="dc-card p-0" 

                 style={{overflow:'hidden'}}> 

              <table className="table 

                                table-hover 

                                mb-0"> 

                <thead style={{ 

                  background:'var(--light)'}}> 

                  <tr> 

                    <th>User</th> 

                    <th>Role</th> 

                    <th>Questions</th> 

                    <th>Actions</th> 

                  </tr> 

                </thead> 

                <tbody> 

                  {users.map((u) => ( 

                    <tr key={u.id}> 

                      <td> 

                        <b>{u.username}</b> 

                        <br/> 

                        <small style={{ 

                          color:'var(--gray)'}}> 

                          {u.email} 

                        </small> 

                      </td> 

                      <td> 

                        <select 

                          className="form-select 

                                     form-select-sm" 

                          value={u.role} 

                          onChange={(e) => 

                            handleRoleChange( 

                              u.id, 

                              e.target.value)} 

                        > 

                          <option>USER</option> 

                          <option>MODERATOR</option> 

                          <option>ADMIN</option> 

                        </select> 

                      </td> 

                      <td>{u.totalQuestions}</td> 

                      <td> 

                        <button 

                          className="btn btn-sm 

                                     btn-danger" 

                          onClick={() => 

                            handleDeleteUser(u.id)} 

                        > 

                          🗑️ 

                        </button> 

                      </td> 

                    </tr> 

                  ))} 

                </tbody> 

              </table> 

            </div> 

          </div> 

        </div> 

      )} 

 

      {/* MODERATION TAB */} 

      {activeTab === 'moderation' && ( 

        <div className="dc-card p-0" 

             style={{overflow:'hidden'}}> 

          {flagged.length === 0 ? ( 

            <div className="text-center p-5"> 

              <div style={{fontSize:'4rem'}}> 

                ✅ 

              </div> 

              <h4>All Clear!</h4> 

              <p style={{color:'var(--gray)'}}> 

                No content pending review 

              </p> 

            </div> 

          ) : ( 

            <table className="table 

                              table-hover 

                              mb-0"> 

              <thead style={{ 

                background:'var(--light)'}}> 

                <tr> 

                  <th>Type</th> 

                  <th>Content</th> 

                  <th>Reason</th> 

                  <th>Date</th> 

                  <th>Actions</th> 

                </tr> 

              </thead> 

              <tbody> 

                {flagged.map((item) => ( 

                  <tr key={item.id}> 

                    <td> 

                      <span className="dc-tag"> 

                        {item.contentType} 

                      </span> 

                    </td> 

                    <td style={{ 

                      maxWidth:'200px', 

                      overflow:'hidden', 

                      textOverflow:'ellipsis', 

                      whiteSpace:'nowrap' 

                    }}> 

                      {item.content} 

                    </td> 

                    <td> 

                      <span style={{ 

                        color:'var(--danger)', 

                        fontWeight:600, 

                        fontSize:'0.85rem' 

                      }}> 

                        {item.reason} 

                      </span> 

                    </td> 

                    <td> 

                      <small> 

                        {new Date(item.flaggedAt) 

                          .toLocaleDateString()} 

                      </small> 

                    </td> 

                    <td> 

                      <div className="d-flex 

                                      gap-2"> 

                        <button 

                          className="btn btn-sm 

                                     btn-success" 

                          onClick={() => 

                            handleApprove(item.id)} 

                        > 

                          ✅ 

                        </button> 

                        <button 

                          className="btn btn-sm 

                                     btn-danger" 

                          onClick={() => 

                            handleReject(item.id)} 

                        > 

                          🗑️ 

                        </button> 

                      </div> 

                    </td> 

                  </tr> 

                ))} 

              </tbody> 

            </table> 

          )} 

        </div> 

      )} 


      {activeTab === 'chat' && (
  <div className="dc-card">
    <h5 style={{ fontWeight: 700, marginBottom: '1rem' }}>
      💬 Chat Analytics
    </h5>

    <ResponsiveContainer width="100%" height={300}>
      <BarChart
        data={[
          { name: "Messages", value: stats?.totalMessages || 0 },
          { name: "Users", value: stats?.totalUsers || 0 },
        ]}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Bar dataKey="value" fill="#4CC9F0" />
      </BarChart>
    </ResponsiveContainer>
  </div>
)}


    </div> 

  ); 

}; 

 

export default AdminDashboardPage; 