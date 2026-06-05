import 'package:flutter/material.dart';
import 'package:frontend/features/home/screens/home_page.dart';

import '../auth/screens/login_page.dart';
import '../auth/screens/register_page.dart';
import '../auth/screens/forgot_password_page.dart';

import 'auth_wrapper.dart';

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,

      title: 'Finance App',

      theme: ThemeData.light(),

      darkTheme: ThemeData.dark(),

      themeMode: ThemeMode.dark,

      home: const AuthWrapper(),

      routes: {
        '/login': (_) => const LoginPage(),
        '/register': (_) => const RegisterPage(),
        '/forgot-password': (_) => const ForgotPasswordPage(),
        '/home': (_) => const HomePage(),
      },
    );
  }
}