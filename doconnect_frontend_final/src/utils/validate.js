/** 

 * Validation Utilities 

 * Client-side form validation functions 

 * Matches backend validation rules 

 */ 

 

// Validate registration form 

export const validateRegister = (data) => { 

  const errors = {}; 

 

  if (!data.username?.trim()) 

    errors.username = "Username is required"; 

  else if (data.username.length < 3) 

    errors.username = "Username min 3 characters"; 

  else if (data.username.length > 20) 

    errors.username = "Username max 20 characters"; 

  else if (!/^[a-zA-Z0-9_]*$/.test( 

                                data.username)) 

    errors.username = 

      "Only letters, numbers, underscore"; 

 

  if (!data.email?.trim()) 

    errors.email = "Email is required"; 

  else if (!/\S+@\S+\.\S+/.test(data.email)) 

    errors.email = "Enter a valid email"; 

 

  if (!data.password) 

    errors.password = "Password is required"; 

  else if (data.password.length < 6) 

    errors.password = "Password min 6 characters"; 

  else if ( 

    !/(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])/.test( 

      data.password)) 

    errors.password = 

      "Need uppercase, lowercase & number"; 

 

  return errors; 

}; 

 

// Validate login form 

export const validateLogin = (data) => { 

  const errors = {}; 

 

  if (!data.email?.trim()) 

    errors.email = "Email is required"; 

  else if (!/\S+@\S+\.\S+/.test(data.email)) 

    errors.email = "Enter a valid email"; 

 

  if (!data.password) 

    errors.password = "Password is required"; 

 

  return errors; 

}; 

 

// Validate question form 

export const validateQuestion = (data) => { 

  const errors = {}; 

 

  if (!data.title?.trim()) 

    errors.title = "Title is required"; 

  else if (data.title.length < 10) 

    errors.title = "Title min 10 characters"; 

  else if (data.title.length > 150) 

    errors.title = "Title max 150 characters"; 

 

  if (!data.content?.trim()) 

    errors.content = "Content is required"; 

  else if (data.content.length < 20) 

    errors.content = "Content min 20 characters"; 

 

  if (!data.tags || data.tags.length === 0) 

    errors.tags = "Add at least one tag"; 

  else if (data.tags.length > 5) 

    errors.tags = "Maximum 5 tags allowed"; 

 

  return errors; 

}; 

 

// Validate answer form 

export const validateAnswer = (data) => { 

  const errors = {}; 

 

  if (!data.content?.trim()) 

    errors.content = "Answer cannot be empty"; 

  else if (data.content.length < 10) 

    errors.content = "Answer min 10 characters"; 

 

  return errors; 

}; 

 

// Validate profile update 

export const validateProfile = (data) => { 

  const errors = {}; 

 

  if (!data.username?.trim()) 

    errors.username = "Username is required"; 

  else if (data.username.length < 3) 

    errors.username = "Username min 3 characters"; 

 

  if (data.bio && data.bio.length > 200) 

    errors.bio = "Bio max 200 characters"; 

 

  return errors; 

}; 

 

// Validate password change 

export const validatePassword = (data) => { 

  const errors = {}; 

 

  if (!data.currentPassword) 

    errors.currentPassword = 

      "Current password required"; 

 

  if (!data.newPassword) 

    errors.newPassword = "New password required"; 

  else if (data.newPassword.length < 6) 

    errors.newPassword = "Password min 6 chars"; 

  else if ( 

    !/(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])/.test( 

      data.newPassword)) 

    errors.newPassword = 

      "Need uppercase, lowercase & number"; 

 

  if (!data.confirmPassword) 

    errors.confirmPassword = 

      "Confirm password required"; 

  else if ( 

    data.newPassword !== data.confirmPassword) 

    errors.confirmPassword = 

      "Passwords do not match"; 

 

  return errors; 

}; 

 

// Validate chat message 

export const validateMessage = (content) => { 

  if (!content?.trim()) 

    return "Message cannot be empty"; 

  if (content.length > 500) 

    return "Message max 500 characters"; 

  return null; 

};