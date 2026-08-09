import 'user_plant.dart';

class Garden {
  final int waterCurrency;
  final int coinCurrency;
  final List<UserPlant> plants;

  Garden({
    required this.waterCurrency,
    required this.coinCurrency,
    required this.plants,
  });

  factory Garden.fromJson(
      Map<String, dynamic> json) {

    return Garden(
      waterCurrency:
          json['water'],
      coinCurrency:
          json['coins'],

      plants: (json['plants'] as List)
          .map(
            (plant) =>
                UserPlant.fromJson(plant),
          )
          .toList(),
    );
  }
}