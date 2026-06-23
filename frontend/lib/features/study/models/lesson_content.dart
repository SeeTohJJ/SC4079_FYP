class LessonContent {
  final String nodeId;
  final String title;
  final String content;

  LessonContent({
    required this.nodeId,
    required this.title,
    required this.content,
  });

  factory LessonContent.fromJson(Map<String, dynamic> json) {
    return LessonContent(
      nodeId: json['nodeId'],
      title: json['title'],
      content: json['content'],
    );
  }
}