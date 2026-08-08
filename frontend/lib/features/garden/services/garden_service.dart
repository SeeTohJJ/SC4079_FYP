import 'dart:convert';

import 'package:frontend/features/garden/models/user_plant.dart';
import 'package:http/http.dart' as http;

import 'package:frontend/auth/services/auth_services.dart';
import 'package:frontend/features/garden/models/garden.dart';

class GardenService {
  static const String baseUrl =
      'http://10.0.2.2:8080/api/garden';

  final AuthService authService = AuthService();

  Future<Garden> getGarden() async {
    final token =
        await authService.getToken();

    final response = await http.get(
      Uri.parse(baseUrl),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode != 200) {
      throw Exception(
        'Failed to load garden',
      );
    }

    return Garden.fromJson(
      jsonDecode(response.body),
    );
  }

  Future<UserPlant> waterPlant(int plantId) async {

    final token =
        await authService.getToken();

    final response = await http.post(
      Uri.parse(
        '$baseUrl/plants/$plantId/water',
      ),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 200) {
      return UserPlant.fromJson(
        jsonDecode(response.body),
      );
    }

    if (response.statusCode == 409) {
      throw Exception(
        'INSUFFICIENT_WATER_CURRENCY',
      );
    }

    throw Exception(
      'Failed to water plant',
    );
  }
}