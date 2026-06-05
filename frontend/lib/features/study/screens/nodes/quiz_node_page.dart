import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';
import 'package:frontend/features/study/models/quiz_question.dart';

class QuizNodePage extends StatefulWidget {
  final StudyNode node;

  const QuizNodePage({super.key, required this.node});

  @override
  State<QuizNodePage> createState() => _QuizNodePageState();
}

class _QuizNodePageState extends State<QuizNodePage> {
  int? selectedIndex;

  final question = QuizQuestion(
    question: "What is budgeting?",
    options: [
      "Spending all your money",
      "Planning how to use your money",
      "Borrowing money",
      "Ignoring expenses",
    ],
    correctIndex: 1,
  );

  void submit() {
    final isCorrect = selectedIndex == question.correctIndex;

    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text(isCorrect ? "Correct 🎉" : "Wrong ❌"),
        content: Text(isCorrect
            ? "You earned rewards!"
            : "Try again next time."),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context);
              Navigator.pop(context, isCorrect); // return result
            },
            child: const Text("Continue"),
          )
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(widget.node.title)),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Text(
              question.question,
              style: const TextStyle(fontSize: 20),
            ),
            const SizedBox(height: 20),

            ...List.generate(question.options.length, (i) {
              return RadioListTile(
                title: Text(question.options[i]),
                value: i,
                groupValue: selectedIndex,
                onChanged: (value) {
                  setState(() {
                    selectedIndex = value;
                  });
                },
              );
            }),

            const Spacer(),

            ElevatedButton(
              onPressed: selectedIndex == null ? null : submit,
              child: const Text("Submit"),
            ),
          ],
        ),
      ),
    );
  }
}