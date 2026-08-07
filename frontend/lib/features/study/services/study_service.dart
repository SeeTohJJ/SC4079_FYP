import 'dart:convert';
import 'package:frontend/features/study/exceptions/insufficient_energy_exception.dart';
import 'package:frontend/features/study/models/lesson_content.dart';
import 'package:frontend/features/study/models/quiz_content.dart';
import 'package:frontend/features/study/models/quiz_result.dart';
import 'package:http/http.dart' as http;
import 'package:frontend/auth/services/auth_services.dart';

class StudyService {
  static const String baseUrl = "http://10.0.2.2:8080/api/study";

  final AuthService authService = AuthService();

  Future<List<dynamic>> getStudyPathNodes(String token) async {

    final response = await http.post(
      Uri.parse(
        '$baseUrl/GetStudyPathNodes',
      ),
      headers: {
        'Authorization': 'Bearer $token',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    }

    throw Exception('Failed to load study path');
  }

  Future<void> completeNode(String nodeId) async {
    final token = await authService.getToken();

    if (token == null) {
      throw Exception("Not logged in");
    }

    final response = await http.post(
      Uri.parse("$baseUrl/api/study/completeNode"),
      headers: {
        "Authorization": "Bearer $token",
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "nodeId": nodeId,
      }),
    );

    if (response.statusCode != 200) {
      throw Exception("Failed to complete node");
    }
  }

  Future<LessonContent> getLessonContent(String nodeId) async {
    final token = await authService.getToken();

    if (token == null) {
      throw Exception("Not logged in");
    }

    final response = await http.post(
      Uri.parse('$baseUrl/GetLessonContent'),
      headers: {
        "Authorization": "Bearer $token",
        'Content-Type': 'application/json',
      },
      body: jsonEncode({
        'nodeId': nodeId,
      }),
    );

    if (response.statusCode == 200) {
      return LessonContent.fromJson(jsonDecode(response.body));
    }

    if (response.statusCode == 409) {

      final data = jsonDecode(response.body);

      if (data['error'] == 'INSUFFICIENT_ENERGY') {

        throw InsufficientEnergyException(
          currentEnergy: data['currentEnergy'],
          requiredEnergy: data['requiredEnergy'],
          secondsUntilNextEnergy:
              data['secondsUntilNextEnergy'],
        );
      }
    }

    throw Exception('Failed to load lesson content');
  }

  Future<void> submitLesson(String nodeId) async {
    final token = await authService.getToken();

    if (token == null) {
      throw Exception("Not logged in");
    }

    final response = await http.post(
      Uri.parse("$baseUrl/SubmitLesson"),
      headers: {
        "Authorization": "Bearer $token",
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "nodeId": nodeId,
      }),
    );

    if (response.statusCode != 200) {
      throw Exception("Failed to submit lesson");
    }
  }

  Future<QuizContent> getQuizContent(String nodeId) async {
    final token = await authService.getToken();

    if (token == null) {
      throw Exception("Not logged in");
    }

    final response = await http.post(
      Uri.parse("$baseUrl/GetQuizContent"),
      headers: {
        "Authorization": "Bearer $token",
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "nodeId": nodeId,
      }),
    );

    if (response.statusCode != 200) {
      throw Exception("Failed to load quiz");
    }

    return QuizContent.fromJson(jsonDecode(response.body));
  }

    Future<QuizResult> submitQuiz({required String nodeId, required String optionSelected, required int timeTaken, required bool hintUsed}) async {
    final token = await authService.getToken();

    if (token == null) {
      throw Exception("Not logged in");
    }

    final response = await http.post(
      Uri.parse("$baseUrl/SubmitQuiz"),
      headers: {
        "Authorization": "Bearer $token",
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "nodeId": nodeId,
        "optionSelected": optionSelected,
        "timeTaken": timeTaken,
      }),
    );

    if (response.statusCode != 200) {
      throw Exception("Failed to submit quiz");
    }

    print("Response body: ${response.body}");

    return QuizResult.fromJson(jsonDecode(response.body));
  }

  
}