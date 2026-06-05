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

  StudyNode({
    required this.id,
    required this.title,
    required this.type,
    this.isUnlocked = false,
    this.isCompleted = false,
  });
}