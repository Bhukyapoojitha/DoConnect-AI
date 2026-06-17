/** 

 * QuestionDetailPage 

 * Shows question + answers + AI answer 

 * Users can post answers and vote 

 */ 

import { useEffect, useState } from 'react'; 

import { useParams, useNavigate } 

                    from 'react-router-dom'; 

import { useSelector } from 'react-redux'; 

import { toast } from 'react-toastify'; 

import questionService from 

                '../services/questionService'; 

import answerService from 

                '../services/answerService'; 

import aiService from '../services/aiService'; 

import AnswerCard from 

                '../components/AnswerCard'; 

import { validateAnswer } from 

                          '../utils/validate'; 


import moderationService from '../services/moderationService'; 

const QuestionDetailPage = () => { 

  const { id } = useParams(); 

  const navigate = useNavigate(); 

  const { user } = 

    useSelector((state) => state.auth); 

 

  const [question, setQuestion] = useState(null); 

  const [answers, setAnswers] = useState([]); 

  const [aiAnswer, setAiAnswer] = useState(''); 

  const [answerInput, setAnswerInput] = 

                                    useState(''); 

  const [answerError, setAnswerError] = 

                                    useState(''); 

  const [loadingAI, setLoadingAI] = 

                                useState(false); 

  const [loading, setLoading] = useState(true); 

 

  useEffect(() => { 

    loadData(); 

  }, [id]); 

 

  const loadData = async () => { 

    try { 

      const [qData, aData] = await Promise.all([ 

        questionService.getQuestionById(id), 

        answerService.getAnswersByQuestion(id), 

      ]); 

      setQuestion(qData.data); 

      setAnswers(aData.data || []); 

    } catch { 

      toast.error('Failed to load question'); 

    } finally { 

      setLoading(false); 

    } 

  }; 

 

  const handleGenerateAI = async () => { 

    setLoadingAI(true); 

    try { 

      const data = 

        await aiService.generateAnswer(id); 

      setAiAnswer(data.data?.answer || ''); 

      toast.success('🤖 AI answer generated!'); 

    } catch { 

      toast.error('AI generation failed'); 

    } finally { 

      setLoadingAI(false); 

    } 

  }; 

 const handleReportQuestion = async (questionId) => {
  try {
await moderationService.reportContent({
  contentId: question.id,
  contentType: "QUESTION",
  content: question.content,
  reason: "REPORTED_BY_USER"
});

    toast.success("✅ Content reported successfully");
  } catch (error) {
    console.error(error);
    toast.error("❌ Failed to report content");
  }
};


  const handlePostAnswer = async (e) => { 

    e.preventDefault(); 

    const errors = 

      validateAnswer({ content: answerInput }); 

    if (errors.content) { 

      setAnswerError(errors.content); 

      return; 

    } 

 

    try { 

      const data = await answerService 

                              .postAnswer({ 

        content: answerInput, 

        questionId: parseInt(id), 

      }); 

      setAnswers([...answers, data.data]); 

      setAnswerInput(''); 

      toast.success('Answer posted! ✅'); 

    } catch { 

      toast.error('Failed to post answer'); 

    } 

  }; 

 

  const handleAccept = async (answerId) => { 

    try { 

      await answerService.acceptAnswer(answerId); 

      loadData(); 

      toast.success('Answer accepted! ✅'); 

    } catch { 

      toast.error('Failed to accept'); 

    } 

  }; 

 

  const handleDelete = async (answerId) => { 

    try { 

      await answerService.deleteAnswer(answerId); 

      setAnswers(answers.filter( 

                    a => a.id !== answerId)); 

      toast.success('Answer deleted'); 

    } catch { 

      toast.error('Failed to delete'); 

    } 

  }; 

 

  if (loading) return ( 

    <div className="dc-container"> 

      <div className="dc-spinner"/> 

    </div> 

  ); 

 

  if (!question) return ( 

    <div className="dc-container"> 

      <p>Question not found.</p> 

    </div> 

  ); 

 

  return ( 

    <div className="dc-container"> 

 

      {/* Question */} 

      <div className="dc-card mb-4"> 

        <h2 style={{ 

          color:'var(--primary)', 

          fontWeight:700, 

          marginBottom:'1rem' 

        }}> 

          {question.title} 

        </h2> 

 

        <p style={{ 

          fontSize:'1rem', 

          lineHeight:1.8, 

          whiteSpace:'pre-wrap' 

        }}> 

          {question.content} 

        </p> 

 

        {/* Tags */} 

        <div className="my-3"> 

          {question.tags?.map((tag) => ( 

            <span key={tag} 

                  className="dc-tag"> 

              {tag} 

            </span> 

          ))} 

        </div> 

 

        {/* Meta + Vote */} 

        <div className="d-flex 

                        justify-content-between 

                        align-items-center"> 

          <small style={{color:'var(--gray)'}}> 

            Asked by{' '} 

            <b>{question.username}</b> 

            {' · '}{question.views} views 

          </small> 

 

          <div className="d-flex gap-2"> 

            <button 

              className="btn btn-sm 

                         btn-outline-primary" 

              onClick={() => 

                questionService.voteQuestion( 

                  id, 'up') 

                .then(loadData)} 

            > 

              👍 {question.votes} 

            </button> 

            <button 

              className="btn btn-sm 

                         btn-outline-secondary" 

              onClick={() => 

                questionService.voteQuestion( 

                  id, 'down') 

                .then(loadData)} 

            > 

              👎 

            </button> 

            <button
  className="btn btn-warning ms-2"
  onClick={() => handleReportQuestion(question.id)}
>
  🚩 Report
</button>


          </div> 

        </div> 

      </div> 

 

      {/* AI Answer */} 

      <div className="dc-card mb-4" 

           style={{ 

             borderLeft: 

               '4px solid var(--primary)' 

           }}> 

        <div className="d-flex 

                        justify-content-between 

                        align-items-center 

                        mb-3"> 

          <h5 style={{ 

            color:'var(--primary)', 

            fontWeight:700, 

            margin:0 

          }}> 

            🤖 AI Answer Assistant 

          </h5> 

          <button 

            className="btn-primary-dc" 

            onClick={handleGenerateAI} 

            disabled={loadingAI} 

            style={{fontSize:'0.9rem'}} 

          > 

            {loadingAI ? 

              '⏳ Generating...' : 

              '🤖 Generate AI Answer'} 

          </button> 

         

        </div> 

 

        {aiAnswer ? ( 

          <div style={{ 

            background:'var(--light)', 

            borderRadius:'var(--border-radius)', 

            padding:'1rem', 

            whiteSpace:'pre-wrap', 

            lineHeight:1.8 

          }}> 

            {aiAnswer} 

          </div> 

        ) : ( 

          <p style={{color:'var(--gray)'}}> 

            Click the button to get an 

            AI-powered answer using Gemini 

          </p> 

        )} 

      </div> 

 

      {/* Answers */} 

      <h4 style={{ 

        fontWeight:700, 

        marginBottom:'1rem' 

      }}> 

        💬 {answers.length} Answers 

      </h4> 

 

      {answers.map((answer) => ( 

        <AnswerCard 

          key={answer.id} 

          answer={answer} 

          questionOwnerId={question.userId} 

          currentUserId={user?.id} 

          onAccept={handleAccept} 

          onDelete={handleDelete} 

        /> 

      ))} 

 

      {/* Post Answer */} 

      <div className="dc-card mt-4"> 

        <h5 style={{fontWeight:700, 

                    marginBottom:'1rem'}}> 

          ✍️ Your Answer 

        </h5> 

        <form onSubmit={handlePostAnswer}> 

          <textarea 

            className={`dc-input mb-2 ${ 

              answerError ? 'error' : ''}`} 

            rows={5} 

            placeholder="Write your answer here... Include code examples if needed." 

            value={answerInput} 

            onChange={(e) => { 

              setAnswerInput(e.target.value); 

              setAnswerError(''); 

            }} 

          /> 

          {answerError && ( 

            <div className="error-text mb-2"> 

              {answerError} 

            </div> 

          )} 

          <button 

            type="submit" 

            className="btn-primary-dc"> 

            Post Answer 🚀 

          </button> 

        </form> 

      </div> 

    </div> 

  ); 

}; 

 

export default QuestionDetailPage;