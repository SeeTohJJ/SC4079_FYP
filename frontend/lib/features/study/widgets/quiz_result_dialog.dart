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
            // Result icon
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

            // Result title
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

            // Mastery title
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  "Mastery",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                Text(
                  getMasteryTitle(result.updatedMastery),
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),

            const SizedBox(height: 10),

            Row(
              children: List.generate(
                10,
                (index) {
                  final isFilled =
                      result.updatedMastery > index;

                  return Expanded(
                    child: Container(
                      height: 10,
                      margin: EdgeInsets.only(
                        right: index == 9 ? 0 : 2,
                      ),
                      decoration: BoxDecoration(
                        color: isFilled
                            ? Theme.of(context)
                                .colorScheme
                                .primary
                            : Colors.grey.shade800,
                        borderRadius:
                            BorderRadius.circular(3),
                      ),
                    ),
                  );
                },
              ),
            ),

            const SizedBox(height: 8),

            // Mastery score / insufficient data
            Text(
              result.updatedMastery == 0
                  ? "Keep learning to establish mastery"
                  : "Mastery Level "
                      "${result.updatedMastery}/10",
              style: TextStyle(
                fontSize: 13,
                color: Colors.grey.shade400,
              ),
            ),

            const SizedBox(height: 24),

            // Water reward
            Container(
              padding: const EdgeInsets.symmetric(
                horizontal: 20,
                vertical: 12,
              ),
              decoration: BoxDecoration(
                color: Colors.blue.withOpacity(0.15),
                borderRadius: BorderRadius.circular(16),
              ),
              child: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const Icon(
                    Icons.water_drop,
                    color: Colors.lightBlue,
                    size: 28,
                  ),

                  const SizedBox(width: 8),

                  Text(
                    "+${result.waterReward} Water",
                    style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 24),

            // Feedback
            Text(
              result.feedback,
              textAlign: TextAlign.center,
            ),

            const SizedBox(height: 20),

            // Continue button
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor:
                      const Color.fromARGB(
                    255,
                    75,
                    5,
                    75,
                  ),
                ),
                onPressed: () =>
                    Navigator.pop(context),
                child: const Text("Continue"),
              ),
            ),
          ],
        ),
      ),
    );
  }

  String getMasteryTitle(int masteryScore) {
    switch (masteryScore) {
      case 0:
        return "Building Knowledge";
      case 1:
        return "Very Low";
      case 2:
        return "Low";
      case 3:
        return "Developing";
      case 4:
        return "Basic";
      case 5:
        return "Intermediate";
      case 6:
        return "Proficient";
      case 7:
        return "Advanced";
      case 8:
        return "High Proficiency";
      case 9:
        return "Near Mastery";
      case 10:
        return "Mastered";
      default:
        return "Unknown";
    }
  }
}