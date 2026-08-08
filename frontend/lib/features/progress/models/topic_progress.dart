class TopicProgress {
  final String topicId;
  final String topicName;
  final int completedLessons;
  final int totalLessons;
  final double masteryScore;

  TopicProgress({
    required this.topicId,
    required this.topicName,
    required this.completedLessons,
    required this.totalLessons,
    required this.masteryScore,
  });

  factory TopicProgress.fromJson(
      Map<String, dynamic> json) {

    return TopicProgress(
      topicId: json['topicId'],
      topicName: json['topicName'],
      completedLessons:
          json['completedLessons'],
      totalLessons:
          json['totalLessons'],
      masteryScore:
          (json['masteryScore'] as num).toDouble(),
    );
  }
}