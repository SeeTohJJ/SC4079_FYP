import 'package:flutter/material.dart';

import '../core/storage/secure_storage_service.dart';
import '../auth/screens/login_page.dart';
import '../home/screens/home_page.dart';

class AuthWrapper extends StatefulWidget {
  const AuthWrapper({super.key});

  @override
  State<AuthWrapper> createState() => _AuthWrapperState();
}

class _AuthWrapperState extends State<AuthWrapper> {

  final storage = SecureStorageService();

  bool loading = true;
  bool loggedIn = false;

  @override
  void initState() {
    super.initState();
    checkLogin();
  }

  Future<void> checkLogin() async {
    print('[AuthWrapper] checkLogin called');
    final token = await storage.getToken();
    print('[AuthWrapper] token from storage: \\${token}');

    if (token != null && token.isNotEmpty) {
      print('[AuthWrapper] User is logged in');
      loggedIn = true;
    } else {
      print('[AuthWrapper] No valid token, user is not logged in');
    }

    setState(() {
      loading = false;
    });
  }

  @override
  Widget build(BuildContext context) {

    if (loading) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }

    if (loggedIn) {
      return const HomePage();
    }

    return const LoginPage();
  }
}