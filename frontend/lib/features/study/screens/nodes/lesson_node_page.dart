import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';

class LessonNodePage extends StatelessWidget {

  final StudyNode node;
  final VoidCallback? onComplete;

  const LessonNodePage({super.key, required this.node, this.onComplete});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(node.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              node.title,
              style: Theme.of(context).textTheme.headlineMedium,
            ),

            const SizedBox(height: 24),

            const Text(
                            '''
              Budgeting is the process of planning how you will spend your income.

              A budget helps you:
              • Control spending
              • Save money
              • Reach financial goals
              • Avoid unnecessary debt
              ''',
            ),

            const Spacer(),

            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () {
                  onComplete?.call();

                  Navigator.pop(context);
                },
                child: const Text("Complete Lesson"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}