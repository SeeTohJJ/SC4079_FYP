enum NodeType {
  lesson,
  quiz,
  decision,
  reward,
  boss,
}

class StudyNode {
  final String id;
  final String title;
  final NodeType type;
  bool isUnlocked;
  bool isCompleted;
  int energyCost;

  StudyNode({
    required this.id,
    required this.title,
    required this.type,
    this.isUnlocked = false,
    this.isCompleted = false,
    required this.energyCost,
  });

    factory StudyNode.fromJson(Map<String, dynamic> json) {
    return StudyNode(
      id: json['nodeId'],
      title: json['nodeTopic'],
      type: _mapType(json['nodeType']),
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
      default:
        throw Exception("Unknown node type: $type");
    }
  }
}