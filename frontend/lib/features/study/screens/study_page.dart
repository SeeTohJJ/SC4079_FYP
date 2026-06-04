import 'package:flutter/material.dart';
import 'package:frontend/features/study/models/study_node.dart';
import 'package:frontend/features/study/widgets/study_line_painter.dart';
  
class StudyPage extends StatelessWidget {
  const StudyPage({super.key});

  @override
  Widget build(BuildContext context) {
    final nodes = _mockNodes();

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

    return width / 2 + offsets[index % offsets.length];
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
        isCompleted: true,
      ),
      StudyNode(
        id: '2',
        title: 'Budgeting Quiz',
        type: NodeType.quiz,
        isUnlocked: true,
        isCompleted: true,
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
        type: NodeType.reward,
        isUnlocked: true,
        isCompleted: false,
      ),
            StudyNode(
        id: '1',
        title: 'What is budgeting?',
        type: NodeType.lesson,
        isUnlocked: false,
        isCompleted: false,
      ),
      StudyNode(
        id: '2',
        title: 'Budgeting Quiz',
        type: NodeType.quiz,
        isUnlocked: false,
        isCompleted: false,
      ),
      StudyNode(
        id: '3',
        title: 'Spending Decision',
        type: NodeType.decision,
        isUnlocked: false,
        isCompleted: false,
      ),
      StudyNode(
        id: '4',
        title: 'Bonus Rewards',
        type: NodeType.reward,
        isUnlocked: false,
        isCompleted: false,
      ),
    ];
  }

  // 🔵 NODE UI (circle)
  Widget _buildNode(StudyNode node) {
    late Color color;

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
        color = Colors.green;
        break;
      case NodeType.boss:
        color = Colors.red;
        break;
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

  // 🔗 CONNECTOR LINE (fixed)
  Widget _buildLine(int index, int length) {
    if (index == length - 1) return const SizedBox();

    final isLeft = index.isEven;

    return Positioned(
      left: isLeft ? 115 : 115,
      top: index * 140.0 + 35,
      child: Container(
        width: 4,
        height: 140,
        color: Colors.grey.shade300,
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
                  // TODO: navigate to lesson/quiz screen
                },
                child: const Text("Start"),
              ),
            ],
          ),
        );
      },
    );
  }

  // 🎯 ICON MAPPING
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
}