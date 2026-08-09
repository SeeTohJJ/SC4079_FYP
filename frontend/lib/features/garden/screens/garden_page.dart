import 'package:flutter/material.dart';

import 'package:frontend/features/garden/models/garden.dart';
import 'package:frontend/features/garden/models/user_plant.dart';
import 'package:frontend/features/garden/services/garden_service.dart';

class GardenPage extends StatefulWidget {
  const GardenPage({super.key});

  @override
  State<GardenPage> createState() => _GardenPageState();
}

class _GardenPageState extends State<GardenPage> {
  final GardenService gardenService = GardenService();

  Garden? garden;

  bool isLoading = true;

  int currentPlantIndex = 0;

  @override
  void initState() {
    super.initState();
    loadGarden();
  }

  Future<void> loadGarden() async {
    try {
      final result = await gardenService.getGarden();

      if (!mounted) return;

      setState(() {
        garden = result;
        isLoading = false;

        // Make sure the current index is valid
        if (garden!.plants.isEmpty) {
          currentPlantIndex = 0;
        } else if (currentPlantIndex >= garden!.plants.length) {
          currentPlantIndex = garden!.plants.length - 1;
        }
      });
    } catch (e) {
      if (!mounted) return;

      setState(() {
        isLoading = false;
      });

      debugPrint('Failed to load garden: $e');
    }
  }

  UserPlant get currentPlant {
    return garden!.plants[currentPlantIndex];
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }

