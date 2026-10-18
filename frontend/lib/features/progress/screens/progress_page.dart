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
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  'Mastery',
                ),

                Text(
                  getMasteryTitle(topic.masteryScore),
                ),
              ],
            ),

            const SizedBox(height: 6),

            Row(
              children: List.generate(
                10,
                (index) {
                  final isFilled = topic.masteryScore > index;

                  return Expanded(
                    child: Container(
                      height: 10,
                      margin: EdgeInsets.only(
                        right: index == 9 ? 0 : 2,
                      ),
                      decoration: BoxDecoration(
                        color: isFilled
                            ? Theme.of(context).colorScheme.primary
                            : Colors.grey.shade800,
                        borderRadius: BorderRadius.circular(3),
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }


  Map<String, Map<String, List<CompletedLesson>>>
      _groupLessonsByTopicAndSubtopic() {
    final Map<String, Map<String, List<CompletedLesson>>>
        grouped = {};

    for (final lesson in completedLessons) {
      grouped
          .putIfAbsent(
            lesson.topicId,
            () => <String, List<CompletedLesson>>{},
          )
          .putIfAbsent(
            lesson.subtopicId,
            () => <CompletedLesson>[],
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
      _groupLessonsByTopicAndSubtopic();

  return Column(
    children: groupedLessons.entries.map((topicEntry) {
      final topicName = _getTopicName(topicEntry.key);

      final topicLessons = topicEntry.value.values
          .expand((lessons) => lessons)
          .length;

      return Card(
        margin: const EdgeInsets.only(bottom: 12),
        child: ExpansionTile(
          leading: const Icon(Icons.folder),
          title: Text(
            topicName,
            style: const TextStyle(
              fontWeight: FontWeight.bold,
              fontSize: 18,
            ),
          ),
          subtitle: Text('$topicLessons completed lessons'),
          children: topicEntry.value.entries.map((subtopicEntry) {
            final lessons = subtopicEntry.value;

            return Padding(
              padding: const EdgeInsets.only(left: 12),
              child: ExpansionTile(
                leading: const Icon(Icons.folder_open),
                title: Text(
                  _getSubtopicName(subtopicEntry.key),
                ),
                subtitle: Text('${lessons.length} lessons'),
                children: lessons.map((lesson) {
                  return ListTile(
                    contentPadding: const EdgeInsets.only(
                      left: 24,
                      right: 16,
                    ),
                    leading: const Icon(Icons.menu_book),
                    title: Text(lesson.title),
                    trailing: const Icon(Icons.chevron_right),
                    onTap: () {
                      _openLesson(lesson.nodeId);
                    },
                  );
                }).toList(),
              ),
            );
          }).toList(),
        ),
      );
    }).toList(),
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

  String _getSubtopicName(String subtopicId) {
    switch (subtopicId) {
      case 'T001S001':
        return 'Introduction to Budgeting';

      case 'T001S002':
        return 'Needs vs Wants';

      case 'T001S003':
        return 'Creating a Monthly Budget';

      case 'T001S004':
        return 'Tracking Expenses';

      default:
        return subtopicId;
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

  String getMasteryTitle(int masteryScore) {
    switch (masteryScore) {
      case 0:
        return 'Insufficient Data';
      case 1:
        return 'Very Low';
      case 2:
        return 'Low';
      case 3:
        return 'Developing';
      case 4:
        return 'Basic';
      case 5:
        return 'Intermediate';
      case 6:
        return 'Proficient';
      case 7:
        return 'Advanced';
      case 8:
        return 'High Proficiency';
      case 9:
        return 'Near Mastery';
      case 10:
        return 'Mastered';
      default:
        return 'Unknown';
    }
  }
}