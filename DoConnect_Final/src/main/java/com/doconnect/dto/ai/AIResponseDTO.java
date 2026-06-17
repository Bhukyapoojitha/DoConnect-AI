package com.doconnect.dto.ai;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/** 

 * AIResponseDTO 

 * Response from AI operations 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class AIResponseDTO { 

    private String generatedAnswer; 

    private List<String> suggestedTags; 

    private boolean isToxic; 

    private String toxicReason; 

    private double sentimentScore; 

    private String sentimentLabel; 

} 
