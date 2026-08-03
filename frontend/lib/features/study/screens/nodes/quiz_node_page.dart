import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/quiz_content.dart';
import 'package:frontend/features/study/models/quiz_option.dart';
import 'package:frontend/features/study/services/study_service.dart';
import 'package:frontend/features/study/widgets/quiz_result_dialog.dart';

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
  bool showHint = false;
  bool hintUsed = false;

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
      final result = await studyService.submitQuiz(
        nodeId: widget.nodeId,
        optionSelected: selectedOptionId!,
        timeTaken: stopwatch.elapsed.inSeconds,
        hintUsed: hintUsed,
      );

      if (!mounted) return;

      await showDialog(
        context: context,
        barrierDismissible: false,
        builder: (_) => QuizResultDialog(result: result),
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

            const SizedBox(height: 12),

            Align(
              alignment: Alignment.centerLeft,
              child: TextButton.icon(
                icon: Icon(
                  showHint ? Icons.visibility_off : Icons.lightbulb_outline,
                  color: Colors.amber,
                ),
                label: Text(
                  showHint ? "Hide Hint" : "Show Hint",
                ),
                onPressed: () {
                  setState(() {
                    showHint = !showHint;
                    if (showHint) {
                      hintUsed = true;
                    }
                  });
                },
              ),
            ),

            if (showHint)
              Container(
                width: double.infinity,
                margin: const EdgeInsets.only(bottom: 20),
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.amber.shade50,
                  borderRadius: BorderRadius.circular(12),
                  border: Border.all(
                    color: Colors.amber,
                  ),
                ),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Icon(
                      Icons.lightbulb,
                      color: Colors.amber,
                    ),
                    const SizedBox(width: 12),
                    Expanded(
                      child: Text(
                        quiz.hint ?? "No hint available.",
                        style: const TextStyle(fontSize: 15, color: Colors.black87),
                      ),
                    ),
                  ],
                ),
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
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color.fromARGB(255, 75, 5, 75),
                ),
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