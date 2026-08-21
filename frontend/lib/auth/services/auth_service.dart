import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:frontend/core/storage/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AuthService {
  static const String baseUrl = "http://10.0.2.2:8080/api/auth";

  final storage = const FlutterSecureStorage();

  Future<bool> login(String email, String password) async {
    final response = await http.post(
      Uri.parse("$baseUrl/login"),
      headers: {
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "email": email.toLowerCase().trim(),
        "password": password,
      }),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);

      await storage.write(
        key: "jwt",
        value: data["token"],
      );

      return true;
    }

    return false;
  }

  Future<bool> register(
    String email,
    String password,
    String username,
    String gender,
    int age,
    String employmentStatus,
    int income,
    String country,
    List<String> topicsToLearn,
    
  ) async {

    final payload = {
      "email": email.toLowerCase().trim(),
      "password": password,
      "username": username,
      "gender": gender,
      "employmentStatus": employmentStatus,
      "age": age,
      "income": income,
      "country": country,
      "topics": topicsToLearn,
    };
    
    final response = await http.post(
      Uri.parse("$baseUrl/register"),
      headers: {"Content-Type": "application/json"},
      body: jsonEncode(payload),
    );

    return response.statusCode == 200 || response.statusCode == 201;
  }

  Future<void> logout() async {
    await storage.delete(key: "jwt");
  }

  Future<String?> getToken() async {
    return await storage.read(key: "jwt");
  }

  Future<void> forgotPassword(String email) async {
    final response = await http.post(
      Uri.parse("$baseUrl/forgot-password"),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': email}),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to send reset code');
    }
  }
  
  Future<String> verifyResetOtp(String email, String otp) async {
    final response = await http.post(
      Uri.parse("$baseUrl/verify-reset-otp"),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': email, 'otp': otp}),
    );

    if (response.statusCode != 200) {
      throw Exception('Invalid OTP');
    }

    final data = jsonDecode(response.body);

    return data['resetToken'];
  }

  Future<void> resetPassword({required String resetToken, required String newPassword}) async {
    final response = await http.post(
      Uri.parse("$baseUrl/reset-password"),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'resetToken': resetToken, 'newPassword': newPassword}),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to reset password');
    }
  }

  Future<void> changePassword({required String currentPassword, required String newPassword}) async {

    final token = await SecureStorageService().getToken();

    final response = await http.post(
      Uri.parse(
        '$baseUrl/change-password',
      ),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
      body: jsonEncode({
        'currentPassword': currentPassword,
        'newPassword': newPassword,
      }),
    );

    debugPrint('CHANGE PASSWORD STATUS: ${response.statusCode}');
    debugPrint('CHANGE PASSWORD RESPONSE: ${response.body}');

    if (response.statusCode != 200) {
      throw Exception(
        response.body.isNotEmpty
            ? response.body
            : 'Failed to change password',
      );
    }
  }
}