import 'user_plant.dart';

class Garden {
  final int waterCurrency;
  final List<UserPlant> plants;

  Garden({
    required this.waterCurrency,
    required this.plants,
  });

  factory Garden.fromJson(
      Map<String, dynamic> json) {

    return Garden(
      waterCurrency:
          json['waterCurrency'],

      plants: (json['plants'] as List)
          .map(
            (plant) =>
                UserPlant.fromJson(plant),
          )
          .toList(),
    );
  }
}