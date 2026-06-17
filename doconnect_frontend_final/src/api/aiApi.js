import axiosInstance from './axiosConfig'; 
 
export const aiApi = { 
  fetchSolutionSuggestion: (qId) => axiosInstance.post(`/ai/suggest-answer/${qId}`).then(res => res.data), 
  summarizeContext: (textPayload) => axiosInstance.post('/ai/summarize', { textContext: textPayload }).then(res => res.data), 
}; 
