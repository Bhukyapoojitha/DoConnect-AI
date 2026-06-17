import axiosInstance from "../api/axiosConfig";

const moderationService = {

    getStats: () =>
  axiosInstance.get("/moderation/stats"),
  getPendingContent: () =>
    axiosInstance.get("/moderation/pending"),

  approveContent: (id) =>
    axiosInstance.put(`/moderation/${id}/approve`),

  rejectContent: (id) =>
    axiosInstance.put(`/moderation/${id}/reject`),

  reportContent: (data) =>
    axiosInstance.post("/moderation/flag", data)
};

export default moderationService;