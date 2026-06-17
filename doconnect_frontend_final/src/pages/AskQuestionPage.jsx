/** 

 * AskQuestionPage 

 * Question creation with AI tag suggestions 

 * and content moderation check 

 */ 

import { useState } from 'react'; 

import { useNavigate } from 'react-router-dom'; 

import { toast } from 'react-toastify'; 

import questionService from 

                '../services/questionService'; 

import aiService from '../services/aiService'; 

import { validateQuestion } from 

                          '../utils/validate'; 

 

const AskQuestionPage = () => { 

  const navigate = useNavigate(); 

 

  const [formData, setFormData] = useState({ 

    title: '', 

    content: '', 

    tags: [], 

  }); 

  const [errors, setErrors] = useState({}); 

  const [tagInput, setTagInput] = useState(''); 

  const [suggestedTags, setSuggestedTags] = 

                                    useState([]); 

  const [loadingTags, setLoadingTags] = 

                                    useState(false); 

  const [loading, setLoading] = useState(false); 

 

  const handleChange = (e) => { 

    setFormData({ 

      ...formData, 

      [e.target.name]: e.target.value, 

    }); 

    if (errors[e.target.name]) { 

      setErrors({ ...errors, 

                  [e.target.name]: '' }); 

    } 

  }; 

 

  // AI tag suggestion 

  const handleSuggestTags = async () => { 

    if (!formData.title || !formData.content) { 

      toast.warning( 

        'Add title and content first!'); 

      return; 

    } 

    setLoadingTags(true); 

    try { 

      const data = await aiService.suggestTags( 

        formData.title, formData.content); 

      setSuggestedTags(data.data?.suggestedTags 

                                          || []); 

      toast.success( 

        '🤖 AI suggested tags!'); 

    } catch { 

      toast.error('Tag suggestion failed'); 

    } finally { 

      setLoadingTags(false); 

    } 

  }; 

 

  const addTag = (tag) => { 

    const cleanTag = tag.toLowerCase().trim(); 

    if (!formData.tags.includes(cleanTag) && 

        formData.tags.length < 5) { 

      setFormData({ 

        ...formData, 

        tags: [...formData.tags, cleanTag], 

      }); 

      setErrors({ ...errors, tags: '' }); 

    } 

  }; 

 

  const removeTag = (tag) => { 

    setFormData({ 

      ...formData, 

      tags: formData.tags.filter( 

                            (t) => t !== tag), 

    }); 

  }; 

 

  const handleSubmit = async (e) => { 

    e.preventDefault(); 

 

    const validationErrors = 

                    validateQuestion(formData); 

    if (Object.keys( 

                validationErrors).length > 0) { 

      setErrors(validationErrors); 

      return; 

    } 

 

    setLoading(true); 

    try { 

      // AI content moderation check 

      const modResult = 

        await aiService.moderateContent( 

          formData.content); 

 

      if (modResult.data?.isToxic) { 

        toast.error( 

          '⚠️ Content violates guidelines!'); 

        setLoading(false); 

        return; 

      } 

 

      await questionService 

                    .createQuestion(formData); 

      toast.success( 

        '✅ Question posted! AI is generating answer...'); 

      navigate('/home'); 

    } catch (err) { 

      toast.error( 

        err.response?.data?.message || 

        'Failed to post question'); 

    } finally { 

      setLoading(false); 

    } 

  }; 

 

  return ( 

    <div className="dc-container"> 

      <div className="dc-card" 

           style={{maxWidth:'800px', 

                   margin:'0 auto'}}> 

 

        <h2 style={{ 

          color:'var(--primary)', 

          fontWeight:800, 

          marginBottom:'1.5rem' 

        }}> 

          ❓ Ask a Question 

        </h2> 

 

        <form onSubmit={handleSubmit}> 

 

          {/* Title */} 

          <div className="mb-4"> 

            <label className="dc-label"> 

              Question Title 

            </label> 

            <input 

              type="text" 

              name="title" 

              className={`dc-input ${ 

                errors.title ? 'error' : ''}`} 

              placeholder="What's your question? Be specific..." 

              value={formData.title} 

              onChange={handleChange} 

            /> 

            <div className="d-flex 

                            justify-content-between"> 

              {errors.title ? ( 

                <div className="error-text"> 

                  {errors.title} 

                </div> 

              ) : <div/>} 

              <small style={{color:'var(--gray)'}}> 

                {formData.title.length}/150 

              </small> 

            </div> 

          </div> 

 

          {/* Content */} 

          <div className="mb-4"> 

            <label className="dc-label"> 

              Question Details 

            </label> 

            <textarea 

              name="content" 

              className={`dc-input ${ 

                errors.content ? 'error' : ''}`} 

              rows={8} 

              placeholder="Describe your problem in detail. Include what you've tried, error messages, and code snippets..." 

              value={formData.content} 

              onChange={handleChange} 

            /> 

            {errors.content && ( 

              <div className="error-text"> 

                {errors.content} 

              </div> 

            )} 

          </div> 

 

          {/* Tags */} 

          <div className="mb-4"> 

            <label className="dc-label"> 

              Tags (1-5) 

            </label> 

 

            {/* Tag input */} 

            <div className="d-flex gap-2 mb-2"> 

              <input 

                type="text" 

                className="dc-input" 

                placeholder="Add tag..." 

                value={tagInput} 

                onChange={(e) => 

                    setTagInput(e.target.value)} 

                onKeyPress={(e) => { 

                  if (e.key === 'Enter') { 

                    e.preventDefault(); 

                    if (tagInput.trim()) { 

                      addTag(tagInput); 

                      setTagInput(''); 

                    } 

                  } 

                }} 

              /> 

              <button 

                type="button" 

                className="btn-outline-dc" 

                onClick={() => { 

                  if (tagInput.trim()) { 

                    addTag(tagInput); 

                    setTagInput(''); 

                  } 

                }} 

              > 

                Add 

              </button> 

              <button 

                type="button" 

                className="btn-primary-dc" 

                onClick={handleSuggestTags} 

                disabled={loadingTags} 

                style={{whiteSpace:'nowrap'}} 

              > 

                {loadingTags ? 

                  '⏳...' : 

                  '🤖 AI Tags'} 

              </button> 

            </div> 

 

            {/* AI suggestions */} 

            {suggestedTags.length > 0 && ( 

              <div className="mb-2"> 

                <small style={{ 

                  color:'var(--gray)'}}> 

                  AI Suggestions (click to add): 

                </small> 

                <div className="mt-1"> 

                  {suggestedTags.map((tag) => ( 

                    <span 

                      key={tag} 

                      className="dc-tag 

                                 dc-tag-ai" 

                      style={{cursor:'pointer'}} 

                      onClick={() => addTag(tag)} 

                    > 

                      + {tag} 

                    </span> 

                  ))} 

                </div> 

              </div> 

            )} 

 

            {/* Selected tags */} 

            <div> 

              {formData.tags.map((tag) => ( 

                <span 

                  key={tag} 

                  className="dc-tag" 

                  style={{cursor:'pointer'}} 

                  onClick={() => removeTag(tag)} 

                > 

                  {tag} ✕ 

                </span> 

              ))} 

            </div> 

            {errors.tags && ( 

              <div className="error-text"> 

                {errors.tags} 

              </div> 

            )} 

          </div> 

 

          {/* Submit */} 

          <button 

            type="submit" 

            className="btn-primary-dc w-100" 

            disabled={loading} 

            style={{ 

              padding:'1rem', 

              fontSize:'1rem' 

            }} 

          > 

            {loading ? 

              '⏳ Posting & generating AI answer...' : 

              '🚀 Post Question'} 

          </button> 

        </form> 

      </div> 

    </div> 

  ); 

}; 

 

export default AskQuestionPage; 