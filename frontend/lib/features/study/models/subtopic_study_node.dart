import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';

class SubtopicStudyNode {
  final String subtopicId;
  final String subtopicName;
  final String topicName;
  final String nodeTitle;
  final List<StudyNode> activities;

  SubtopicStudyNode({
    required this.subtopicId,
    required this.subtopicName,
    required this.topicName,
    required this.nodeTitle,
    required this.activities,
  });

  int get completedCount {
    return activities.where((activity) => activity.isCompleted).length;
  }

  int get totalCount {
    return activities.length;
  }

  double get progress {
    if (activities.isEmpty) {
      return 0.0;
    }

    return completedCount / totalCount;
  }

  bool get hasNewItems {
    return activities.any(
      (activity) =>
          activity.isUnlocked && !activity.isCompleted,
    );
  }

  bool get isUnlocked {
    return activities.any(
      (activity) => activity.isUnlocked,
    );
  }

  Color get color {
    switch (topicName.toLowerCase()) {
      case 'budgeting':
        return Colors.blue;

      case 'saving':
        return Colors.green;

      case 'investing':
        return Colors.orange;

      case 'credit management':
        return Colors.purple;

      default:
        return Colors.grey;
    }
  }
}