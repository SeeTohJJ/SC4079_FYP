import 'package:flutter/material.dart';
import 'package:frontend/features/study/exceptions/insufficient_energy_exception.dart';
import 'package:frontend/features/study/models/energy.dart';
import 'package:frontend/features/study/models/study_node.dart';
import 'package:frontend/features/study/models/subtopic_study_node.dart';
import 'package:frontend/features/study/screens/nodes/boss_node_page.dart';
import 'package:frontend/features/study/screens/nodes/decision_node_page.dart';
import 'package:frontend/features/study/screens/nodes/lesson_node_page.dart';
import 'package:frontend/features/study/screens/nodes/quiz_node_page.dart';
import 'package:frontend/features/study/screens/nodes/reward_node_page.dart';
import 'package:frontend/features/study/services/energy_service.dart';
import 'package:frontend/features/study/services/study_service.dart';
import 'package:frontend/features/study/widgets/energy_widget.dart';
import 'package:frontend/features/study/widgets/study_line_painter.dart';
import 'package:frontend/auth/services/auth_service.dart';

class StudyPage extends StatefulWidget {
  const StudyPage({super.key});

  @override
  State<StudyPage> createState() => _StudyPageState();
}

class _StudyPageState extends State<StudyPage> {
  final authService = AuthService();
  final studyService = StudyService();
  final EnergyService energyService = EnergyService();

  late Future<String?> token;

  List<StudyNode> nodes = [];
  List<SubtopicStudyNode> subtopicNodes = [];

  final ScrollController _studyPathScrollController =
      ScrollController();

  final TextEditingController _searchController =
      TextEditingController();

  List<SubtopicStudyNode> _searchResults = [];

  bool isLoading = true;

  Energy? energy;

  @override
  void initState() {
    super.initState();

    token = authService.getToken();

    loadNodes();
    loadEnergy();
  }

  // ============================================================
  // DATA LOADING
  // ============================================================

