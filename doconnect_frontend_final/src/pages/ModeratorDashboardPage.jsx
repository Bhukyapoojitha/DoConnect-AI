/** 

 * ModeratorDashboardPage.jsx 

 * Only shows content moderation section 

 * For MODERATOR role users 

 */ 

import { useEffect, useState } from 'react'; 

import { toast } from 'react-toastify'; 

import analyticsService from 

                '../services/analyticsService'; 
import moderationService from '../services/moderationService';

 

const ModeratorDashboardPage = () => { 

 

  const [flagged, setFlagged] = useState([]); 

  const [loading, setLoading] = useState(true); 

 

 useEffect(() => {
  loadFlaggedContent();
  loadStats();
}, []);

 

  const [stats, setStats] = useState({
  pending: 0,
  approved: 0,
  rejected: 0
});
const loadStats = async () => {
  try {
    const res = await moderationService.getStats();

    console.log("STATS:", res.data);

    setStats(res.data.data);
  } catch (err) {
    console.error(err);
  }
};

useEffect(() => {
  loadFlaggedContent();
  loadStats();
}, []);


  const loadFlaggedContent = async () => { 

    try { 

      const res = await analyticsService 

                          .getPendingContent(); 

      setFlagged(res.data.data || []); 

    } catch (err) { 

      toast.error('Failed to load content'); 

    } finally { 

      setLoading(false); 

    } 

  }; 

 
const handleApprove = async (id) => {
  try {
    await analyticsService.approveContent(id);

    setFlagged(flagged.filter(f => f.id !== id));

    await loadStats();

    toast.success('✅ Content approved');
  } catch {
    toast.error('Failed to approve');
  }
};

 

const handleReject = async (id) => {
  try {
    await analyticsService.rejectContent(id);

    setFlagged(flagged.filter(f => f.id !== id));

    await loadStats();

    toast.success('🗑️ Content rejected');
  } catch {
    toast.error('Failed to reject');
  }
};

 

  if (loading) return ( 

    <div className="dc-container 

                    text-center py-5"> 

      <div className="dc-spinner"/> 

      <p className="mt-3"> 

        Loading moderation queue... 

      </p> 

    </div> 

  ); 

 

  return ( 

    <div className="dc-container"> 

 

      {/* Header */} 

      <div className="dc-card mb-4" 

           style={{ 

             background: 

               'linear-gradient(135deg, #1A1A2E, #0F3460)', 

             color: 'white' 

           }}> 

        <h2 style={{ 

          margin: 0, 

          fontWeight: 800 

        }}> 

          🛡️ Moderator Dashboard 

        </h2> 

        <p style={{ 

          margin: '0.5rem 0 0', 

          opacity: 0.7 

        }}> 

          Review and moderate flagged content 

        </p> 

      </div> 

 

      {/* Stats */} 

      <div className="row g-3 mb-4"> 

        <div className="col-md-4"> 

          <div className="stat-card danger"> 

            <div style={{fontSize:'2rem'}}> 

              ⚠️ 

            </div> 

            <h3 style={{fontWeight:800}}> 

              {stats.pending} 

            </h3> 

            <small>Pending Review</small> 

          </div> 

        </div> 

        <div className="col-md-4"> 

          <div className="stat-card success"> 

            <div style={{fontSize:'2rem'}}> 

              ✅ 

            </div> 

            <h3 style={{fontWeight:800}}>
  {stats.approved}
</h3>

            <small>Approved Today</small> 

          </div> 

        </div> 

        <div className="col-md-4"> 

          <div className="stat-card"> 

            <div style={{fontSize:'2rem'}}> 

              🗑️ 

            </div> 

            <h3 style={{fontWeight:800}}>
  {stats.rejected}
</h3> 

            <small>Rejected Today</small> 

          </div> 

        </div> 

      </div> 

 

      {/* Flagged Content */} 

      <div className="dc-card"> 

        <h4 style={{ 

          fontWeight: 700, 

          marginBottom: '1.5rem', 

          color: 'var(--dark)' 

        }}> 

          ⚠️ Flagged Content ( 

            {stats.pending} pending) 

        </h4> 

 

        {flagged.length === 0 ? ( 

          <div style={{ 

            textAlign: 'center', 

            padding: '3rem' 

          }}> 

            <div style={{fontSize:'4rem'}}> 

              ✅ 

            </div> 

            <h4>All Clear!</h4> 

            <p style={{color:'var(--gray)'}}> 

              No content pending review 

            </p> 

          </div> 

        ) : ( 

          <div> 

            {flagged.map((item) => ( 

              <div 

                key={item.id} 

                className="dc-card mb-3" 

                style={{ 

                  border: 

                    '1px solid #FFE0E6', 

                  background: '#FFF5F7' 

                }} 

              > 

                <div className="d-flex 

                                justify-content-between 

                                align-items-start 

                                mb-2"> 

                  <div> 

                    <span style={{ 

                      background: 

                        'var(--primary)', 

                      color: 'white', 

                      padding: 

                        '0.2rem 0.6rem', 

                      borderRadius: '20px', 

                      fontSize: '0.75rem', 

                      fontWeight: 600, 

                      marginRight: '0.5rem' 

                    }}> 

                      {item.contentType} 

                    </span> 

                    <span style={{ 

                      background: '#FF6584', 

                      color: 'white', 

                      padding: 

                        '0.2rem 0.6rem', 

                      borderRadius: '20px', 

                      fontSize: '0.75rem', 

                      fontWeight: 600 

                    }}> 

                      ⚠️ {item.reason} 

                    </span> 

                  </div> 

                  <small style={{ 

                    color: 'var(--gray)' 

                  }}> 

                    {new Date(item.flaggedAt) 

                      .toLocaleDateString()} 

                  </small> 

                </div> 

 

                <p style={{ 

                  fontSize: '0.95rem', 

                  marginBottom: '1rem', 

                  color: 'var(--dark)', 

                  background: 'white', 

                  padding: '0.8rem', 

                  borderRadius: '8px', 

                  border: '1px solid #E9ECEF' 

                }}> 

                  {item.content} 

                </p> 

 

                <div className="d-flex gap-2"> 

                  <button 

                    className="btn btn-success" 

                    onClick={() => 

                              handleApprove( 

                                item.id)} 

                  > 

                    ✅ Approve Content 

                  </button> 

                  <button 

                    className="btn btn-danger" 

                    onClick={() => 

                              handleReject( 

                                item.id)} 

                  > 

                    🗑️ Reject & Delete 

                  </button> 

                </div> 

              </div> 

            ))} 

          </div> 

        )} 

      </div> 

    </div> 

  ); 

}; 

 

export default ModeratorDashboardPage; 