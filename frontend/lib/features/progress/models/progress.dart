import 'topic_progress.dart';

class Progress {
  final int dailyStreak;
  final List<TopicProgress> topics;

  Progress({
    required this.dailyStreak,
    required this.topics,
  });

  factory Progress.fromJson(
      Map<String, dynamic> json) {

    return Progress(
      dailyStreak: json['dailyStreak'],

      topics: (json['topics'] as List)
          .map(
            (topic) =>
                TopicProgress.fromJson(topic),
          )
          .toList(),
    );
  }
}