package com.doconnect.service.interfaces;

import java.util.List;
import java.util.Map;

import com.doconnect.dto.analytics.AnalyticsDTO;

public interface AnalyticsService { 

    AnalyticsDTO getDashboardStats(); 

    List<Map<String, Object>> getTrendingTopics(); 

    List<Map<String, Object>> getActiveUsers(); 

    double getAverageSentiment(); 

} 