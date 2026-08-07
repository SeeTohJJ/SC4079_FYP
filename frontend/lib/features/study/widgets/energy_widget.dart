import 'dart:async';

import 'package:flutter/material.dart';
import '../models/energy.dart';

class EnergyWidget extends StatefulWidget {
  final Energy energy;
  final VoidCallback? onRefresh;

  const EnergyWidget({
    super.key,
    required this.energy,
    this.onRefresh,
  });

  @override
  State<EnergyWidget> createState() => _EnergyWidgetState();
}

class _EnergyWidgetState extends State<EnergyWidget> {
  Timer? _timer;

  late int _secondsRemaining;
  late int _currentEnergy;

  @override
  void initState() {
    super.initState();

    _currentEnergy = widget.energy.currentEnergy;
    _secondsRemaining = widget.energy.secondsUntilNextEnergy;

    _startTimer();
  }

  @override
  void didUpdateWidget(covariant EnergyWidget oldWidget) {
    super.didUpdateWidget(oldWidget);

    if (oldWidget.energy.currentEnergy !=
            widget.energy.currentEnergy ||
        oldWidget.energy.secondsUntilNextEnergy !=
            widget.energy.secondsUntilNextEnergy) {
      _currentEnergy = widget.energy.currentEnergy;
      _secondsRemaining = widget.energy.secondsUntilNextEnergy;
    }
  }

  void _startTimer() {
    _timer = Timer.periodic(
      const Duration(seconds: 1),
      (_) {
        if (!mounted) return;

        if (_secondsRemaining > 0) {
          setState(() {
            _secondsRemaining--;
          });
        } else {
          // Ask backend for the authoritative energy value.
          widget.onRefresh?.call();
        }
      },
    );
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  String _formatTime(int seconds) {
    final minutes = seconds ~/ 60;
    final remainingSeconds = seconds % 60;

    return '$minutes:${remainingSeconds.toString().padLeft(2, '0')}';
  }

  @override
  Widget build(BuildContext context) {
    final isFull =
        _currentEnergy >= widget.energy.maxEnergy;

    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        const Icon(
          Icons.bolt,
          color: Colors.amber,
        ),

        const SizedBox(width: 4),

        Text(
          '$_currentEnergy/${widget.energy.maxEnergy}',
          style: const TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),

        if (!isFull) ...[
          const SizedBox(width: 8),

          Text(
            '+1 ${_formatTime(_secondsRemaining)}',
            style: const TextStyle(
              fontSize: 12,
              color: Colors.grey,
            ),
          ),
        ],
      ],
    );
  }
}