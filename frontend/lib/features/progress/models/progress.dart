import 'topic_progress.dart';

class Progress {
  final int dailyStreak;
  final List<TopicProgress> topics;
  final String userName;

  Progress({
    required this.dailyStreak,
    required this.topics,
    required this.userName,
  });

  factory Progress.fromJson(
      Map<String, dynamic> json) {

    return Progress(
      dailyStreak: json['dailyStreak'],
      userName: json['userName'],
      topics: (json['topics'] as List)
          .map(
            (topic) =>
                TopicProgress.fromJson(topic),
          )
          .toList(),
    );
  }

  double? get overallProgress => null;
}