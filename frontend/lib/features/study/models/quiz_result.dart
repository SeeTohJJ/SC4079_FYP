class QuizResult {

    final bool correct;
    final String topicName;
    final int previousMastery;
    final int updatedMastery;
    final DateTime nextReviewDate;
    final String feedback;
    final bool newChainGenerated;
    final int waterReward;

    QuizResult({
        required this.correct,
        required this.topicName,
        required this.previousMastery,
        required this.updatedMastery,
        required this.nextReviewDate,
        required this.feedback,
        required this.newChainGenerated,
        required this.waterReward,
    });

    factory QuizResult.fromJson(Map<String, dynamic> json) {
      return QuizResult(
        correct: (json['correct'] ?? json['is_correct']) as bool? ?? false,
        
        topicName: (json['topicName'] ?? json['topic_name']) as String? ?? 'General Topic',
        
        previousMastery: ((json['previousMastery'] ?? json['previous_mastery']) as num?)?.toInt() ?? 0,
        
        updatedMastery: ((json['updatedMastery'] ?? json['updated_mastery']) as num?)?.toInt() ?? 0,
        
        nextReviewDate: json['nextReviewDate'] != null
            ? DateTime.tryParse(json['nextReviewDate'].toString()) ?? DateTime.now()
            : (json['next_review_date'] != null
                ? DateTime.tryParse(json['next_review_date'].toString()) ?? DateTime.now()
                : DateTime.now()),
                
        feedback: (json['feedback'] as String?) ?? '',
        
        newChainGenerated: (json['newChainGenerated'] ?? json['new_chain_generated']) as bool? ?? false,
        
        waterReward: (json['waterReward'] ?? json['water_reward']) as int? ?? 0,
      );
    }
}