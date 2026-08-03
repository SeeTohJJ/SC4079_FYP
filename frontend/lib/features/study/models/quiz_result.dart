class QuizResult {

    final bool correct;
    final String topicName;
    final double previousPKnow;
    final double updatedPKnow;
    final DateTime nextReviewDate;
    final String feedback;
    final bool newChainGenerated;

    QuizResult({
        required this.correct,
        required this.topicName,
        required this.previousPKnow,
        required this.updatedPKnow,
        required this.nextReviewDate,
        required this.feedback,
        required this.newChainGenerated,
    });

    factory QuizResult.fromJson(Map<String, dynamic> json) {
      return QuizResult(
        correct: (json['correct'] ?? json['is_correct']) as bool? ?? false,
        
        topicName: (json['topicName'] ?? json['topic_name']) as String? ?? 'General Topic',
        
        previousPKnow: ((json['previousPKnow'] ?? json['previous_p_know']) as num?)?.toDouble() ?? 0.0,
        
        updatedPKnow: ((json['updatedPKnow'] ?? json['updated_p_know']) as num?)?.toDouble() ?? 0.0,
        
        nextReviewDate: json['nextReviewDate'] != null
            ? DateTime.tryParse(json['nextReviewDate'].toString()) ?? DateTime.now()
            : (json['next_review_date'] != null
                ? DateTime.tryParse(json['next_review_date'].toString()) ?? DateTime.now()
                : DateTime.now()),
                
        feedback: (json['feedback'] as String?) ?? '',
        
        newChainGenerated: (json['newChainGenerated'] ?? json['new_chain_generated']) as bool? ?? false,
      );
    }
}