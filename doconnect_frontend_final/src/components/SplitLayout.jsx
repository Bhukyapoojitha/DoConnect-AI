/** 

 * SplitLayout Component 

 * Reusable split screen layout 

 * Used for Login and Register pages 

 */ 

import { useState, useEffect } from 'react'; 

 

// Rotating testimonials data 

const testimonials = [ 

  { 

    icon: '💡', 

    text: 'DoConnect AI helped me solve a complex Spring Boot issue in minutes!', 

    author: 'Rahul S., Java Developer', 

  }, 

  { 

    icon: '🤖', 

    text: 'The AI suggestions are incredibly accurate. It saves me hours of research!', 

    author: 'Priya M., Full Stack Dev', 

  }, 

  { 

    icon: '💬', 

    text: 'Real-time chat makes collaboration so much faster and easier.', 

    author: 'Arjun K., Software Engineer', 

  }, 

  { 

    icon: '🚀', 

    text: 'Best developer Q&A platform I have ever used. The AI answers are amazing!', 

    author: 'Sneha T., React Developer', 

  }, 

]; 

 

const SplitLayout = ({ 

  children, 

  title, 

  subtitle, 

}) => { 

  const [currentIndex, setCurrentIndex] = 

                                    useState(0); 

 

  // Auto-rotate testimonials 

  useEffect(() => { 

    const timer = setInterval(() => { 

      setCurrentIndex((prev) => 

        (prev + 1) % testimonials.length); 

    }, 3000); 

    return () => clearInterval(timer); 

  }, []); 

 

  const current = testimonials[currentIndex]; 

 

  return ( 

    <div className="split-screen"> 

 

      {/* LEFT - Form */} 

      <div className="split-left"> 

        <div style={{maxWidth:'420px', 

                     width:'100%'}}> 

 

          {/* Logo */} 

          <div className="text-center mb-4"> 

            <h2 style={{ 

              color:'var(--primary)', 

              fontWeight:800, 

              fontSize:'2rem' 

            }}> 

              🔗 DoConnect AI 

            </h2> 

            <p style={{color:'var(--gray)'}}> 

              {subtitle} 

            </p> 

          </div> 

 

          {/* Form content */} 

          {children} 

        </div> 

      </div> 

 

      {/* RIGHT - Testimonials */} 

      <div className="split-right"> 

        <div style={{ 

          maxWidth:'500px', 

          textAlign:'center', 

          position:'relative', 

          zIndex:1 

        }}> 

 

          {/* Floating icon */} 

          <div className="float-animation" 

               style={{fontSize:'5rem', 

                       marginBottom:'2rem'}}> 

            {current.icon} 

          </div> 

 

          {/* Testimonial card */} 

          <div className="testimonial-card 

                          fade-in-up" 

               key={currentIndex}> 

            <p style={{ 

              fontSize:'1.2rem', 

              lineHeight:1.8, 

              marginBottom:'1rem' 

            }}> 

              "{current.text}" 

            </p> 

            <p style={{ 

              opacity:0.7, 

              fontSize:'0.9rem' 

            }}> 

              — {current.author} 

            </p> 

          </div> 

 

          {/* Dots */} 

          <div className="d-flex 

                          justify-content-center 

                          gap-2 mt-3"> 

            {testimonials.map((_, i) => ( 

              <div 

                key={i} 

                onClick={() => 

                          setCurrentIndex(i)} 

                style={{ 

                  width: i === currentIndex ? 

                            '24px' : '8px', 

                  height:'8px', 

                  borderRadius:'4px', 

                  background: i === currentIndex ? 

                    'white' : 

                    'rgba(255,255,255,0.4)', 

                  cursor:'pointer', 

                  transition:'all 0.3s ease' 

                }} 

              /> 

            ))} 

          </div> 

 

          {/* Stats */} 

          <div className="d-flex 

                          justify-content-center 

                          gap-4 mt-4"> 

            {[ 

              {num:'10K+', label:'Users'}, 

              {num:'50K+', label:'Questions'}, 

              {num:'100K+', label:'Answers'}, 

            ].map(stat => ( 

              <div key={stat.label} 

                   style={{color:'white'}}> 

                <div style={{ 

                  fontSize:'1.5rem', 

                  fontWeight:800 

                }}> 

                  {stat.num} 

                </div> 

                <div style={{ 

                  opacity:0.7, 

                  fontSize:'0.8rem' 

                }}> 

                  {stat.label} 

                </div> 

              </div> 

            ))} 

          </div> 

        </div> 

      </div> 

    </div> 

  ); 

}; 

 

export default SplitLayout;