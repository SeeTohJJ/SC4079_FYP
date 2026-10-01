enum NodeType {
  lesson,
  quiz,
  decision,
  reward,
  boss,
  review,
  generated,
}

class StudyNode {
  final String id;
  final String nodeTopic;
  final NodeType type;

  final String subtopicId;
  final String subtopicName;
  final String topicName;
  final String nodeTitle;

  bool isUnlocked;
  bool isCompleted;
  int energyCost;

  StudyNode({
    required this.id,
    required this.nodeTopic,
    required this.type,
    required this.subtopicId,
    required this.subtopicName,
    required this.topicName,
    required this.nodeTitle,
    required this.isCompleted,
    required this.isUnlocked,
    required this.energyCost,
  });

  factory StudyNode.fromJson(Map<String, dynamic> json) {
    return StudyNode(
      id: json['nodeId'],
      nodeTopic: json['nodeTopic'],
      type: _mapType(json['nodeType']),
      subtopicId: json['subtopicId'],
      subtopicName: json['subtopicName'],
      topicName: json['topicName'],
      nodeTitle: json['title'],
      isUnlocked: json['unlocked'] ?? false,
      isCompleted: json['completed'] ?? false,
      energyCost: json['energyCost'] ?? 0,
    );
  }

  static NodeType _mapType(String type) {
    switch (type) {
      case 'LESSON':
        return NodeType.lesson;
      case 'QUIZ':
        return NodeType.quiz;
      case 'DECISION':
        return NodeType.decision;
      case 'REWARD':
        return NodeType.reward;
      case 'BOSS':
        return NodeType.boss;
      case 'REVIEW':
        return NodeType.review;
      case 'GENERATED':
        return NodeType.generated;
      default:
        throw Exception("Unknown node type: $type");
    }
  }
}