/** 

 * ChatListPage 

 * Shows available chat rooms 

 * Users can join or create custom rooms 

 */ 

import { useState } from 'react'; 

import { useNavigate } from 'react-router-dom'; 

 

const rooms = [ 

  { 

    id: 'general', 

    label: 'General', 

    icon: '💬', 

    desc: 'General developer discussions' 

  }, 

  { 

    id: 'java', 

    label: 'Java Help', 

    icon: '☕', 

    desc: 'Java and Spring Boot help' 

  }, 

  { 

    id: 'react', 

    label: 'React Help', 

    icon: '⚛️', 

    desc: 'React and frontend discussions' 

  }, 

  { 

    id: 'ai', 

    label: 'AI Discussion', 

    icon: '🤖', 

    desc: 'AI, ML and data science' 

  }, 

  { 

    id: 'database', 

    label: 'Database', 

    icon: '🗄️', 

    desc: 'SQL, MySQL, database help' 

  }, 

  { 

    id: 'devops', 

    label: 'DevOps', 

    icon: '🚀', 

    desc: 'Deployment, Docker, CI/CD' 

  }, 

]; 

 

const ChatListPage = () => { 

  const navigate = useNavigate(); 

  const [customRoom, setCustomRoom] = 

                                    useState(''); 

 

  return ( 

    <div className="dc-container"> 

 

      <div className="section-header"> 

        <h2 className="section-title"> 

          💬 Chat Rooms 

        </h2> 

      </div> 

 

      {/* Room Cards */} 

      <div className="row g-3 mb-4"> 

        {rooms.map((room) => ( 

          <div key={room.id} 

               className="col-md-4"> 

            <div 

              className="dc-card h-100 

                         text-center" 

              style={{cursor:'pointer'}} 

              onClick={() => 

                navigate(`/chat/${room.id}`)} 

            > 

              <div style={{ 

                fontSize:'3rem', 

                marginBottom:'0.5rem' 

              }}> 

                {room.icon} 

              </div> 

              <h5 style={{ 

                fontWeight:700, 

                color:'var(--primary)' 

              }}> 

                {room.label} 

              </h5> 

              <p style={{ 

                color:'var(--gray)', 

                fontSize:'0.9rem' 

              }}> 

                {room.desc} 

              </p> 

              <button 

                className="btn-primary-dc" 

                style={{fontSize:'0.85rem'}}> 

                Join Room → 

              </button> 

            </div> 

          </div> 

        ))} 

      </div> 

 

      {/* Custom Room */} 

      <div className="dc-card"> 

        <h5 style={{fontWeight:700, 

                    marginBottom:'1rem'}}> 

          🎯 Create Custom Room 

        </h5> 

        <div className="d-flex gap-2"> 

          <input 

            type="text" 

            className="dc-input" 

            placeholder="Enter room name..." 

            value={customRoom} 

            onChange={(e) => 

                setCustomRoom(e.target.value)} 

            onKeyPress={(e) => { 

              if (e.key === 'Enter' && 

                  customRoom.trim()) { 

                navigate(`/chat/${customRoom}`); 

              } 

            }} 

          /> 

          <button 

            className="btn-primary-dc" 

            style={{whiteSpace:'nowrap'}} 

            onClick={() => { 

              if (customRoom.trim()) { 

                navigate(`/chat/${customRoom}`); 

              } 

            }} 

          > 

            Join / Create 

          </button> 

        </div> 

      </div> 

    </div> 

  ); 

}; 

 

export default ChatListPage;