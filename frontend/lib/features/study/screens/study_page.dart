import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';
import 'package:frontend/features/study/screens/nodes/boss_node_page.dart';
import 'package:frontend/features/study/screens/nodes/decision_node_page.dart';
import 'package:frontend/features/study/screens/nodes/lesson_node_page.dart';
import 'package:frontend/features/study/screens/nodes/quiz_node_page.dart';
import 'package:frontend/features/study/screens/nodes/reward_node_page.dart';
import 'package:frontend/features/study/widgets/study_line_painter.dart';
  
class StudyPage extends StatefulWidget {
  const StudyPage({super.key});

  @override
  State<StudyPage> createState() => _StudyPageState();
}

class _StudyPageState extends State<StudyPage> {

  late List<StudyNode> nodes;
  
  @override
  void initState() {
    super.initState();
    nodes = _mockNodes();
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

  // 🧪 Mock data (replace later with backend/service)
  List<StudyNode> _mockNodes() {
    return [
      StudyNode(
        id: '1',
        title: 'What is budgeting?',
        type: NodeType.lesson,
        isUnlocked: true,
        isCompleted: false,
      ),
      StudyNode(
        id: '2',
        title: 'Budgeting Quiz',
        type: NodeType.quiz,
        isUnlocked: true,
        isCompleted: false,
      ),
      StudyNode(
        id: '3',
        title: 'Spending Decision',
        type: NodeType.decision,
        isUnlocked: true,
        isCompleted: false,
      ),
      StudyNode(
        id: '4',
        title: 'Bonus Rewards',
        type: NodeType.lesson,
        isUnlocked: true,
        isCompleted: false,
      ),
      StudyNode(
        id: '5',
        title: 'What is budgeting?',
        type: NodeType.lesson,
        isUnlocked: false,
        isCompleted: false,
      ),
      StudyNode(
        id: '6',
        title: 'Budgeting Quiz',
        type: NodeType.quiz,
        isUnlocked: false,
        isCompleted: false,
      ),
      StudyNode(
        id: '7',
        title: 'Spending Decision',
        type: NodeType.decision,
        isUnlocked: false,
        isCompleted: false,
      ),
      StudyNode(
        id: '8',
        title: 'Bonus Rewards',
        type: NodeType.reward,
        isUnlocked: false,
        isCompleted: false,
      ),
    ];
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

  // 🎮 TAP HANDLER
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
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => LessonNodePage(
              node: node,
              onComplete: () {
                _completeNode(node.id);
              },
            ),
          ),
        );
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

  void _completeNode(String nodeId) {
    final index = nodes.indexWhere((n) => n.id == nodeId);

    if (index == -1) return;

    setState(() {
      nodes[index].isCompleted = true;

      if (index + 1 < nodes.length) {
        nodes[index + 1].isUnlocked = true;
      }
    });
  }
}