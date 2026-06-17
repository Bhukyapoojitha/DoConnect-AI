/** 

 * ChatRoomPage 

 * Real-time WebSocket chat room 

 * Shows history + live messages 

 */ 

import { useEffect, useRef, useState } 

                              from 'react'; 

import { useParams } from 'react-router-dom'; 

import { useSelector } from 'react-redux'; 

import { toast } from 'react-toastify'; 

import useWebSocket from 

                    '../hooks/useWebSocket'; 

import axiosInstance from 

                    '../api/axiosConfig'; 

import { validateMessage } from 

                          '../utils/validate'; 

 

const ChatRoomPage = () => { 

  const { roomId } = useParams(); 

  const { user } = 

    useSelector((state) => state.auth); 

  const username = user?.username || localStorage.getItem("username") || 'Anonymous';

 console.log("CURRENT USERNAME:", username);
console.log("USER OBJECT:", user);

  const { messages, connected, sendMessage } = 

    useWebSocket(roomId, username); 

 

  const [input, setInput] = useState(''); 

  const [history, setHistory] = useState([]); 

  const [inputError, setInputError] = 

                                    useState(''); 

  const bottomRef = useRef(null); 

 

  // Load chat history 

  useEffect(() => { 

    const loadHistory = async () => { 

      try { 

        const res = await axiosInstance 

                    .get(`/chat/${roomId}/history`); 

        setHistory(res.data?.data || []); 

      } catch { 

        console.error('Failed to load history'); 

      } 

    }; 

    loadHistory(); 

  }, [roomId]); 

 

  // Auto scroll to bottom 

  useEffect(() => { 

    bottomRef.current?.scrollIntoView( 

                        { behavior: 'smooth' }); 

  }, [messages, history]); 

 

  const handleSend = () => { 

    const error = validateMessage(input); 

    if (error) { 

      setInputError(error); 

      return; 

    } 

    sendMessage(input); 

    setInput(''); 

    setInputError(''); 

  }; 

 

  const allMessages = [...history, ...messages]; 

 

  return ( 

    <div className="dc-container"> 

      <div style={{ 

        background:'white', 

        borderRadius:'var(--border-radius-lg)', 

        boxShadow:'var(--shadow-lg)', 

        overflow:'hidden' 

      }}> 

 

        {/* Header */} 

        <div style={{ 

          background:'linear-gradient(135deg, var(--dark), var(--dark-3))', 

          padding:'1rem 1.5rem', 

          display:'flex', 

          justifyContent:'space-between', 

          alignItems:'center' 

        }}> 

          <h5 style={{ 

            color:'white', 

            margin:0, 

            fontWeight:700 

          }}> 

            💬 Room: #{roomId} 

          </h5> 

          <span style={{ 

            padding:'0.3rem 0.8rem', 

            borderRadius:'20px', 

            background: connected ? 

              'var(--success)' : 'var(--danger)', 

            color: 'white', 

            fontSize:'0.8rem', 

            fontWeight:600 

          }}> 

            {connected ? 

              '🟢 Live' : '🔴 Offline'} 

          </span> 

        </div> 

 

        {/* Messages */} 

        <div style={{ 

          height:'450px', 

          overflowY:'auto', 

          padding:'1.5rem', 

          background:'var(--light)' 

        }}> 

          {allMessages.map((msg, i) => ( 

            <div key={i} 

                 className="mb-3"> 

 

              {/* System message */} 

              {msg.type === 'JOIN' || 

               msg.type === 'LEAVE' ? ( 

                <div className="text-center"> 

                  <small style={{ 

                    color:'var(--gray)', 

                    background:'var(--gray-light)', 

                    padding:'0.2rem 0.8rem', 

                    borderRadius:'20px' 

                  }}> 

                    {msg.content} 

                  </small> 

                </div> 

              ) : ( 

                /* Chat bubble */ 

                <div style={{ 

                  display:'flex', 

                  flexDirection:'column', 

                  alignItems: 

                    msg.sender === username ? 

                      'flex-end' : 'flex-start' 

                }}> 

                  <small style={{ 

                    color:'var(--gray)', 

                    marginBottom:'0.3rem' 

                  }}> 

                    {msg.sender} 

                  </small> 

                  <div className={ 

                    msg.sender === username ? 

                      'chat-bubble-me' : 

                      'chat-bubble-other' 

                  }> 

                    {msg.content} 

                  </div> 

                  <small style={{ 

                    color:'var(--gray)', 

                    fontSize:'0.7rem', 

                    marginTop:'0.2rem' 

                  }}> 

                    {msg.timestamp ? 

                      new Date(msg.timestamp) 

                        .toLocaleTimeString() : 

                      ''} 

                  </small> 

                </div> 

              )} 

            </div> 

          ))} 

          <div ref={bottomRef} /> 

        </div> 

 

        {/* Input */} 

        <div style={{ 

          padding:'1rem 1.5rem', 

          borderTop:'1px solid var(--gray-light)' 

        }}> 

          {inputError && ( 

            <div className="error-text mb-2"> 

              {inputError} 

            </div> 

          )} 

          <div className="d-flex gap-2"> 

            <input 

              className="dc-input" 

              placeholder={connected ? 

                "Type a message..." : 

                "Connecting..."} 

              value={input} 

              onChange={(e) => { 

                setInput(e.target.value); 

                setInputError(''); 

              }} 

              onKeyPress={(e) => { 

                if (e.key === 'Enter') 

                  handleSend(); 

              }} 

              disabled={!connected} 

            /> 

            <button 

              className="btn-primary-dc" 

              onClick={handleSend} 

              disabled={!connected} 

            > 

              Send 🚀 

            </button> 

          </div> 

        </div> 

      </div> 

    </div> 

  ); 

}; 

 

export default ChatRoomPage; 