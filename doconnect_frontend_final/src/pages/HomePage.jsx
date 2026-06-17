/** 

 * HomePage 

 * Main questions feed with search 

 * Shows trending and all questions 

 */ 

import { useEffect, useState } from 'react'; 

import { useDispatch, useSelector } 

                            from 'react-redux'; 

import { Link } from 'react-router-dom'; 

import { setQuestions, setLoading } 

            from '../redux/slices/questionSlice'; 

import questionService from 

                '../services/questionService'; 

import QuestionCard from 

                '../components/QuestionCard'; 

 

const HomePage = () => { 

  const dispatch = useDispatch(); 

  const { questions, loading } = 

    useSelector((state) => state.questions); 

 

  const [search, setSearch] = useState(''); 

  const [activeTab, setActiveTab] = 

                              useState('all'); 

 

  useEffect(() => { 

    loadQuestions(); 

  }, []); 

 

  const loadQuestions = async () => { 

    dispatch(setLoading(true)); 

    try { 

      const data = await questionService 

                              .getAllQuestions(); 

      dispatch(setQuestions( 

                    data.data || [])); 

    } catch (err) { 

      console.error('Failed to load'); 

    } finally { 

      dispatch(setLoading(false)); 

    } 

  }; 

 

  const loadTrending = async () => { 

    try { 

      const data = await questionService 

                              .getTrending(); 

      dispatch(setQuestions( 

                    data.data || [])); 

    } catch (err) { 

      console.error('Failed to load trending'); 

    } 

  }; 

 

  const handleSearch = async (e) => { 

    e.preventDefault(); 

    if (!search.trim()) { 

      loadQuestions(); 

      return; 

    } 

    try { 

      const data = await questionService 

                    .searchQuestions(search); 

      dispatch(setQuestions( 

                    data.data || [])); 

    } catch (err) { 

      console.error('Search failed'); 

    } 

  }; 

 

  const handleTabChange = (tab) => { 

    setActiveTab(tab); 

    if (tab === 'trending') { 

      loadTrending(); 

    } else { 

      loadQuestions(); 

    } 

  }; 

 

  return ( 

    <div className="dc-container"> 

 

      {/* Header */} 

      <div className="section-header"> 

        <h2 className="section-title"> 

          {activeTab === 'trending' ? 

            '🔥 Trending Questions' : 

            '❓ All Questions'} 

        </h2> 

        <Link to="/ask" 

              className="btn-primary-dc"> 

          + Ask Question 

        </Link> 

      </div> 

 

      {/* Search Bar */} 

      <form onSubmit={handleSearch} 

            className="mb-4"> 

        <div className="d-flex gap-2"> 

          <input 

            type="text" 

            className="dc-input" 

            placeholder="🔍 Search questions..." 

            value={search} 

            onChange={(e) => 

                        setSearch(e.target.value)} 

          /> 

          <button 

            type="submit" 

            className="btn-primary-dc" 

            style={{whiteSpace:'nowrap'}}> 

            Search 

          </button> 

        </div> 

      </form> 

 

      {/* Tabs */} 

      <div className="d-flex gap-2 mb-4"> 

        {['all', 'trending'].map((tab) => ( 

          <button 

            key={tab} 

            onClick={() => handleTabChange(tab)} 

            style={{ 

              padding:'0.5rem 1.2rem', 

              borderRadius:'20px', 

              border:'none', 

              background: activeTab === tab ? 

                'var(--primary)' : 

                'var(--gray-light)', 

              color: activeTab === tab ? 

                'white' : 'var(--gray)', 

              fontWeight:600, 

              cursor:'pointer', 

              transition:'all 0.3s' 

            }} 

          > 

            {tab === 'all' ? 

              '📋 All' : '🔥 Trending'} 

          </button> 

        ))} 

      </div> 

 

      {/* Questions List */} 

      {loading ? ( 

        <div className="dc-spinner" /> 

      ) : questions.length === 0 ? ( 

        <div className="dc-card text-center 

                        py-5"> 

          <div style={{fontSize:'4rem'}}> 

            🤔 

          </div> 

          <h4>No questions found</h4> 

          <p className="text-muted"> 

            Be the first to ask! 

          </p> 

          <Link to="/ask" 

                className="btn-primary-dc"> 

            Ask a Question 

          </Link> 

        </div> 

      ) : ( 

        questions.map((q) => ( 

          <QuestionCard key={q.id} 

                        question={q} /> 

        )) 

      )} 

    </div> 

  ); 

}; 

 

export default HomePage;