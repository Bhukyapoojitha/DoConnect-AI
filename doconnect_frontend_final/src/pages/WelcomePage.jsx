/** 

 * WelcomePage 

 * Landing page for DoConnect AI 

 * Shows features and call to action 

 */ 

import { Link } from 'react-router-dom'; 

 

const features = [ 

  { 

    icon: '🤖', 

    title: 'AI Answer Generation', 

    desc: 'Get instant AI-powered answers using Google Gemini' 

  }, 

  { 

    icon: '💬', 

    title: 'Real-Time Chat', 

    desc: 'Collaborate live with developers worldwide' 

  }, 

  { 

    icon: '🏷️', 

    title: 'Smart Tag Prediction', 

    desc: 'AI suggests the perfect tags for your questions' 

  }, 

  { 

    icon: '🛡️', 

    title: 'AI Moderation', 

    desc: 'Automatic toxic content detection and filtering' 

  }, 

  { 

    icon: '📊', 

    title: 'Analytics Dashboard', 

    desc: 'Track trending topics and engagement metrics' 

  }, 

  { 

    icon: '🔒', 

    title: 'JWT Security', 

    desc: 'Enterprise-grade authentication and authorization' 

  }, 

]; 

 

const WelcomePage = () => { 

  return ( 

    <div> 

 

      {/* Hero Section */} 

      <div style={{ 

        background: 'linear-gradient(135deg, #1A1A2E 0%, #16213E 50%, #0F3460 100%)', 

        minHeight: '90vh', 

        display: 'flex', 

        alignItems: 'center', 

        justifyContent: 'center', 

        textAlign: 'center', 

        color: 'white', 

        padding: '2rem', 

        position: 'relative', 

        overflow: 'hidden', 

      }}> 

 

        {/* Background circles */} 

        <div style={{ 

          position:'absolute', 

          width:'500px', height:'500px', 

          background:'rgba(108,99,255,0.1)', 

          borderRadius:'50%', 

          top:'-100px', right:'-100px' 

        }}/> 

        <div style={{ 

          position:'absolute', 

          width:'300px', height:'300px', 

          background:'rgba(255,101,132,0.1)', 

          borderRadius:'50%', 

          bottom:'-50px', left:'-50px' 

        }}/> 

 

        {/* Hero Content */} 

        <div style={{ 

          position:'relative', 

          zIndex:1, 

          maxWidth:'800px' 

        }}> 

          <div className="float-animation" 

               style={{fontSize:'5rem', 

                       marginBottom:'1rem'}}> 

            🔗 

          </div> 

 

          <h1 style={{ 

            fontSize:'3.5rem', 

            fontWeight:800, 

            marginBottom:'1rem', 

            lineHeight:1.2 

          }}> 

            DoConnect{' '} 

            <span style={{ 

              color:'#6C63FF' 

            }}> 

              AI 

            </span> 

          </h1> 

 

          <p style={{ 

            fontSize:'1.3rem', 

            opacity:0.8, 

            marginBottom:'2rem', 

            lineHeight:1.8 

          }}> 

            The intelligent discussion platform 

            where developers ask, answer, and 

            collaborate with the power of 

            AI-driven insights 

          </p> 

 

          <div className="d-flex 

                          justify-content-center 

                          gap-3 flex-wrap"> 

            <Link to="/register" 

                  className="btn-primary-dc" 

                  style={{fontSize:'1.1rem', 

                          padding:'0.9rem 2.5rem'}}> 

              🚀 Get Started Free 

            </Link> 

            <Link to="/login" 

                  className="btn-outline-dc" 

                  style={{ 

                    fontSize:'1.1rem', 

                    padding:'0.9rem 2.5rem', 

                    color:'white', 

                    borderColor:'white' 

                  }}> 

              Login 

            </Link> 

          </div> 

        </div> 

      </div> 

 

      {/* Features Section */} 

      <div style={{ 

        padding:'5rem 2rem', 

        background:'var(--light)' 

      }}> 

        <div className="dc-container"> 

          <div className="text-center mb-5"> 

            <h2 style={{ 

              fontSize:'2.5rem', 

              fontWeight:800, 

              color:'var(--dark)' 

            }}> 

              Everything You Need 

            </h2> 

            <p style={{color:'var(--gray)'}}> 

              Powered by Google Gemini AI 

            </p> 

          </div> 

 

          <div className="row g-4"> 

            {features.map((f, i) => ( 

              <div key={i} 

                   className="col-md-4"> 

                <div className="dc-card 

                                h-100 

                                text-center"> 

                  <div style={{ 

                    fontSize:'3rem', 

                    marginBottom:'1rem' 

                  }}> 

                    {f.icon} 

                  </div> 

                  <h5 style={{ 

                    fontWeight:700, 

                    color:'var(--primary)' 

                  }}> 

                    {f.title} 

                  </h5> 

                  <p style={{ 

                    color:'var(--gray)', 

                    fontSize:'0.95rem' 

                  }}> 

                    {f.desc} 

                  </p> 

                </div> 

              </div> 

            ))} 

          </div> 

        </div> 

      </div> 

 

      {/* CTA Section */} 

      <div style={{ 

        background: 'linear-gradient(135deg, #6C63FF, #5A52D5)', 

        padding:'4rem 2rem', 

        textAlign:'center', 

        color:'white' 

      }}> 

        <h2 style={{fontSize:'2rem', 

                    fontWeight:800, 

                    marginBottom:'1rem'}}> 

          Ready to Connect? 

        </h2> 

        <p style={{opacity:0.8, 

                   marginBottom:'2rem'}}> 

          Join thousands of developers 

          already using DoConnect AI 

        </p> 

        <Link to="/register" 

              className="btn-primary-dc" 

              style={{ 

                background:'white', 

                color:'var(--primary)', 

                fontSize:'1.1rem', 

                padding:'0.9rem 2.5rem' 

              }}> 

          Join Now — It's Free 🎉 

        </Link> 

      </div> 

    </div> 

  ); 

}; 

 

export default WelcomePage;