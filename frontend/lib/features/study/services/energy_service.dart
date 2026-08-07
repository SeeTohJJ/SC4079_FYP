import 'dart:convert';

import 'package:frontend/auth/services/auth_services.dart';
import 'package:frontend/features/study/models/energy.dart';
import 'package:http/http.dart' as http;


class EnergyService {
    static const String baseUrl = "http://10.0.2.2:8080/api/energy";

    final AuthService authService = AuthService();

    Future<Energy> getEnergy() async {
        final token = await authService.getToken();

        final response = await http.get(
          Uri.parse('$baseUrl/get_energy'),
          headers: {
            'Authorization': 'Bearer $token',
            'Content-Type': 'application/json',
          },
        );

        if (response.statusCode != 200) {
          throw Exception('Failed to load energy');
        }

        return Energy.fromJson(
          jsonDecode(response.body),
        );
      }
      
  }