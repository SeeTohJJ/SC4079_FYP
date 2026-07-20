import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/lesson_content.dart';

class LessonNodePage extends StatelessWidget {
  final LessonContent lesson;
  final Future<void> Function()? onComplete;

  const LessonNodePage({
    super.key,
    required this.lesson,
    this.onComplete,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(lesson.title),
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
                onPressed: () {
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