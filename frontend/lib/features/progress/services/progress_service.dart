import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

import 'package:frontend/auth/services/auth_services.dart';
import 'package:frontend/features/progress/models/progress.dart';

class ProgressService {

  static const String baseUrl =
      'http://10.0.2.2:8080/api/progress';

  final AuthService authService =
      AuthService();

  Future<Progress> getProgress() async {

    final token =
        await authService.getToken();

    final response = await http.get(
      Uri.parse('$baseUrl/get_progress'),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    debugPrint(
      'Progress response: ${response.statusCode}',
    );

    if (response.statusCode != 200) {
      throw Exception(
        'Failed to load progress',
      );
    }

    return Progress.fromJson(
      jsonDecode(response.body),
    );
  }
}