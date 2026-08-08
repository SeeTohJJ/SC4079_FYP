class UserPlant {
  final int plantId;
  final String topicId;
  final String topicName;
  final String plantType;

  final int growthStage;
  final int maxGrowthStage;

  final double growthProgress;

  final int happiness;
  final double masteryScore;

  final bool canWater;

  UserPlant({
    required this.plantId,
    required this.topicId,
    required this.topicName,
    required this.plantType,
    required this.growthStage,
    required this.maxGrowthStage,
    required this.growthProgress,
    required this.happiness,
    required this.masteryScore,
    required this.canWater,
  });

  factory UserPlant.fromJson(
      Map<String, dynamic> json) {
    return UserPlant(
      plantId: json['plantId'],
      topicId: json['topicId'],
      topicName: json['topicName'],
      plantType: json['plantType'],

      growthStage: json['growthStage'],
      maxGrowthStage: json['maxGrowthStage'],

      growthProgress:
          (json['growthProgress'] as num).toDouble(),

      happiness: json['happiness'],

      masteryScore:
          (json['masteryScore'] as num).toDouble(),

      canWater: json['canWater'],
    );
  }
}