import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';

class RewardNodePage extends StatelessWidget {
  const RewardNodePage({super.key, required StudyNode node});

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      body: const Center(
        child: Text(
          "Welcome to the Reward Node Page!",
          style: TextStyle(fontSize: 18),
        ),
      ),
    );
  }
}