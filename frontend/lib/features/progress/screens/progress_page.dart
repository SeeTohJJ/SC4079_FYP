import 'package:flutter/material.dart';

import 'package:frontend/features/progress/models/completed_lesson.dart';
import 'package:frontend/features/progress/models/progress.dart';
import 'package:frontend/features/progress/models/topic_progress.dart';
import 'package:frontend/features/progress/screens/library_lesson_page.dart';
import 'package:frontend/features/progress/services/progress_service.dart';

import 'package:frontend/features/study/screens/nodes/lesson_node_page.dart';

class ProgressPage extends StatefulWidget {
  const ProgressPage({super.key});

  @override
  State<ProgressPage> createState() =>
      _ProgressPageState();
}

class _ProgressPageState
    extends State<ProgressPage> {

  final ProgressService progressService =
      ProgressService();

  Progress? progress;

  List<CompletedLesson> completedLessons = [];

  bool isLoading = true;

  int _selectedTab = 0;
  String _greeting = '';

  @override
  void initState() {
    super.initState();
    _setRandomGreeting();
    loadProgress();
  }

  Future<void> loadProgress() async {
    try {
      final result = await progressService.getProgress();

      final lessons = await progressService.getCompletedLessons();

      if (!mounted) return;

      setState(() {
        progress = result;
        completedLessons = lessons;
        isLoading = false;
      });

    } catch (e) {
      if (!mounted) return;

      setState(() {
        isLoading = false;
      });

      debugPrint(
        'Failed to load progress: $e',
      );
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
            _buildProgressHeader(),

            const SizedBox(height: 16),

            _buildStreakCard(),

            const SizedBox(height: 24),

            SegmentedButton<int>(
              segments: const [

                ButtonSegment<int>(
                  value: 0,
                  label: Text("Progress"),
                  icon: Icon(
                    Icons.bar_chart,
                  ),
                ),

                ButtonSegment<int>(
                  value: 1,
                  label: Text("Lessons"),
                  icon: Icon(
                    Icons.menu_book,
                  ),
                ),
              ],

              selected: {_selectedTab},

              onSelectionChanged:
                  (Set<int> selected) {

                setState(() {
                  _selectedTab =
                      selected.first;
                });
              },
            ),

            const SizedBox(height: 16),

            if (_selectedTab == 0)

              ...progress!.topics.map(
                (topic) =>
                    _buildTopicCard(topic),
              )

            else

              _buildLessonsLibrary(),
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
                    fontWeight:
                        FontWeight.bold,
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

    final mastery =
        topic.masteryScore * 100.0;

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
                fontWeight:
                    FontWeight.bold,
              ),
            ),

            const SizedBox(height: 16),

            Row(
              mainAxisAlignment:
                  MainAxisAlignment
                      .spaceBetween,

              children: [

                const Text(
                  'Lessons',
                ),

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
                  MainAxisAlignment
                      .spaceBetween,

              children: [

                const Text(
                  'Mastery',
                ),

                Text(
                  '${mastery.toStringAsFixed(0)}%',
                ),
              ],
            ),

            const SizedBox(height: 6),

            LinearProgressIndicator(
              value:
                  topic.masteryScore,
            ),
          ],
        ),
      ),
    );
  }

  Map<String, List<CompletedLesson>> _groupLessonsByTopic() {

    final Map<String, List<CompletedLesson>>
        grouped = {};

    for (final lesson in completedLessons) {
      grouped
          .putIfAbsent(
            lesson.topicId,
            () => [],
          )
          .add(lesson);
    }

    return grouped;
  }

Widget _buildLessonsLibrary() {
  if (completedLessons.isEmpty) {
    return const Padding(
      padding: EdgeInsets.only(top: 40),
      child: Center(
        child: Column(
          children: [
            Icon(
              Icons.menu_book_outlined,
              size: 64,
            ),

            SizedBox(height: 16),

            Text(
              'No completed lessons yet',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),

            SizedBox(height: 8),

            Text(
              'Complete lessons to see them here.',
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }

  final groupedLessons =
      _groupLessonsByTopic();

  return Column(
    children: groupedLessons.entries.map(
      (entry) {
        final topicId = entry.key;

        final topicName =
            _getTopicName(topicId);

        final lessons = entry.value;

        return Card(
          margin: const EdgeInsets.only(
            bottom: 12,
          ),

          child: ExpansionTile(
            title: Text(
              topicName,
              style: const TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 18,
              ),
            ),

            leading: const Icon(
              Icons.folder,
            ),

            children: lessons.map(
              (lesson) {
                return ListTile(
                  title: Text(
                    lesson.title,
                  ),

                  leading: const Icon(
                    Icons.menu_book,
                  ),

                  trailing: const Icon(
                    Icons.chevron_right,
                  ),

                  onTap: () {
                    _openLesson(
                      lesson.nodeId,
                    );
                  },
                );
              },
            ).toList(),
          ),
        );
      },
    ).toList(),
  );
}

  Future<void> _openLesson(String nodeId) async {
    try {
      final lesson = await studyService.getLessonContent(nodeId);

      if (!mounted) return;

      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => LibraryLessonPage(
            lesson: lesson,
          ),
        ),
      );

    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context)
          .showSnackBar(
        const SnackBar(
          content: Text(
            'Failed to load lesson.',
          ),
        ),
      );
    }
  }

  String _getTopicName(String topicId) {
    switch (topicId) {
      case 'T001':
        return 'Budgeting';

      case 'T002':
        return 'Saving';

      case 'T003':
        return 'Investing';

      case 'T004':
        return 'Credit';

      default:
        return topicId;
    }
  }

  Widget _buildProgressHeader() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          '$_greeting, ${progress!.userName}!',
          style: const TextStyle(
            fontSize: 28,
            fontWeight: FontWeight.bold,
          ),
        ),

        const SizedBox(height: 4),
      ],
    );
  }

  void _setRandomGreeting() {
    final greetings = [
      'Hey',
      'Welcome back',
      'Good to see you',
      'Nice to see you',
      'Ready to learn',
      'Keep it up',
      'Let\'s keep going',
    ];

    greetings.shuffle();

    _greeting = greetings.first;
  }
}