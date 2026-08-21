import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../core/theme/app_theme.dart';
import 'auth_wrapper.dart';

import '../auth/screens/login_page.dart';
import '../auth/screens/register_page.dart';
import '../auth/screens/forgot_password_page.dart';
import '../features/garden/screens/garden_page.dart';

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  final AppTheme appTheme = AppTheme();

  @override
  void initState() {
    super.initState();

    appTheme.loadTheme();
  }

  @override
  void dispose() {
    appTheme.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {

    return ChangeNotifierProvider<AppTheme>.value(
      value: appTheme,

      child: Consumer<AppTheme>(
        builder: (context, theme, child) {

          return MaterialApp(
            debugShowCheckedModeBanner: false,

            title: 'FinLit',

            theme: theme.theme,
            darkTheme: theme.darkTheme,
            themeMode: theme.themeMode,

            home: const AuthWrapper(),

            routes: {
              '/login': (_) => const LoginPage(),
              '/register': (_) => const RegisterPage(),
              '/forgot-password': (_) => const ForgotPasswordPage(),
              '/garden': (_) => const GardenPage(),
            },
          );
        },
      ),
    );
  }
}