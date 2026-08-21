import 'package:flutter/material.dart';
import 'package:frontend/auth/screens/change_password_page.dart';
import 'package:provider/provider.dart';

import 'package:frontend/core/storage/secure_storage_service.dart';
import 'package:frontend/core/theme/app_theme.dart';

class SettingsPage extends StatelessWidget {
  const SettingsPage({super.key});

  @override
  Widget build(BuildContext context) {
    final appTheme = context.watch<AppTheme>();

    return Scaffold(
      appBar: AppBar(
        title: const Text('Settings'),
      ),

      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(20),

          child: Column(
            crossAxisAlignment:
                CrossAxisAlignment.start,

            children: [

              const Text(
                'Appearance',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 8),

              Card(
                child: ListTile(
                  leading: const Icon(
                    Icons.palette_outlined,
                  ),

                  title: const Text(
                    'Accent Color',
                  ),

                  subtitle: const Text(
                    'Customize the app\'s color',
                  ),

                  trailing: CircleAvatar(
                    radius: 14,
                    backgroundColor:
                        appTheme.accentColor,
                  ),

                  onTap: () {
                    _showAccentColorPicker(
                      context,
                      appTheme,
                    );
                  },
                ),
              ),

              const SizedBox(height: 8),

              Card(
                child: ListTile(
                  leading: const Icon(
                    Icons.dark_mode_outlined,
                  ),

                  title: const Text(
                    'Theme',
                  ),

                  subtitle: Text(
                    _getThemeName(appTheme.themeMode),
                  ),

                  trailing: const Icon(
                    Icons.chevron_right,
                  ),

                  onTap: () {
                    _showThemePicker(
                      context,
                      appTheme,
                    );
                  },
                ),
              ),

              const SizedBox(height: 24),

              const Text(
                'Account',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 8),

              Card(
                child: ListTile(
                  leading: const Icon(
                    Icons.lock_outline,
                  ),

                  title: const Text(
                    'Change Password',
                  ),

                  subtitle: const Text(
                    'Update your account password',
                  ),

                  trailing: const Icon(
                    Icons.chevron_right,
                  ),

                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (_) =>
                            const ChangePasswordPage(),
                      ),
                    );
                  },
                ),
              ),

              const Spacer(),

              SizedBox(
                width: double.infinity,
                height: 52,

                child: ElevatedButton.icon(
                  onPressed: () async {

                    final storage =
                        SecureStorageService();

                    await storage.clearToken();

                    if (!context.mounted) return;

                    Navigator.pushReplacementNamed(
                      context,
                      '/login',
                    );
                  },

                  icon: const Icon(
                    Icons.logout,
                  ),

                  label: const Text(
                    'Logout',
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
                  ),

                  style: ElevatedButton.styleFrom(
                    backgroundColor:
                        Colors.red.shade600,

                    foregroundColor:
                        Colors.white,

                    elevation: 2,

                    shape:
                        RoundedRectangleBorder(
                      borderRadius:
                          BorderRadius.circular(30),
                    ),
                  ),
                ),
              ),

              const SizedBox(height: 8),
            ],
          ),
        ),
      ),
    );
  }

  void _showAccentColorPicker(
    BuildContext context,
    AppTheme appTheme,
  ) {

    final colors = [
      const Color(0xFF6C63FF), // Purple
      const Color(0xFF2196F3), // Blue
      const Color(0xFF00ACC1), // Cyan
      const Color(0xFF4CAF50), // Green
      const Color(0xFFFF9800), // Orange
      const Color(0xFFE91E63), // Pink
      const Color(0xFFEF5350), // Red
      const Color(0xFF9C27B0), // Violet
    ];

    showModalBottomSheet(
      context: context,

      builder: (context) {
        return Padding(
          padding: const EdgeInsets.all(24),

          child: Column(
            mainAxisSize:
                MainAxisSize.min,

            crossAxisAlignment:
                CrossAxisAlignment.start,

            children: [

              const Text(
                'Choose Accent Color',
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 8),

              const Text(
                'Choose a color for your app.',
              ),

              const SizedBox(height: 24),

              Wrap(
                spacing: 18,
                runSpacing: 18,

                children: colors.map(
                  (color) {

                    final selected =
                        appTheme.accentColor.value ==
                        color.value;

                    return GestureDetector(
                      onTap: () async {

                        await appTheme
                            .setAccentColor(
                          color,
                        );

                        if (context.mounted) {
                          Navigator.pop(context);
                        }
                      },

                      child: Container(
                        width: 55,
                        height: 55,

                        decoration:
                            BoxDecoration(
                          color: color,
                          shape:
                              BoxShape.circle,

                          border: selected
                              ? Border.all(
                                  color:
                                      Colors.black,
                                  width: 3,
                                )
                              : null,
                        ),

                        child: selected
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

              const SizedBox(height: 16),
            ],
          ),
        );
      },
    );
  }

  String _getThemeName(ThemeMode mode) {
    switch (mode) {
      case ThemeMode.light:
        return 'Light';

      case ThemeMode.dark:
        return 'Dark';

      case ThemeMode.system:
        return 'System default';
    }
  }

  void _showThemePicker(BuildContext context, AppTheme appTheme) {
    showModalBottomSheet(
      context: context,

      builder: (context) {
        return Padding(
          padding: const EdgeInsets.all(24),

          child: Column(
            mainAxisSize: MainAxisSize.min,

            crossAxisAlignment:
                CrossAxisAlignment.start,

            children: [

              const Text(
                'Choose Theme',
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 8),

              const Text(
                'Choose how FinLit should look.',
              ),

              const SizedBox(height: 24),

              _buildThemeOption(
                context,
                appTheme,
                ThemeMode.light,
                'Light',
                Icons.light_mode,
              ),

              _buildThemeOption(
                context,
                appTheme,
                ThemeMode.dark,
                'Dark',
                Icons.dark_mode,
              ),

              _buildThemeOption(
                context,
                appTheme,
                ThemeMode.system,
                'System default',
                Icons.settings_suggest,
              ),

              const SizedBox(height: 8),
            ],
          ),
        );
      },
    );
  }

  Widget _buildThemeOption(
    BuildContext context,
    AppTheme appTheme,
    ThemeMode mode,
    String title,
    IconData icon,
  ) {
    final selected =
        appTheme.themeMode == mode;

    return ListTile(
      leading: Icon(
        icon,
        color: selected
            ? appTheme.accentColor
            : null,
      ),

      title: Text(title),

      trailing: selected
          ? Icon(
              Icons.check,
              color: appTheme.accentColor,
            )
          : null,

      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(12),
      ),

      onTap: () async {
        await appTheme.setThemeMode(mode);

        if (!context.mounted) return;

        Navigator.pop(context);
      },
    );
  }
}