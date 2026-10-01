import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/lesson_content.dart';
import 'package:frontend/features/study/services/study_service.dart';

final studyService = StudyService();

class LessonNodePage extends StatelessWidget {
  final LessonContent lesson;
  final String nodeId;
  final bool reviewMode;
  
  const LessonNodePage({
    super.key,
    required this.lesson,
    required this.nodeId,
    this.reviewMode = false,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Lesson"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              lesson.title,
              style: Theme.of(context).textTheme.headlineMedium,
            ),

            const SizedBox(height: 24),

            Text(
              lesson.content,
            ),

            const Spacer(),

            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
              style: ElevatedButton.styleFrom( 
                backgroundColor: Theme.of(context).colorScheme.primary, 
                foregroundColor: Colors.black, 
                ),
              onPressed: () async {

                await studyService.submitLesson(nodeId);

                if (!context.mounted) return;

                Navigator.pop(context, true);
              },
              child: const Text("Complete Lesson"),
            )
            ),
          ],
        ),
      ),
    );
  }
}