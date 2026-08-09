import 'package:flutter/material.dart';

import 'package:frontend/features/progress/models/progress.dart';
import 'package:frontend/features/progress/models/topic_progress.dart';
import 'package:frontend/features/progress/services/progress_service.dart';

class ProgressPage extends StatefulWidget {
  const ProgressPage({super.key});

  @override
  State<ProgressPage> createState() => _ProgressPageState();
}

class _ProgressPageState extends State<ProgressPage> {

  final ProgressService progressService = ProgressService();

  Progress? progress;

  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    loadProgress();
  }

  Future<void> loadProgress() async {
    try {
      final result = await progressService.getProgress();

      if (!mounted) return;

      setState(() {
        progress = result;
        isLoading = false;
      });
    } catch (e) {
      if (!mounted) return;

      setState(() {
        isLoading = false;
      });

      debugPrint('Failed to load progress: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }

    if (progress == null) {
      return const Scaffold(
        body: Center(
          child: Text(
            'Failed to load progress',
          ),
        ),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text('Progress'),
      ),

      body: RefreshIndicator(
        onRefresh: loadProgress,

        child: ListView(
          padding: const EdgeInsets.all(16),

          children: [
            _buildStreakCard(),

            const SizedBox(height: 24),

            const Text(
              'Your Progress',
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 12),

            ...progress!.topics.map(
              (topic) => _buildTopicCard(topic),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStreakCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(20),

        child: Row(
          children: [
            const Icon(
              Icons.local_fire_department,
              size: 42,
            ),

            const SizedBox(width: 16),

            Column(
              crossAxisAlignment:
                  CrossAxisAlignment.start,

              children: [
                Text(
                  '${progress!.dailyStreak} Day Streak',
                  style: const TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 4),

                const Text(
                  'Keep learning every day!',
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTopicCard(
      TopicProgress topic) {

    final lessonProgress =
        topic.totalLessons == 0
            ? 0.0
            : topic.completedLessons /
                topic.totalLessons;

    final mastery = topic.masteryScore * 100.0;

    return Card(
      margin: const EdgeInsets.only(
        bottom: 12,
      ),

      child: Padding(
        padding: const EdgeInsets.all(16),

        child: Column(
          crossAxisAlignment:
              CrossAxisAlignment.start,

          children: [
            Text(
              topic.topicName,
              style: const TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 16),

            Row(
              mainAxisAlignment:
                  MainAxisAlignment.spaceBetween,

              children: [
                const Text('Lessons'),

                Text(
                  '${topic.completedLessons} '
                  '/ ${topic.totalLessons}',
                ),
              ],
            ),

            const SizedBox(height: 6),

            LinearProgressIndicator(
              value: lessonProgress,
            ),

            const SizedBox(height: 16),

            Row(
              mainAxisAlignment:
                  MainAxisAlignment.spaceBetween,

              children: [
                const Text('Mastery'),

                Text(
                  '${mastery.toStringAsFixed(0)}%',
                ),
              ],
            ),

            const SizedBox(height: 6),

            LinearProgressIndicator(
              value: topic.masteryScore,
            ),
          ],
        ),
      ),
    );
  }
}