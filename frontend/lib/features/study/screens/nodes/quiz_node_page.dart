import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/quiz_content.dart';
import 'package:frontend/features/study/models/quiz_option.dart';
import 'package:frontend/features/study/services/study_service.dart';

class QuizNodePage extends StatefulWidget {
  final String nodeId;
  final QuizContent quiz;

  const QuizNodePage({
    super.key,
    required this.nodeId,
    required this.quiz,
  });

  @override
  State<QuizNodePage> createState() => _QuizNodePageState();
}

class _QuizNodePageState extends State<QuizNodePage> {
  final studyService = StudyService();


  String? selectedOptionId;
  late final Stopwatch stopwatch;
  late List<QuizOption> options;

  @override
  void initState() {
    super.initState();
    stopwatch = Stopwatch()..start();

    options = List<QuizOption>.from(widget.quiz.options);
    options.shuffle();
  }

  Future<void> submit() async {
    if (selectedOptionId == null) return;

    stopwatch.stop();

    try {
      await studyService.submitQuiz(
        nodeId: widget.nodeId,
        optionSelected: selectedOptionId!,
        timeTaken: stopwatch.elapsed.inSeconds,
      );

      if (!mounted) return;

      Navigator.pop(context, true);
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text("Failed to submit quiz: $e"),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    final quiz = widget.quiz;

    return Scaffold(
      appBar: AppBar(
        title: Text(quiz.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              quiz.question,
              style: Theme.of(context).textTheme.headlineSmall,
            ),

            const SizedBox(height: 24),

            ...List.generate(options.length, (index) {
              final option = options[index];

              return RadioListTile<String>(
                title: Text(option.text),
                value: option.id,
                groupValue: selectedOptionId,
                onChanged: (value) {
                  setState(() {
                    selectedOptionId = value;
                  });
                },
              );
            }),

            const Spacer(),

            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: selectedOptionId == null ? null : submit,
                child: const Text("Submit Quiz"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}