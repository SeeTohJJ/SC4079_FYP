import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';

class DecisionNodePage extends StatelessWidget {
  final StudyNode node;

  const DecisionNodePage({super.key, required this.node});

  void _makeChoice(BuildContext context, String choice, int reward) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text("Outcome"),
        content: Text(
          "$choice\n\nYou gained $reward AP",
        ),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context);
              Navigator.pop(context, true); // completed
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
      appBar: AppBar(title: Text(node.title)),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            const Text(
              "You receive 2000 monthly income. What do you do?",
              style: TextStyle(fontSize: 18),
            ),

            const SizedBox(height: 30),

            ElevatedButton(
              onPressed: () =>
                  _makeChoice(context, "Spend everything", 0),
              child: const Text("Spend everything"),
            ),

            ElevatedButton(
              onPressed: () =>
                  _makeChoice(context, "Save 20%", 10),
              child: const Text("Save 20%"),
            ),

            ElevatedButton(
              onPressed: () =>
                  _makeChoice(context, "Invest 30%", 20),
              child: const Text("Invest 30%"),
            ),
          ],
        ),
      ),
    );
  }
}