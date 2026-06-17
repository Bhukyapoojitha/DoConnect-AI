package com.doconnect.dto.analytics;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Map;




/** 

* AnalyticsDTO 

* Dashboard statistics response 

*/ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class AnalyticsDTO { 

   private long totalUsers; 

   private long totalQuestions; 

   private long totalAnswers; 

   private long totalMessages; 

   private long flaggedContentCount; 

   private double averageSentiment; 

   private List<Map<String, Object>> trendingTopics; 

   private List<Map<String, Object>> activeUsers; 

}