/** 

 * Question Slice 

 * Manages questions state globally 

 */ 

import { createSlice } from '@reduxjs/toolkit'; 

 

const questionSlice = createSlice({ 

  name: 'questions', 

  initialState: { 

    questions: [], 

    selectedQuestion: null, 

    loading: false, 

    error: null, 

  }, 

  reducers: { 

    setQuestions: (state, action) => { 

      state.questions = action.payload; 

    }, 

    setSelectedQuestion: (state, action) => { 

      state.selectedQuestion = action.payload; 

    }, 

    setLoading: (state, action) => { 

      state.loading = action.payload; 

    }, 

    setError: (state, action) => { 

      state.error = action.payload; 

    }, 

    addQuestion: (state, action) => { 

      state.questions.unshift(action.payload); 

    }, 

    updateQuestion: (state, action) => { 

      const index = state.questions.findIndex( 

        q => q.id === action.payload.id); 

      if (index !== -1) { 

        state.questions[index] = action.payload; 

      } 

    }, 

    removeQuestion: (state, action) => { 

      state.questions = state.questions.filter( 

        q => q.id !== action.payload); 

    }, 

  }, 

}); 

 

export const { 

  setQuestions, 

  setSelectedQuestion, 

  setLoading, 

  setError, 

  addQuestion, 

  updateQuestion, 

  removeQuestion, 

} = questionSlice.actions; 

 

export default questionSlice.reducer;