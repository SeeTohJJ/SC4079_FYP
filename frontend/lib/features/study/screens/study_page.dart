import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';
import 'package:frontend/features/study/screens/nodes/boss_node_page.dart';
import 'package:frontend/features/study/screens/nodes/decision_node_page.dart';
import 'package:frontend/features/study/screens/nodes/lesson_node_page.dart';
import 'package:frontend/features/study/screens/nodes/quiz_node_page.dart';
import 'package:frontend/features/study/screens/nodes/reward_node_page.dart';
import 'package:frontend/features/study/widgets/study_line_painter.dart';
import 'package:frontend/features/study/services/study_service.dart';
import 'package:frontend/auth/services/auth_services.dart';
  
class StudyPage extends StatefulWidget {
  const StudyPage({super.key});

  @override
  State<StudyPage> createState() => _StudyPageState();
}

class _StudyPageState extends State<StudyPage> {

  final authService = AuthService();
  final studyService = StudyService();

  late Future<String?> token;
  late List<StudyNode> nodes;
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    loadNodes();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(title: const Text("Study Path")),
      body: SingleChildScrollView(
        child: SizedBox(
          height: 1200, // temporary fixed height for debugging
          width: double.infinity,
          child: Stack(
            children: [
              Positioned.fill(
                    child: CustomPaint(
                      painter: StudyPathPainter(
                        nodes: nodes,
                        getX: (i) => _getX(i, context),
                        getY: (i) => _getY(i),
                      ),
                    ),
                  ),

                  ...nodes.asMap().entries.map((entry) {
                    final index = entry.key;
                    final node = entry.value;

                    return Positioned(
                      left: _getX(index, context),
                      top: _getY(index),
                      child: GestureDetector(
                        onTap: node.isUnlocked
                            ? () => _onNodeTap(context, node)
                            : null,
                        child: _buildNode(node),
                      ),
                    );
                  }),
            ],
          ),
        ),
      ),
    );
  }

  double _getX(int index, BuildContext context) {
    final width = MediaQuery.of(context).size.width;

    const offsets = [0, 60, 90, 60, 0, -60, -90, -60];

    return width / 2 - 35 + offsets[index % offsets.length];
  }

  double _getY(int index) {
    return index * 120.0;
  }

  Future<void> loadNodes() async {
    final token = await authService.getToken();

    if (token == null) {
      throw Exception("User not logged in");
    }

    final result = await studyService.getStudyPathNodes(token);

    final mapped = result
    .map((json) => StudyNode.fromJson(json))
    .toList();

    setState(() {
      nodes = mapped;
      isLoading = false;
    });
  }

  Widget _buildNode(StudyNode node) {
    late Color color;

    if (node.isCompleted) {
      color = Colors.green;
    } else {
      switch (node.type) {
        case NodeType.lesson:
          color = Colors.blue;
          break;
        case NodeType.quiz:
          color = Colors.orange;
          break;
        case NodeType.decision:
          color = Colors.purple;
          break;
        case NodeType.reward:
          color = Colors.yellow;
          break;
        case NodeType.boss:
          color = Colors.red;
          break;
      }
    }

    return Opacity(
      opacity: node.isUnlocked ? 1 : 0.3,
      child: Container(
        width: 70,
        height: 70,
        decoration: BoxDecoration(
          shape: BoxShape.circle,
          color: node.isCompleted ? color : Colors.white,
          border: Border.all(color: color, width: 3),
        ),
        child: Center(
          child: Icon(
            _getIcon(node.type),
            color: node.isCompleted ? Colors.white : color,
          ),
        ),
      ),
    );
  }

  void _onNodeTap(BuildContext context, StudyNode node) {
    showModalBottomSheet(
      context: context,
      builder: (_) {
        return Padding(
          padding: const EdgeInsets.all(20),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                node.title,
                style: const TextStyle(fontSize: 18),
              ),
              const SizedBox(height: 10),
              Text("Type: ${node.type}"),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  Navigator.pop(context);
                  _openNode(context, node);
                },
                child: const Text("Start"),
              ),
            ],
          ),
        );
      },
    );
  }

  IconData _getIcon(NodeType type) {
    switch (type) {
      case NodeType.lesson:
        return Icons.menu_book;
      case NodeType.quiz:
        return Icons.quiz;
      case NodeType.decision:
        return Icons.alt_route;
      case NodeType.reward:
        return Icons.monetization_on;
      case NodeType.boss:
        return Icons.emoji_events;
    }
  }

  Future<void> _openNode(BuildContext context, StudyNode node) async {
    switch (node.type) {

      case NodeType.lesson:

        final lesson = await studyService.getLessonContent(node.id);

        if (!mounted) return;

        final result = await Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => LessonNodePage(
              lesson: lesson,
              nodeId: node.id,
            ),
          ),
        );

        if (!mounted) return;

        if (result == true) {
          await loadNodes();
        }

        break;

      case NodeType.quiz:
        final result = await Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => QuizNodePage(node: node),
          ),
        );

        if (result == true) {
          _completeNode(node.id);
        }

        break;

      case NodeType.decision:
        final result = await Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => DecisionNodePage(node: node),
          ),
        );

        if (result == true) {
          _completeNode(node.id);
        }

        break;

      case NodeType.reward:
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => RewardNodePage(node: node),
          ),
        );
        break;
    case NodeType.boss:
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => BossNodePage(node: node),
        ),
      );
      break;
    }
  }

  Future<void> _completeNode(String nodeId) async {
    try {
      await studyService.completeNode(nodeId);

      // Reload from backend so UI stays in sync
      await loadNodes();

    } catch (e) {
      debugPrint(e.toString());
    }
  }
}