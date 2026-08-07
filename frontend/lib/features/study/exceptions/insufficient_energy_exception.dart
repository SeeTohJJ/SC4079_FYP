class InsufficientEnergyException implements Exception {
  final int currentEnergy;
  final int requiredEnergy;
  final int secondsUntilNextEnergy;

  InsufficientEnergyException({
    required this.currentEnergy,
    required this.requiredEnergy,
    required this.secondsUntilNextEnergy,
  });
}