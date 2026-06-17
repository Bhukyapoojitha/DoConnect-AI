/** 

 * AnalyticsServiceImpl 

 * Generates dashboard statistics 

 * Uses AI for sentiment analysis 

 */ 
package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.doconnect.service.interfaces.AnalyticsService;
import com.doconnect.service.interfaces.AIService;

import com.doconnect.repository.UserRepository;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.repository.AnswerRepository;
import com.doconnect.repository.ChatMessageRepository;
import com.doconnect.repository.FlaggedContentRepository;

import com.doconnect.entity.Question;
import com.doconnect.entity.Tag;
import com.doconnect.enums.ModerationStatus;

import com.doconnect.dto.analytics.AnalyticsDTO;


@Service 

@RequiredArgsConstructor 

@Slf4j 

public class AnalyticsServiceImpl 

                implements AnalyticsService { 

 

    private final UserRepository userRepository; 

    private final QuestionRepository 

                        questionRepository; 

    private final AnswerRepository 

                        answerRepository; 

    private final ChatMessageRepository 

                        chatMessageRepository; 

    private final FlaggedContentRepository 

                        flaggedContentRepository; 

    private final AIService aiService; 

 

    @Override 

    public AnalyticsDTO getDashboardStats() { 

        log.info("Generating dashboard stats"); 

        return AnalyticsDTO.builder() 

                .totalUsers( 

                    userRepository.count()) 

                .totalQuestions( 

                    questionRepository.count()) 

                .totalAnswers( 

                    answerRepository.count()) 

                .totalMessages( 

                    chatMessageRepository.count()) 

                .flaggedContentCount( 

                    flaggedContentRepository 

                        .countByStatus( 

                            ModerationStatus.PENDING)) 

                .averageSentiment( 

                    getAverageSentiment()) 

                .trendingTopics( 

                    getTrendingTopics()) 

                .activeUsers(getActiveUsers()) 

                .build(); 

    } 

 

    @Override 

    public List<Map<String, Object>> 

                        getTrendingTopics() { 

        return questionRepository 

                .findTop10ByOrderByViewsDesc() 

                .stream() 

                .limit(5) 

                .map(q -> { 

                    Map<String, Object> map = 

                                    new HashMap<>(); 

                    map.put("title", q.getTitle()); 

                    map.put("views", q.getViews()); 

                    map.put("votes", q.getVotes()); 

                    map.put("tags", 

                        q.getTags().stream() 

                            .map(Tag::getName) 

                            .collect( 

                              Collectors.toList())); 

                    return map; 

                }) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public List<Map<String, Object>> 

                        getActiveUsers() { 

        return userRepository.findAll() 

                .stream() 

                .sorted((a, b) -> 

                    b.getQuestions().size() - 

                    a.getQuestions().size()) 

                .limit(5) 

                .map(u -> { 

                    Map<String, Object> map = 

                                    new HashMap<>(); 

                    map.put("username", 

                                u.getUsername()); 

                    map.put("questions", 

                        u.getQuestions().size()); 

                    map.put("answers", 

                        u.getAnswers().size()); 

                    return map; 

                }) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public double getAverageSentiment() { 

        List<Question> questions = 

            questionRepository.findAll(); 

        if (questions.isEmpty()) return 0.0; 

 

        double total = questions.stream() 

                .mapToDouble(q -> 

                    aiService.analyzeSentiment( 

                        q.getContent())) 

                .average() 

                .orElse(0.0); 

 

        return Math.round(total * 100.0) / 100.0; 

    } 

}