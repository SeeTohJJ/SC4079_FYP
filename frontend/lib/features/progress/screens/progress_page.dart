import 'package:flutter/material.dart';

class ProgressPage extends StatelessWidget {
  const ProgressPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      body: const Center(
        child: Text(
          "Welcome to the Progress Page!",
          style: TextStyle(fontSize: 18),
        ),
      ),
    );
  }
}