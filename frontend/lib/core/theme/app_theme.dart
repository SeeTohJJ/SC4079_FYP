import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AppTheme extends ChangeNotifier {
  static const String _accentColorKey = 'accent_color';
  static const String _themeModeKey = 'theme_mode';

  Color _accentColor = const Color(0xFF6C63FF);
  ThemeMode _themeMode = ThemeMode.dark;

  Color get accentColor => _accentColor;
  ThemeMode get themeMode => _themeMode;

  ThemeData get theme {
    final colorScheme = ColorScheme.fromSeed(
      seedColor: _accentColor,
      brightness: Brightness.light,
    );

    return ThemeData(
      brightness: Brightness.light,
      colorScheme: colorScheme,

      scaffoldBackgroundColor: const Color(0xFFF5F5F5),

      appBarTheme: const AppBarTheme(
        backgroundColor: Color(0xFFF5F5F5),
        foregroundColor: Colors.black,
      ),

      cardTheme: const CardThemeData(
        color: Colors.white,
      ),

      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: _accentColor,
          foregroundColor: Colors.white,
        ),
      ),

      progressIndicatorTheme: ProgressIndicatorThemeData(
        color: _accentColor,
      ),
    );
  }

  ThemeData get darkTheme {
    final colorScheme = ColorScheme.fromSeed(
      seedColor: _accentColor,
      brightness: Brightness.dark,
    );

    return ThemeData(
      brightness: Brightness.dark,
      colorScheme: colorScheme,

      scaffoldBackgroundColor: const Color(0xFF121212),

      appBarTheme: const AppBarTheme(
        backgroundColor: Color(0xFF121212),
        foregroundColor: Colors.white,
      ),

      cardTheme: const CardThemeData(
        color: Color(0xFF1E1E1E),
      ),

      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: _accentColor,
          foregroundColor: Colors.white,
        ),
      ),

      progressIndicatorTheme: ProgressIndicatorThemeData(
        color: _accentColor,
      ),
    );
  }

  Future<void> setAccentColor(Color color) async {
    _accentColor = color;

    final prefs = await SharedPreferences.getInstance();

    await prefs.setInt(
      _accentColorKey,
      color.value,
    );

    notifyListeners();
  }

  Future<void> setThemeMode(ThemeMode mode) async {
    _themeMode = mode;

    final prefs = await SharedPreferences.getInstance();

    await prefs.setString(
      _themeModeKey,
      mode.name,
    );

    notifyListeners();
  }

  Future<void> loadTheme() async {
    final prefs = await SharedPreferences.getInstance();

    final savedColor = prefs.getInt(_accentColorKey);

    if (savedColor != null) {
      _accentColor = Color(savedColor);
    }

    final savedTheme = prefs.getString(_themeModeKey);

    if (savedTheme != null) {
      _themeMode = ThemeMode.values.firstWhere(
        (mode) => mode.name == savedTheme,
        orElse: () => ThemeMode.dark,
      );
    }

    notifyListeners();
  }
}