import 'dart:convert';
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

    print(payload);
    
    final response = await http.post(
      Uri.parse("$baseUrl/register"),
      headers: {"Content-Type": "application/json"},
      body: jsonEncode(payload),
    );

    return response.statusCode == 200 || response.statusCode == 201;
  }

  Future<bool> forgotPassword(String email) async {
    final response = await http.post(
      Uri.parse("$baseUrl/forgot-password"),
      headers: {
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "email": email,
      }),
    );

    return response.statusCode == 200;
  }

  Future<void> logout() async {
    await storage.delete(key: "jwt");
  }

  Future<String?> getToken() async {
    return await storage.read(key: "jwt");
  }
  
}