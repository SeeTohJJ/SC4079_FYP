import 'package:flutter/material.dart';
import '../services/auth_service.dart';
import '../screens/login_page.dart';

class ResetPasswordPage extends StatefulWidget {
  final String email;
  final String otp;
  final String resetToken;

  const ResetPasswordPage({
    super.key,
    required this.email,
    required this.otp,
    required this.resetToken,
  });

  @override
  State<ResetPasswordPage> createState() => _ResetPasswordPageState();
}

class _ResetPasswordPageState extends State<ResetPasswordPage> {
  final _passwordController = TextEditingController();
  final _confirmPasswordController = TextEditingController();
  final AuthService _authService = AuthService();

  bool _isLoading = false;

  @override
  void dispose() {
    _passwordController.dispose();
    _confirmPasswordController.dispose();
    super.dispose();
  }

  Future<void> _resetPassword() async {
    final password = _passwordController.text;
    final confirmPassword = _confirmPasswordController.text;

    if (password.isEmpty || confirmPassword.isEmpty) {
      _showMessage("Please fill in all fields.");
      return;
    }

    if (password != confirmPassword) {
      _showMessage("Passwords do not match.");
      return;
    }

    if (password.length < 8) {
      _showMessage("Password must be at least 8 characters.");
      return;
    }

    setState(() {
      _isLoading = true;
    });

    try {
      await _authService.resetPassword(
        resetToken: widget.resetToken,
        newPassword: password,
      );

      if (!mounted) return;

      _showMessage("Password successfully reset.");

      Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(
          builder: (_) => const LoginPage(),
        ),
        (route) => false,
      );
    } catch (e) {
      if (!mounted) return;

      _showMessage(
        "Unable to reset password. The code may have expired.",
      );
    } finally {
      if (mounted) {
        setState(() {
          _isLoading = false;
        });
      }
    }
  }

  void _showMessage(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Reset Password"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const SizedBox(height: 40),

            const Text(
              "Create a new password",
              style: TextStyle(
                fontSize: 28,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 32),

            TextField(
              controller: _passwordController,
              obscureText: true,
              decoration: const InputDecoration(
                labelText: "New Password",
                border: OutlineInputBorder(),
              ),
            ),

            const SizedBox(height: 16),

            TextField(
              controller: _confirmPasswordController,
              obscureText: true,
              decoration: const InputDecoration(
                labelText: "Confirm Password",
                border: OutlineInputBorder(),
              ),
            ),

            const SizedBox(height: 24),

            ElevatedButton(
              onPressed: _isLoading ? null : _resetPassword,
              child: _isLoading
                  ? const CircularProgressIndicator()
                  : const Text("Reset Password"),
            ),
          ],
        ),
      ),
    );
  }
}