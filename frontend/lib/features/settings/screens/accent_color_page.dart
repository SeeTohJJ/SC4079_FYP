import 'package:flutter/material.dart';
import 'package:frontend/core/theme/app_theme.dart';
import 'package:provider/provider.dart';

class AccentColorPage extends StatelessWidget {
  const AccentColorPage({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    final appTheme =
        Provider.of<AppTheme>(context);

    final colors = [
      Colors.blue,
      Colors.green,
      Colors.orange,
      Colors.purple,
      Colors.red,
      Colors.teal,
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Accent Color',
        ),
      ),

      body: Padding(
        padding: const EdgeInsets.all(24),

        child: Column(
          crossAxisAlignment:
              CrossAxisAlignment.start,

          children: [
            const Text(
              'Choose your accent color',

              style: TextStyle(
                fontSize: 22,
                fontWeight:
                    FontWeight.bold,
              ),
            ),

            const SizedBox(height: 8),

            const Text(
              'This changes the main color '
              'used throughout the app.',
            ),

            const SizedBox(height: 32),

            Wrap(
              spacing: 16,
              runSpacing: 16,

              children: colors.map(
                (color) {

                  final isSelected =
                      appTheme.accentColor.value ==
                      color.value;

                  return GestureDetector(
                    onTap: () {
                      appTheme
                          .setAccentColor(color);
                    },

                    child: Container(
                      width: 60,
                      height: 60,

                      decoration:
                          BoxDecoration(
                        color: color,
                        shape:
                            BoxShape.circle,

                        border: isSelected
                            ? Border.all(
                                width: 4,
                                color:
                                    Colors.black,
                              )
                            : null,
                      ),

                      child: isSelected
                          ? const Icon(
                              Icons.check,
                              color:
                                  Colors.white,
                            )
                          : null,
                    ),
                  );
                },
              ).toList(),
            ),
          ],
        ),
      ),
    );
  }
}