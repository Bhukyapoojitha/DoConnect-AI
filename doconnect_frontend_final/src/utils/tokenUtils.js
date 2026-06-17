export const tokenUtils = {
  decodeTokenClaims: (token) => {
    try {
      return JSON.parse(atob(token.split(".")[1]));
    } catch {
      return null;
    }
  },
};