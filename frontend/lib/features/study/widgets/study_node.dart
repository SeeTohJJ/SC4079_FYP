import 'package:flutter/material.dart';
import '../models/study_node.dart';

class StudyNodeWidget extends StatelessWidget {
  final StudyNode node;

  const StudyNodeWidget({super.key, required this.node});

  @override
  Widget build(BuildContext context) {
    Color color;

    switch (node.type) {
      case NodeType.lesson:
        color = Colors.blue;
        break;
      case NodeType.quiz:
        color = Colors.orange;
        break;
      case NodeType.decision:
        color = Colors.purple;
        break;
      case NodeType.reward:
        color = Colors.green;
        break;
      case NodeType.boss:
        color = Colors.red;
        break;
    }

    return GestureDetector(
      onTap: node.isUnlocked
          ? () {
              // TODO: navigate to node detail
            }
          : null,
      child: Opacity(
        opacity: node.isUnlocked ? 1 : 0.3,
        child: Container(
          margin: const EdgeInsets.symmetric(vertical: 10),
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: color.withOpacity(0.2),
            borderRadius: BorderRadius.circular(16),
            border: Border.all(color: color),
          ),
          child: Row(
            children: [
              Icon(_getIcon(node.type), color: color),
              const SizedBox(width: 12),
              Text(node.title),
              const Spacer(),
              if (node.isCompleted)
                const Icon(Icons.check, color: Colors.green),
            ],
          ),
        ),
      ),
    );
  }

  IconData _getIcon(NodeType type) {
    switch (type) {
      case NodeType.lesson:
        return Icons.menu_book;
      case NodeType.quiz:
        return Icons.quiz;
      case NodeType.decision:
        return Icons.alt_route;
      case NodeType.reward:
        return Icons.monetization_on;
      case NodeType.boss:
        return Icons.emoji_events;
    }
  }
}