    if (garden == null) {
      return Scaffold(
        appBar: AppBar(
          title: const Text('Garden'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                'Failed to load garden.',
              ),
              const SizedBox(height: 12),
              ElevatedButton(
                onPressed: () {
                  setState(() {
                    isLoading = true;
                  });

                  loadGarden();
                },
                child: const Text('Retry'),
              ),
            ],
          ),
        ),
      );
    }

    if (garden!.plants.isEmpty) {
      return Scaffold(
        appBar: AppBar(
          title: const Text('Garden'),
        ),
        body: const Center(
          child: Text(
            'You do not have any plants yet.',
          ),
        ),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text('Garden'),
        actions: [
          Padding(
            padding: const EdgeInsets.only(
              right: 16,
            ),
            child: Row(
              children: [
                const Icon(
                  Icons.water_drop,
                ),
                const SizedBox(width: 5),
                Text(
                  '${garden!.waterCurrency}',
                  style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
      body: RefreshIndicator(
        onRefresh: loadGarden,
        child: Stack(
          children: [
            _buildPlantView(),
            _buildLeftButton(),
            _buildRightButton(),
            _buildWaterButton(),
          ],
        ),
      ),
    );
  }

  Widget _buildPlantView() {
    final plant = currentPlant;

    return Padding(
      padding: const EdgeInsets.symmetric(
        horizontal: 60,
      ),
      child: Column(
        children: [
          const SizedBox(height: 25),

          // Topic name
          Text(
            plant.topicName,
            style: const TextStyle(
              fontSize: 26,
              fontWeight: FontWeight.bold,
            ),
            textAlign: TextAlign.center,
          ),

          const SizedBox(height: 5),

          // Plant counter
          Text(
            '${currentPlantIndex + 1} / ${garden!.plants.length}',
            style: TextStyle(
              fontSize: 14,
              color: Colors.grey.shade600,
            ),
          ),

          const SizedBox(height: 15),

          // Plant visual
          Expanded(
            child: _buildPlantVisual(plant),
          ),


          const SizedBox(height: 8),

          // Growth progress
          Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: 20,
            ),
            child: LinearProgressIndicator(
              value: plant.growth.clamp(
                0.0,
                1.0,
              ),
              minHeight: 10,
            ),
          ),

          const SizedBox(height: 25),

          // Plant stats
          _buildPlantStats(plant),

          // Leave space for the water button
          const SizedBox(height: 90),
        ],
      ),
    );
  }

  Widget _buildPlantVisual(UserPlant plant) {
    IconData icon;

    switch (plant.stage) {
      case 'SEEDING':
        icon = Icons.grass;
        break;

      case 'SPROUTING':
        icon = Icons.spa;
        break;

      case "VEGETATING":
        icon = Icons.local_florist;
        break;

      case "FLOWERING":
        icon = Icons.park;
        break;

      case "RIPENING":
        icon = Icons.apple;
        break;

      default:
        icon = Icons.forest;
        break;
    }

    return Center(
      child: AnimatedSwitcher(
        duration: const Duration(
          milliseconds: 300,
        ),
        child: Icon(
          icon,
          key: ValueKey(
            '${plant.topicName}_${plant.stage}',
          ),
          size: 180,
        ),
      ),
    );
  }

  Widget _buildPlantStats(UserPlant plant) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        Column(
          children: [
            Icon(
                  Icons.favorite,
                  size: 26
                ),
            const SizedBox(height: 4),
            Text(
              '${plant.happiness}',
              style: const TextStyle(
                fontSize: 17,
                fontWeight: FontWeight.bold,
              ),
            ),
            const Text(
              'Happiness',
              style: TextStyle(
                fontSize: 13,
              ),
            ),
          ],
        ),
        Column(
          children: [
            Icon(
                  Icons.psychology_alt,
                  size: 26
                ),
            const SizedBox(height: 4),
            Text(
              '${(plant.mastery * 100).toStringAsFixed(0)}%',
              style: const TextStyle(
                fontSize: 17,
                fontWeight: FontWeight.bold,
              ),
            ),
            const Text(
              'Mastery',
              style: TextStyle(
                fontSize: 13,
              ),
            ),
          ],
        ),
      ],
    );
  }

  Widget _buildLeftButton() {
    return Positioned(
      left: 4,
      top: 0,
      bottom: 0,
      child: Center(
        child: IconButton(
          onPressed:
              _canGoPrevious() ? _previousPlant : null,
          icon: const Icon(
            Icons.chevron_left,
            size: 48,
          ),
        ),
      ),
    );
  }

  Widget _buildRightButton() {
    return Positioned(
      right: 4,
      top: 0,
      bottom: 0,
      child: Center(
        child: IconButton(
          onPressed:
              _canGoNext() ? _nextPlant : null,
          icon: const Icon(
            Icons.chevron_right,
            size: 48,
          ),
        ),
      ),
    );
  }

  Widget _buildWaterButton() {
    final plant = currentPlant;

    return Positioned(
      left: 40,
      right: 40,
      bottom: 20,
      child: SizedBox(
        height: 55,
        child: ElevatedButton.icon(
          onPressed: () => _waterPlant(plant),
          icon: const Icon(
            Icons.water_drop,
          ),
          label: const Text(
            'Water Plant',
            style: TextStyle(
              fontSize: 18,
            ),
          ),
        ),
      ),
    );
  }

  bool _canGoPrevious() {
    return currentPlantIndex > 0;
  }

  bool _canGoNext() {
    return garden != null &&
        currentPlantIndex <
            garden!.plants.length - 1;
  }

  void _previousPlant() {
    if (!_canGoPrevious()) {
      return;
    }

    setState(() {
      currentPlantIndex--;
    });
  }

  void _nextPlant() {
    if (!_canGoNext()) {
      return;
    }

    setState(() {
      currentPlantIndex++;
    });
  }

  Future<void> _waterPlant(
    UserPlant plant,
  ) async {
    try {
      await gardenService.waterPlant(
        plant.topicId,
      );

      await loadGarden();
    } catch (e) {
      if (!mounted) return;

      if (e.toString().contains(
            'INSUFFICIENT_WATER_CURRENCY',
          )) {
        _showInsufficientCurrencyDialog();
        return;
      }

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Failed to water plant.',
          ),
        ),
      );
    }
  }

  void _showInsufficientCurrencyDialog() {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text(
            'Not Enough Water',
          ),
          content: const Text(
            'You do not have enough water '
            'currency to water this plant.',
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }
}

