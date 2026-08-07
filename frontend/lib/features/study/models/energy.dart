class Energy {
  final int currentEnergy;
  final int maxEnergy;
  final int secondsUntilNextEnergy;

  Energy({
    required this.currentEnergy,
    required this.maxEnergy,
    required this.secondsUntilNextEnergy,
  });

  factory Energy.fromJson(Map<String, dynamic> json) {
    return Energy(
      currentEnergy: json['currentEnergy'],
      maxEnergy: json['maxEnergy'],
      secondsUntilNextEnergy:
          json['secondsUntilNextEnergy'],
    );
  }
}