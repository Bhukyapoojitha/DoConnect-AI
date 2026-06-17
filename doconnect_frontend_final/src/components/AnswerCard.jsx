/** 

 * AnswerCard Component 

 * Displays single answer with vote controls 

 */ 

import { useState } from 'react'; 

import { toast } from 'react-toastify'; 

import answerService from 

                '../services/answerService'; 

 

const AnswerCard = ({ 

  answer, 

  questionOwnerId, 

  currentUserId, 

  onAccept, 

  onDelete, 

}) => { 

  const [votes, setVotes] = 

                    useState(answer.votes); 

 

  const handleVote = async (type) => { 

    try { 

      await answerService 

                .voteAnswer(answer.id, type); 

      setVotes(prev => 

        type === 'up' ? prev + 1 : prev - 1); 

    } catch { 

      toast.error('Vote failed'); 

    } 

  }; 

 

  return ( 

    <div className={`dc-card mb-3 ${ 

      answer.isAccepted ? 

        'border border-success' : ''}`}> 

 

      {/* Accepted badge */} 

      {answer.isAccepted && ( 

        <div className="mb-2"> 

          <span className="badge bg-success"> 

            ✅ Accepted Answer 

          </span> 

        </div> 

      )} 

 

      {/* AI badge */} 

      {answer.isAiGenerated && ( 

        <div className="mb-2"> 

          <span className="dc-tag dc-tag-ai"> 

            🤖 AI Generated 

          </span> 

        </div> 

      )} 

 

      {/* Content */} 

      <p className="mb-3" 

         style={{whiteSpace:'pre-wrap'}}> 

        {answer.content} 

      </p> 

 

      {/* Footer */} 

      <div className="d-flex 

                      justify-content-between 

                      align-items-center"> 

 

        {/* Vote buttons */} 

        <div className="d-flex 

                        align-items-center 

                        gap-2"> 

          <button 

            className="btn btn-sm 

                       btn-outline-primary" 

            onClick={() => handleVote('up')} 

          > 

            👍 {votes} 

          </button> 

          <button 

            className="btn btn-sm 

                       btn-outline-secondary" 

            onClick={() => handleVote('down')} 

          > 

            👎 

          </button> 

        </div> 

 

        {/* Actions */} 

        <div className="d-flex gap-2"> 

          <small className="text-muted"> 

            By {answer.username} 

          </small> 

 

          {/* Accept button - question owner */} 

          {questionOwnerId === currentUserId && 

           !answer.isAccepted && ( 

            <button 

              className="btn btn-sm btn-success" 

              onClick={() => 

                          onAccept(answer.id)} 

            > 

              ✅ Accept 

            </button> 

          )} 

 

          {/* Delete - answer owner */} 

          {answer.userId === currentUserId && ( 

            <button 

              className="btn btn-sm btn-danger" 

              onClick={() => 

                          onDelete(answer.id)} 

            > 

              🗑️ 

            </button> 

          )} 

        </div> 

      </div> 

    </div> 

  ); 

}; 

 

export default AnswerCard;