  Future<void> loadNodes() async {
    try {
      final authToken = await token;

      if (authToken == null) {
        if (!mounted) {
          return;
        }

        setState(() {
          isLoading = false;
        });

        return;
      }

      final data =
          await studyService.getStudyPathNodes(authToken);

      final loadedNodes = data
          .map<StudyNode>(
            (json) => StudyNode.fromJson(json),
          )
          .toList();

      final groupedNodes =
          _groupBySubtopic(loadedNodes);

      if (!mounted) {
        return;
      }

      setState(() {
        nodes = loadedNodes;
        subtopicNodes = groupedNodes;
        isLoading = false;
      });
    } catch (e) {
      if (!mounted) {
        return;
      }

      setState(() {
        isLoading = false;
      });

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Failed to load study path: $e',
          ),
        ),
      );
    }
  }

  Future<void> loadEnergy() async {
    try {
      final loadedEnergy =
          await energyService.getEnergy();

      if (!mounted) {
        return;
      }

      setState(() {
        energy = loadedEnergy;
      });
    } catch (e) {
      debugPrint(
        'Failed to load energy: $e',
      );
    }
  }

  // ============================================================
  // GROUP NODES BY SUBTOPIC
  // ============================================================

  List<SubtopicStudyNode> _groupBySubtopic(
    List<StudyNode> nodes,
  ) {
    final Map<String, List<StudyNode>> grouped = {};

    for (final node in nodes) {
      grouped.putIfAbsent(
        node.subtopicId,
        () => [],
      );

      grouped[node.subtopicId]!.add(node);
    }

    return grouped.entries.map((entry) {
      final activities = entry.value;

      return SubtopicStudyNode(
        subtopicId: entry.key,
        subtopicName: activities.first.subtopicName,
        topicName: activities.first.topicName,
        nodeTitle: activities.first.nodeTitle,
        activities: activities,
      );
    }).toList();
  }

  // ============================================================
  // BUILD
  // ============================================================

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Study Path',
        ),
        actions: [
          if (energy != null)
            Padding(
              padding: const EdgeInsets.only(
                right: 12,
              ),
              child: EnergyWidget(
                energy: energy!,
              ),
            ),
        ],
      ),
      body: isLoading
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : Column(
              children: [
                // Search bar is always visible
                // after loading.
                Container(
                  width: double.infinity,
                  color: Color(0xFF1E1E1E),
                  padding: const EdgeInsets.fromLTRB(
                    16,
                    12,
                    16,
                    12,
                  ),
                  child: _buildSearchBar(),
                ),

                // Study path
                Expanded(
                  child: subtopicNodes.isEmpty
                      ? const Center(
                          child: Text(
                            'No study content available.',
                          ),
                        )
                      : _buildStudyPath(),
                ),
              ],
            ),
    );
  }

  // ============================================================
  // SEARCH BAR
  // ============================================================

  Widget _buildSearchBar() {
    return Column(
      children: [
        TextField(
          controller: _searchController,
          onChanged: _searchSubtopics,
          style: const TextStyle(
            color: Colors.black,
          ),
          decoration: InputDecoration(
            hintText: 'Search study topics...',
            hintStyle: const TextStyle(
              color: Colors.grey,
            ),
            prefixIcon: const Icon(
              Icons.search,
              color: Colors.grey,
            ),
            suffixIcon:
                _searchController.text.isNotEmpty
                    ? IconButton(
                        icon: const Icon(
                          Icons.clear,
                          color: Colors.grey,
                        ),
                        onPressed: () {
                          _searchController.clear();

                          setState(() {
                            _searchResults = [];
                          });
                        },
                      )
                    : null,
            filled: true,
            fillColor: const Color(0xFFEFEFEF),
            border: OutlineInputBorder(
              borderRadius:
                  BorderRadius.circular(16),
              borderSide: BorderSide.none,
            ),
            enabledBorder: OutlineInputBorder(
              borderRadius:
                  BorderRadius.circular(16),
              borderSide: BorderSide.none,
            ),
            focusedBorder: OutlineInputBorder(
              borderRadius:
                  BorderRadius.circular(16),
              borderSide: const BorderSide(
                width: 2,
              ),
            ),
            contentPadding:
                const EdgeInsets.symmetric(
              horizontal: 16,
              vertical: 14,
            ),
          ),
        ),

        // Search results
        if (_searchResults.isNotEmpty)
          Container(
            width: double.infinity,
            margin: const EdgeInsets.only(
              top: 6,
            ),
            constraints: const BoxConstraints(
              maxHeight: 200,
            ),
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius:
                  BorderRadius.circular(12),
              boxShadow: const [
                BoxShadow(
                  blurRadius: 8,
                  spreadRadius: 1,
                ),
              ],
            ),
            child: ListView.builder(
              shrinkWrap: true,
              itemCount: _searchResults.length,
              itemBuilder: (
                context,
                index,
              ) {
                final subtopic =
                    _searchResults[index];

                return ListTile(
                  leading: CircleAvatar(
                    backgroundColor:
                        subtopic.color,
                    child: const Icon(
                      Icons.menu_book,
                      color: Colors.white,
                      size: 18,
                    ),
                  ),
                  title: Text(
                    subtopic.subtopicName,
                    maxLines: 1,
                    overflow:
                        TextOverflow.ellipsis,
                    style: const TextStyle(
                      color: Colors.black,
                    ),
                  ),
                  subtitle: Text(
                    subtopic.topicName,
                    maxLines: 1,
                    overflow:
                        TextOverflow.ellipsis,
                    style: const TextStyle(
                      color: Colors.black,
                    ),
                  ),
                  onTap: () {
                    _moveToSubtopic(
                      subtopic,
                    );
                  },
                );
              },
            ),
          ),
      ],
    );
  }

  // ============================================================
  // SEARCH
  // ============================================================

  void _searchSubtopics(String query) {
    final searchQuery =
        query.trim().toLowerCase();

    if (searchQuery.isEmpty) {
      setState(() {
        _searchResults = [];
      });

      return;
    }

    setState(() {
      _searchResults =
          subtopicNodes.where((subtopic) {
        final subtopicName =
            subtopic.subtopicName
                .toLowerCase();

        final topicName =
            subtopic.topicName
                .toLowerCase();

        return subtopicName
                .contains(searchQuery) ||
            topicName
                .contains(searchQuery);
      }).toList();
    });
  }

  // ============================================================
  // MOVE TO SUBTOPIC
  // ============================================================

  void _moveToSubtopic(
    SubtopicStudyNode subtopic,
  ) {
    final index =
        subtopicNodes.indexWhere(
      (node) =>
          node.subtopicId ==
          subtopic.subtopicId,
    );

    if (index == -1) {
      return;
    }

    const nodeSpacing = 160.0;
    const topPadding = 40.0;

    final targetOffset =
        topPadding +
        (index * nodeSpacing);

    final maxScroll =
        _studyPathScrollController
            .position
            .maxScrollExtent;

    final safeOffset =
        targetOffset.clamp(
      0.0,
      maxScroll,
    );

    _studyPathScrollController.animateTo(
      safeOffset,
      duration:
          const Duration(milliseconds: 500),
      curve: Curves.easeInOut,
    );

    setState(() {
      _searchResults = [];
      _searchController.clear();
    });
  }

  // ============================================================
  // STUDY PATH
  // ============================================================

  Widget _buildStudyPath() {
    return LayoutBuilder(
      builder: (
        context,
        constraints,
      ) {
        const topPadding = 40.0;
        const nodeSpacing = 160.0;

        final pathHeight =
            topPadding +
            (subtopicNodes.length *
                nodeSpacing) +
            nodeSpacing;

        return SingleChildScrollView(
          controller:
              _studyPathScrollController,
          child: SizedBox(
            width: constraints.maxWidth,
            height: pathHeight,
            child: Stack(
              children: [
                CustomPaint(
                  size: Size(
                    constraints.maxWidth,
                    pathHeight,
                  ),
                  painter: StudyPathPainter(
                    nodes: subtopicNodes,
                    getX: _getX,
                    getY: _getY,
                  ),
                ),

                for (
                  int i = 0;
                  i < subtopicNodes.length;
                  i++
                )
                  Positioned(
                    left: _getX(i),
                    top: _getY(i),
                    child:
                        _buildSubtopicNode(
                      subtopicNodes[i],
                      i,
                    ),
                  ),
              ],
            ),
          ),
        );
      },
    );
  }

  // ============================================================
  // NODE POSITIONING
  // ============================================================

  double _getX(int index) {
    const offsets = [
      0.0,
      60.0,
      90.0,
      60.0,
      0.0,
      -60.0,
      -90.0,
      -60.0,
    ];

    final width =
        MediaQuery.of(context).size.width;

    return width / 2 -
        43 +
        offsets[
            index % offsets.length];
  }

  double _getY(int index) {
    const topPadding = 40.0;
    const nodeSpacing = 160.0;

    return (index * nodeSpacing) +
        topPadding;
  }

  // ============================================================
  // SUBTOPIC NODE
  // ============================================================

  Widget _buildSubtopicNode(
    SubtopicStudyNode subtopic,
    int index,
  ) {
    final topicColor =
        subtopic.color;

    return GestureDetector(
      onTap: () =>
          _onSubtopicTap(subtopic),
      child: SizedBox(
        width: 86,
        height: 200,
        child: Column(
          children: [
            Stack(
              clipBehavior:
                  Clip.none,
              alignment:
                  Alignment.center,
              children: [
                SizedBox(
                  width: 86,
                  height: 86,
                  child:
                      CircularProgressIndicator(
                    value:
                        subtopic.progress,
                    strokeWidth: 6,
                    backgroundColor:
                        Colors.grey.shade300,
                    valueColor:
                        AlwaysStoppedAnimation<
                            Color>(
                      topicColor,
                    ),
                  ),
                ),

                Container(
                  width: 68,
                  height: 68,
                  decoration:
                      BoxDecoration(
                    shape:
                        BoxShape.circle,
                    color: subtopic
                            .isUnlocked
                        ? topicColor
                        : Colors
                            .grey
                            .shade400,
                  ),
                  child: Icon(
                    subtopic.isUnlocked
                        ? Icons.menu_book
                        : Icons.lock,
                    color:
                        Colors.white,
                    size: 30,
                  ),
                ),

                if (subtopic
                    .hasNewItems)
                  Positioned(
                    right: 0,
                    top: 0,
                    child:
                        Container(
                      width: 20,
                      height: 20,
                      decoration:
                          BoxDecoration(
                        shape: BoxShape
                            .circle,
                        color:
                            Colors.red,
                        border:
                            Border.all(
                          color:
                              Colors.white,
                          width: 2,
                        ),
                      ),
                    ),
                  ),
              ],
            ),

            const SizedBox(
              height: 6,
            ),

            Text(
              subtopic.subtopicName,
              textAlign:
                  TextAlign.center,
              maxLines: 2,
              overflow:
                  TextOverflow.ellipsis,
              style:
                  const TextStyle(
                fontSize: 12,
                fontWeight:
                    FontWeight.w600,
              ),
            ),
          ],
        ),
      ),
    );
  }

  // ============================================================
  // SUBTOPIC BOTTOM SHEET
  // ============================================================

  void _onSubtopicTap(
    SubtopicStudyNode subtopic,
  ) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor:
          Colors.transparent,
      builder: (context) {
        return _buildSubtopicBottomSheet(
          subtopic,
        );
      },
    );
  }

  Widget _buildSubtopicBottomSheet(
    SubtopicStudyNode subtopic,
  ) {
    return DraggableScrollableSheet(
      initialChildSize: 0.65,
      minChildSize: 0.4,
      maxChildSize: 0.9,
      expand: false,
      builder: (
        context,
        scrollController,
      ) {
        return Container(
          decoration:
              const BoxDecoration(
            color: Color(0xFF1E1E1E),
            borderRadius:
                BorderRadius.vertical(
              top: Radius.circular(24),
            ),
          ),
          child: Column(
            children: [
              const SizedBox(
                height: 12,
              ),

              // Drag handle
              Container(
                width: 40,
                height: 4,
                decoration:
                    BoxDecoration(
                  color:
                      const Color(
                    0xFF666666,
                  ),
                  borderRadius:
                      BorderRadius.circular(
                    10,
                  ),
                ),
              ),

              const SizedBox(
                height: 20,
              ),

              // Header
              Padding(
                padding:
                    const EdgeInsets
                        .symmetric(
                  horizontal: 20,
                ),
                child: Row(
                  children: [
                    Container(
                      width: 48,
                      height: 48,
                      decoration:
                          BoxDecoration(
                        shape:
                            BoxShape.circle,
                        color:
                            subtopic.color,
                      ),
                      child:
                          const Icon(
                        Icons.menu_book,
                        color:
                            Colors.white,
                      ),
                    ),

                    const SizedBox(
                      width: 12,
                    ),

                    Expanded(
                      child:
                          Column(
                        crossAxisAlignment:
                            CrossAxisAlignment
                                .start,
                        children: [
                          Text(
                            subtopic
                                .subtopicName,
                            style:
                                const TextStyle(
                              fontSize:
                                  20,
                              fontWeight:
                                  FontWeight
                                      .bold,
                              color: Colors
                                  .white,
                            ),
                          ),

                          const SizedBox(
                            height: 4,
                          ),

                          Text(
                            subtopic.topicName,
                            style:
                                const TextStyle(
                              fontSize:
                                  14,
                              color:
                                  Color(
                                0xFFAAAAAA,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),

              const SizedBox(
                height: 16,
              ),

              // Progress
              Padding(
                padding:
                    const EdgeInsets
                        .symmetric(
                  horizontal: 20,
                ),
                child: Column(
                  crossAxisAlignment:
                      CrossAxisAlignment
                          .start,
                  children: [
                    Row(
                      mainAxisAlignment:
                          MainAxisAlignment
                              .spaceBetween,
                      children: [
                        const Text(
                          'Progress',
                          style:
                              TextStyle(
                            fontWeight:
                                FontWeight
                                    .bold,
                            color: Colors
                                .white,
                          ),
                        ),

                        Text(
                          '${subtopic.completedCount} of ${subtopic.totalCount} completed',
                          style:
                              const TextStyle(
                            color:
                                Color(
                              0xFFAAAAAA,
                            ),
                          ),
                        ),
                      ],
                    ),

                    const SizedBox(
                      height: 8,
                    ),

                    LinearProgressIndicator(
                      value:
                          subtopic.progress,
                      minHeight: 8,
                      backgroundColor:
                          const Color(
                        0xFF3A3A3A,
                      ),
                      valueColor:
                          AlwaysStoppedAnimation<
                              Color>(
                        subtopic.color,
                      ),
                    ),
                  ],
                ),
              ),

              const SizedBox(
                height: 16,
              ),

              const Divider(
                height: 1,
                color: Color(
                  0xFF383838,
                ),
              ),

              // Activities
              Expanded(
                child:
                    _buildActivityList(
                  subtopic,
                  scrollController,
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  // ============================================================
  // ACTIVITY LIST
  // ============================================================

  Widget _buildActivityList(
    SubtopicStudyNode subtopic,
    ScrollController scrollController,
  ) {
    final lessons =
        subtopic.activities
            .where(
              (activity) =>
                  activity.type ==
                  NodeType.lesson,
            )
            .toList();

    final quizzes =
        subtopic.activities
            .where(
              (activity) =>
                  activity.type ==
                      NodeType.quiz ||
                  activity.type ==
                      NodeType.review ||
                  activity.type ==
                      NodeType.generated,
            )
            .toList();

    final otherActivities =
        subtopic.activities
            .where(
              (activity) =>
                  activity.type ==
                      NodeType.decision ||
                  activity.type ==
                      NodeType.reward ||
                  activity.type ==
                      NodeType.boss,
            )
            .toList();

    return ListView(
      controller:
          scrollController,
      padding:
          const EdgeInsets.symmetric(
        horizontal: 20,
        vertical: 16,
      ),
      children: [
        if (lessons.isNotEmpty) ...[
          const Text(
            'Lessons',
            style: TextStyle(
              fontSize: 18,
              fontWeight:
                  FontWeight.bold,
              color: Colors.white,
            ),
          ),

          const SizedBox(
            height: 8,
          ),

          ...lessons.map(
            (lesson) =>
                _buildActivityItem(
              lesson,
            ),
          ),
        ],

        if (quizzes.isNotEmpty) ...[
          const SizedBox(
            height: 20,
          ),

          const Text(
            'Quizzes',
            style: TextStyle(
              fontSize: 18,
              fontWeight:
                  FontWeight.bold,
              color: Colors.white,
            ),
          ),

          const SizedBox(
            height: 8,
          ),

          ...quizzes.map(
            (quiz) =>
                _buildActivityItem(
              quiz,
            ),
          ),
        ],

        if (otherActivities
            .isNotEmpty) ...[
          const SizedBox(
            height: 20,
          ),

          const Text(
            'Activities',
            style: TextStyle(
              fontSize: 18,
              fontWeight:
                  FontWeight.bold,
              color: Colors.white,
            ),
          ),

          const SizedBox(
            height: 8,
          ),

          ...otherActivities.map(
            (activity) =>
                _buildActivityItem(
              activity,
            ),
          ),
        ],
      ],
    );
  }

  // ============================================================
  // ACTIVITY ITEM
  // ============================================================

  Widget _buildActivityItem(
    StudyNode activity,
  ) {
    final completed =
        activity.isCompleted;

    final unlocked =
        activity.isUnlocked;

    return Card(
      margin:
          const EdgeInsets.only(
        bottom: 8,
      ),
      child: ListTile(
        enabled: unlocked,

        leading:
            _getActivityIcon(
          activity.type,
          completed,
          unlocked,
        ),

        title: Text(
          activity.nodeTitle,
          maxLines: 2,
          overflow:
              TextOverflow.ellipsis,
        ),

        onTap: unlocked
            ? () {
                Navigator.pop(
                  context,
                );

                _openActivity(
                  activity,
                  reviewMode:
                      completed,
                );
              }
            : null,
      ),
    );
  }

  // ============================================================
  // ACTIVITY ICON
  // ============================================================

  Widget _getActivityIcon(
    NodeType type,
    bool completed,
    bool unlocked,
  ) {
    if (!unlocked) {
      return const Icon(
        Icons.lock,
        color: Colors.grey,
      );
    }

    if (completed) {
      return const Icon(
        Icons.check_circle,
        color: Colors.green,
      );
    }

    switch (type) {
      case NodeType.lesson:
        return const Icon(
          Icons.menu_book,
        );

      case NodeType.quiz:
        return const Icon(
          Icons.quiz,
        );

      case NodeType.review:
        return const Icon(
          Icons.refresh,
        );

      case NodeType.generated:
        return const Icon(
          Icons.psychology,
        );

      case NodeType.decision:
        return const Icon(
          Icons.alt_route,
        );

      case NodeType.reward:
        return const Icon(
          Icons.monetization_on,
        );

      case NodeType.boss:
        return const Icon(
          Icons.emoji_events,
        );
    }
  }

  String _getActivityTypeLabel(
    NodeType type,
  ) {
    switch (type) {
      case NodeType.lesson:
        return 'Lesson';

      case NodeType.quiz:
        return 'Quiz';

      case NodeType.review:
        return 'Review';

      case NodeType.generated:
        return 'AI Generated Quiz';

      case NodeType.decision:
        return 'Decision';

      case NodeType.reward:
        return 'Reward';

      case NodeType.boss:
        return 'Challenge';
    }
  }

  // ============================================================
  // OPEN ACTIVITY
  // ============================================================

  Future<void> _openActivity(
    StudyNode node, {
    bool reviewMode = false,
  }) async {
    try {
      switch (node.type) {
        // ------------------------------------------------------
        // LESSON
        // ------------------------------------------------------

        case NodeType.lesson:
          final lesson =
              await studyService
                  .getLessonContent(
            node.id,
          );

          if (!mounted) {
            return;
          }

          final result =
              await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (_) =>
                  LessonNodePage(
                lesson: lesson,
                nodeId: node.id,
                reviewMode:
                    reviewMode,
              ),
            ),
          );

          if (result == true) {
            await loadNodes();
            await loadEnergy();
          }

          break;

        // ------------------------------------------------------
        // QUIZ
        // ------------------------------------------------------

        case NodeType.quiz:
          final quiz =
              await studyService
                  .getQuizContent(
            node.id,
          );

          if (!mounted) {
            return;
          }

          final result =
              await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (_) =>
                  QuizNodePage(
                nodeId: node.id,
                quiz: quiz,
                reviewMode:
                    reviewMode,
              ),
            ),
          );

          if (result == true) {
            await loadNodes();
            await loadEnergy();
          }

          break;

        // ------------------------------------------------------
        // DECISION
        // ------------------------------------------------------

        case NodeType.decision:
          if (!mounted) {
            return;
          }

          final result =
              await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (_) =>
                  DecisionNodePage(
                node: node,
              ),
            ),
          );

          if (result == true) {
            await _completeNode(
              node.id,
            );
          }

          break;

        // ------------------------------------------------------
        // REWARD
        // ------------------------------------------------------

        case NodeType.reward:
          if (!mounted) {
            return;
          }

          await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (_) =>
                  RewardNodePage(
                node: node,
              ),
            ),
          );

          break;

        // ------------------------------------------------------
        // BOSS
        // ------------------------------------------------------

        case NodeType.boss:
          if (!mounted) {
            return;
          }

          await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (_) =>
                  BossNodePage(
                node: node,
              ),
            ),
          );

          break;

        // ------------------------------------------------------
        // REVIEW
        // ------------------------------------------------------

        case NodeType.review:
          final quiz =
              await studyService
                  .getQuizContent(
            node.id,
          );

          if (!mounted) {
            return;
          }

          final result =
              await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (_) =>
                  QuizNodePage(
                nodeId: node.id,
                quiz: quiz,
                reviewMode: true,
              ),
            ),
          );

          if (result == true) {
            await loadNodes();
            await loadEnergy();
          }

          break;

        // ------------------------------------------------------
        // GENERATED
        // ------------------------------------------------------

        case NodeType.generated:
          final quiz =
              await studyService
                  .getGeminiQuizContent();

          if (!mounted) {
            return;
          }

          final result =
              await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (_) =>
                  QuizNodePage(
                nodeId: node.id,
                quiz: quiz,
                reviewMode:
                    reviewMode,
              ),
            ),
          );

          if (result == true) {
            await loadNodes();
            await loadEnergy();
          }

          break;
      }
    } on InsufficientEnergyException catch (e) {
      if (!mounted) {
        return;
      }

      _showInsufficientEnergyDialog(e);
    } catch (e) {
      if (!mounted) {
        return;
      }

      ScaffoldMessenger.of(context)
          .showSnackBar(
        const SnackBar(
          content: Text(
            'You do not have enough energy to start this activity.',
          ),
        ),
      );
    }
  }

  // ============================================================
  // COMPLETE NODE
  // ============================================================

  Future<void> _completeNode(
    String nodeId,
  ) async {
    try {
      await studyService.completeNode(
        nodeId,
      );

      await loadNodes();
      await loadEnergy();
    } catch (e) {
      if (!mounted) {
        return;
      }

      ScaffoldMessenger.of(context)
          .showSnackBar(
        SnackBar(
          content: Text(
            'Failed to complete activity: $e',
          ),
        ),
      );
    }
  }

  // ============================================================
  // INSUFFICIENT ENERGY DIALOG
  // ============================================================

  void _showInsufficientEnergyDialog(
    InsufficientEnergyException e,
  ) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text(
            'Not Enough Energy',
          ),
          content: Column(
            mainAxisSize:
                MainAxisSize.min,
            crossAxisAlignment:
                CrossAxisAlignment.start,
            children: [
              Text(
                'You need ${e.requiredEnergy} energy to start this activity.',
              ),
              const SizedBox(
                height: 8,
              ),
              Text(
                'Current energy: ${e.currentEnergy}',
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(
                  context,
                );
              },
              child: const Text(
                'OK',
              ),
            ),
          ],
        );
      },
    );
  }

  // ============================================================
  // DISPOSE
  // ============================================================

  @override
  void dispose() {
    _studyPathScrollController
        .dispose();

    _searchController.dispose();

    super.dispose();
  }
}
