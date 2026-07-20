import 'package:frontend/features/study/models/quiz_option.dart';

class QuizContent {
  final String nodeId;
  final String title;
  final String question;
  final String optionA;
  final String optionB;
  final String optionC;
  final String optionD;

  QuizContent({
    required this.nodeId,
    required this.title,
    required this.question,
    required this.optionA,
    required this.optionB,
    required this.optionC,
    required this.optionD,
  });

  factory QuizContent.fromJson(Map<String, dynamic> json) {
    return QuizContent(
      nodeId: json["nodeId"],
      title: json["title"],
      question: json["question"],
      optionA: json["optionA"],
      optionB: json["optionB"],
      optionC: json["optionC"],
      optionD: json["optionD"],
    );
  }

  List<QuizOption> get options {
    return [
      QuizOption(id: "A", text: optionA),
      QuizOption(id: "B", text: optionB),
      QuizOption(id: "C", text: optionC),
      QuizOption(id: "D", text: optionD),
    ];
  }
}