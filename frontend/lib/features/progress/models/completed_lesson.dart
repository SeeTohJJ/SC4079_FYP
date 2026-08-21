class CompletedLesson {
  final String nodeId;
  final String title;
  final String topicId;

  CompletedLesson({
    required this.nodeId,
    required this.title,
    required this.topicId,
  });

  factory CompletedLesson.fromJson(
      Map<String, dynamic> json) {
    return CompletedLesson(
      nodeId: json['nodeId'],
      title: json['title'],
      topicId: json['topicId'],
    );
  }
}