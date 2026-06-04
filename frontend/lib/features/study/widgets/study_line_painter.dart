import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';

class StudyPathPainter extends CustomPainter {
  final List<StudyNode> nodes;
  final double Function(int) getX;
  final double Function(int) getY;

  StudyPathPainter({
    required this.nodes,
    required this.getX,
    required this.getY,
  });

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..color = Colors.grey.shade400
      ..strokeWidth = 4
      ..strokeCap = StrokeCap.round;

    for (int i = 0; i < nodes.length - 1; i++) {
      // Skip if either node is locked
      if (!nodes[i].isUnlocked || !nodes[i + 1].isUnlocked) {
        continue;
      }

      Paint paint;

      if (nodes[i].isCompleted) {
        paint = Paint()
          ..color = Colors.green
          ..strokeWidth = 5
          ..strokeCap = StrokeCap.round;
      } else {
        paint = Paint()
          ..color = Colors.grey.shade400
          ..strokeWidth = 5
          ..strokeCap = StrokeCap.round;
      }

      final start = Offset(
        getX(i) + 35,
        getY(i) + 35,
      );

      final end = Offset(
        getX(i + 1) + 35,
        getY(i + 1) + 35,
      );

      canvas.drawLine(start, end, paint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}