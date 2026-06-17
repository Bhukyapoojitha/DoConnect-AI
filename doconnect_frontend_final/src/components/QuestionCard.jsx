/** 

 * QuestionCard Component 

 * Displays single question in list 

 */ 

import { Link } from 'react-router-dom'; 

 

const QuestionCard = ({ question }) => { 

  return ( 

    <Link to={`/questions/${question.id}`} 

          className="text-decoration-none"> 

      <div className="question-card 

                      fade-in-up"> 

 

        {/* Title */} 

        <h5>{question.title}</h5> 

 

        {/* Content preview */} 

        <p className="text-muted mb-2" 

           style={{fontSize:'0.9rem'}}> 

          {question.content?.substring(0, 120)}... 

        </p> 

 

        {/* Tags */} 

        <div className="mb-2"> 

          {question.tags?.map((tag) => ( 

            <span key={tag} 

                  className="dc-tag"> 

              {tag} 

            </span> 

          ))} 

        </div> 

 

        {/* Meta info */} 

        <div className="d-flex gap-3" 

             style={{fontSize:'0.8rem', 

                     color:'#6C757D'}}> 

          <span>👤 {question.username}</span> 

          <span>👁️ {question.views} views</span> 

          <span>👍 {question.votes} votes</span> 

          <span> 

            💬 {question.answerCount} answers 

          </span> 

        </div> 

      </div> 

    </Link> 

  ); 

}; 

 

export default QuestionCard; 