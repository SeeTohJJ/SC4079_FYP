import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/subtopic_study_node.dart';

class StudyPathPainter extends CustomPainter {
  final List<SubtopicStudyNode> nodes;
  final double Function(int) getX;
  final double Function(int) getY;

  StudyPathPainter({
    required this.nodes,
    required this.getX,
    required this.getY,
  });

  @override
  void paint(Canvas canvas, Size size) {
    for (int i = 0; i < nodes.length - 1; i++) {
      final current = nodes[i];
      final next = nodes[i + 1];

      if (!current.isUnlocked || !next.isUnlocked) {
        continue;
      }

      final Paint paint;

      if (current.progress >= 1.0) {
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
        getX(i) + 43,
        getY(i) + 43,
      );

      final end = Offset(
        getX(i + 1) + 43,
        getY(i + 1) + 43,
      );

      canvas.drawLine(start, end, paint);
    }
  }

  @override
  bool shouldRepaint(covariant StudyPathPainter oldDelegate) {
    return true;
  }
}