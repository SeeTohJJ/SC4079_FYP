class UserPlant {
  final String topicId;
  final String topicName;
  final String plantType;
  final double growth;
  final double maxGrowth;
  final int happiness;
  final String stage;
  final double mastery;

  UserPlant({
    required this.topicId,
    required this.topicName,
    required this.plantType,
    required this.growth,
    required this.maxGrowth,
    required this.happiness,
    required this.stage,
    required this.mastery,
  });

  factory UserPlant.fromJson(
      Map<String, dynamic> json) {
    return UserPlant(
      topicId: json['topicId'],
      topicName: json['topicName'],
      plantType: json['plantType'],

      growth: json['growth'],
      maxGrowth: json['maxGrowth'],
      happiness: json['happiness'],
      stage: json['stage'],
      mastery: json['pKnow']
    );
  }
}