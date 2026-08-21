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

  bool _hasMinLength = false;
  bool _hasUppercase = false;
  bool _hasLowercase = false;
  bool _hasSpecialCharacter = false;

  @override
  void dispose() {
    _passwordController.dispose();
    _confirmPasswordController.dispose();
    super.dispose();
  }

  void _checkPasswordRequirements(String password) {
    setState(() {
      _hasMinLength = password.length >= 8;
      _hasUppercase = RegExp(r'[A-Z]').hasMatch(password);
      _hasLowercase = RegExp(r'[a-z]').hasMatch(password);
      _hasSpecialCharacter = RegExp(r'[^a-zA-Z0-9]').hasMatch(password);
    });
  }

  Future<void> _resetPassword() async {
    final password = _passwordController.text;
    final confirmPassword = _confirmPasswordController.text;

    if (password.isEmpty || confirmPassword.isEmpty) {
      _showMessage("Please fill in all fields.");
      return;
    }

    if (!_hasMinLength || !_hasUppercase || !_hasLowercase || !_hasSpecialCharacter) {
      _showMessage("Password does not meet the requirements.");
      return;
    }

    if (password != confirmPassword) {
      _showMessage("Passwords do not match.");
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

  Widget _passwordRequirement(String text, bool satisfied) {
    return Row(
      children: [
        Icon(
          satisfied? Icons.check_circle : Icons.cancel,
          size: 18,
          color: satisfied
              ? Colors.green
              : Colors.grey,
        ),

        const SizedBox(width: 8),

        Text(
          text,
          style: TextStyle(
            color: satisfied ? Colors.green : Colors.grey,
          ),
        ),
      ],
    );
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
              onChanged: _checkPasswordRequirements,
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

            const SizedBox(height: 12),

            Column(
              crossAxisAlignment:
                  CrossAxisAlignment.start,
              children: [
                _passwordRequirement("At least 8 characters", _hasMinLength),

                const SizedBox(height: 4),

                _passwordRequirement("At least 1 uppercase letter", _hasUppercase),
                const SizedBox(height: 4),

                _passwordRequirement("At least 1 lowercase letter", _hasLowercase),

                const SizedBox(height: 4),

                _passwordRequirement("At least 1 special character", _hasSpecialCharacter),
              ],
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