import 'package:flutter/material.dart';
import '../models/quiz_result.dart';

class QuizResultDialog extends StatelessWidget {
  final QuizResult result;

  const QuizResultDialog({
    super.key,
    required this.result,
  });

  @override
  Widget build(BuildContext context) {
    return Dialog(
      backgroundColor: Colors.transparent,
      insetPadding: const EdgeInsets.all(20),
      child: Container(
        padding: const EdgeInsets.all(24),
        decoration: BoxDecoration(
          color: const Color.fromARGB(255, 29, 27, 29),
          borderRadius: BorderRadius.circular(24),
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [

            Icon(
              result.correct
                  ? Icons.check_circle
                  : Icons.cancel,
              color: result.correct
                  ? Colors.green
                  : Colors.red,
              size: 80,
            ),

            const SizedBox(height: 16),

            Text(
              result.correct
                  ? "Correct!"
                  : "Incorrect",
              style: const TextStyle(
                fontSize: 26,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 24),

            LinearProgressIndicator(
              value: result.updatedPKnow,
              minHeight: 10,
              borderRadius: BorderRadius.circular(20),
            ),

            const SizedBox(height: 8),

            Text(
              "Topic Mastery: ${(result.updatedPKnow * 100).toInt()}%",
            ),

            const SizedBox(height: 30),

            Text(
              result.feedback,
              textAlign: TextAlign.center,
            ),

            const SizedBox(height: 20),

            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                    backgroundColor: const Color.fromARGB(255, 75, 5, 75),
                  ),
                onPressed: () => Navigator.pop(context),
                child: const Text("Continue"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}