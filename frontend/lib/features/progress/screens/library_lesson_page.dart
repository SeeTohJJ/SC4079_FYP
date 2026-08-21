import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/lesson_content.dart';

class LibraryLessonPage extends StatelessWidget {
  final LessonContent lesson;

  const LibraryLessonPage({
    super.key,
    required this.lesson,
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
          crossAxisAlignment:
              CrossAxisAlignment.start,

          children: [
            Text(
              lesson.title,
              style: Theme.of(context)
                  .textTheme
                  .headlineMedium,
            ),

            const SizedBox(height: 24),

            Expanded(
              child: SingleChildScrollView(
                child: Text(
                  lesson.content,
                ),
              ),
            ),

            const SizedBox(height: 16),

            SizedBox(
              width: double.infinity,

              child: ElevatedButton(
                onPressed: () {
                  Navigator.pop(context);
                },

                child: const Text(
                  "Back",
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}