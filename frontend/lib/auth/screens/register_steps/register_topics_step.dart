import 'package:flutter/material.dart';

import '../../models/register_data.dart';
import '../../services/auth_services.dart';

class RegisterTopicsStep extends StatefulWidget {

  final RegisterData data;
  final VoidCallback onBack;

  const RegisterTopicsStep({
    super.key,
    required this.data,
    required this.onBack,
  });

  @override
  State<RegisterTopicsStep> createState() =>
      _RegisterTopicsStepState();
}

class _RegisterTopicsStepState
    extends State<RegisterTopicsStep> {

  final AuthService authService = AuthService();

  bool loading = false;

  final topics = [
    {"id": "T001", "name": "Budgeting"},
    {"id": "T002", "name": "Saving"},
    {"id": "T003", "name": "Investing"},
    {"id": "T004", "name": "Credit Cards"},
    {"id": "T005", "name": "Loans"},
    {"id": "T006", "name": "Insurance"},
    {"id": "T007", "name": "Taxes"},
    {"id": "T008", "name": "Retirement Planning"},
    {"id": "T009", "name": "Debt Management"},
    {"id": "T010", "name": "Emergency Fund"},
  ];

  late final Map<String, String> topicToIdMap = {
    for (var topic in topics)
      topic["name"] as String: topic["id"] as String
  };

  final List<String> selectedTopics = [];

  Future<void> finishRegistration() async {

    if (selectedTopics.isEmpty) {

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            "Please select at least one topic",
          ),
        ),
      );

      return;
    }

    widget.data.topicsToLearn = selectedTopics;

    setState(() {
      loading = true;
    });

    try {

      final success = await authService.register(
        widget.data.email,
        widget.data.password,
        widget.data.username,
        widget.data.gender,
        widget.data.age,
        widget.data.employmentStatus,
        widget.data.income,
        widget.data.country,
        widget.data.topicsToLearn,
      );

      if (!mounted) return;

      if (success) {

        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Registration successful"),
          ),
        );

        Navigator.pushReplacementNamed(
          context,
          '/login',
        );

      } else {

        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Registration failed"),
          ),
        );
      }

    } catch (e) {

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text("Error: $e"),
        ),
      );

    } finally {

      if (mounted) {

        setState(() {
          loading = false;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {

    return Padding(
      padding: const EdgeInsets.all(16),

      child: Column(
        crossAxisAlignment:
            CrossAxisAlignment.start,

        children: [

          const SizedBox(height: 48),

          const Text(
            "What would you like to learn?",
            style: TextStyle(
              fontSize: 28,
              fontWeight: FontWeight.bold,
            ),
          ),

          const SizedBox(height: 24),

          Expanded(
            child: Wrap(
              spacing: 12,
              runSpacing: 12,

              children: topics.map((topic) {

                final name = topic["name"] as String;
                final id = topic["id"] as String;

                final selected = selectedTopics.contains(id);

                return FilterChip(
                  label: Text(name),
                  selected: selected,

                  onSelected: (value) {
                    setState(() {
                      if (value) {
                        if (!selectedTopics.contains(id)) {
                          selectedTopics.add(id);
                        }
                      } else {
                        selectedTopics.remove(id);
                      }
                    });
                  },
                );
              }).toList(),
            ),
          ),

          Row(
            children: [

              Expanded(
                child: OutlinedButton(
                  onPressed: widget.onBack,
                  child: const Text("Back"),
                ),
              ),

              const SizedBox(width: 16),

              Expanded(
                child: ElevatedButton(
                  onPressed:
                      loading ? null : finishRegistration,

                  child: loading
                      ? const CircularProgressIndicator()
                      : const Text("Finish"),
                ),
              ),
            ],
          ),

          const SizedBox(height: 24),
        ],
      ),
    );
  }
}