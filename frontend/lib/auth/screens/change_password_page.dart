import 'package:flutter/material.dart';
import '../services/auth_service.dart';

class ChangePasswordPage extends StatefulWidget {
  const ChangePasswordPage({super.key});

  @override
  State<ChangePasswordPage> createState() => _ChangePasswordPageState();
}

class _ChangePasswordPageState extends State<ChangePasswordPage> {

  final _currentPasswordController = TextEditingController();
  final _newPasswordController = TextEditingController();
  final _confirmPasswordController = TextEditingController();
  final AuthService _authService = AuthService();

  bool _isLoading = false;

  @override
  void dispose() {
    _currentPasswordController.dispose();
    _newPasswordController.dispose();
    _confirmPasswordController.dispose();

    super.dispose();
  }

  Future<void> _changePassword() async {

    final currentPassword = _currentPasswordController.text;
    final newPassword = _newPasswordController.text;
    final confirmPassword = _confirmPasswordController.text;

    if (currentPassword.isEmpty ||
        newPassword.isEmpty ||
        confirmPassword.isEmpty) {

      _showMessage('Please fill in all fields.');

      return;
    }


    if (newPassword != confirmPassword) {

      _showMessage('New passwords do not match.');

      return;
    }

    if (newPassword.length < 8) {

      _showMessage('Password must be at least 8 characters.');

      return;
    }

    if (!RegExp(r'[A-Z]').hasMatch(newPassword)) {

      _showMessage('Password must contain at least one uppercase letter.');

      return;
    }

    if (!RegExp(r'[a-z]').hasMatch(newPassword)) {

      _showMessage('Password must contain at least one lowercase letter.');

      return;
    }

    if (!RegExp(r'[^a-zA-Z0-9]').hasMatch(newPassword)) {

      _showMessage('Password must contain at least one special character.');

      return;
    }

    setState(() {
      _isLoading = true;
    });

    try {

      await _authService.changePassword(
        currentPassword: currentPassword,
        newPassword: newPassword,
      );

      if (!mounted) return;

      _showMessage('Password successfully changed.');

      Navigator.pop(context);

    } catch (e) {

      if (!mounted) return;

      debugPrint(
        'Change password error: $e',
      );

      _showMessage(
        'Unable to change password. '
        'Please check your current password.',
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
      SnackBar(
        content: Text(message),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text('Change Password'),
      ),

      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),

          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,

            children: [

              const SizedBox(height: 20),

              const Text(
                'Change your password',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 8),

              const Text(
                'Enter your current password '
                'and choose a new one.',
              ),

              const SizedBox(height: 32),

              TextField(
                controller: _currentPasswordController,
                obscureText: true,

                decoration: const InputDecoration(
                  labelText: 'Current Password',
                  border: OutlineInputBorder(),
                ),
              ),

              const SizedBox(height: 16),

              TextField(
                controller: _newPasswordController,
                obscureText: true,

                decoration: const InputDecoration(
                  labelText: 'New Password',
                  border: OutlineInputBorder(),
                ),
              ),

              const SizedBox(height: 16),

              TextField(
                controller: _confirmPasswordController,
                obscureText: true,

                decoration: const InputDecoration(
                  labelText: 'Confirm New Password',
                  border: OutlineInputBorder(),
                ),
              ),

              const SizedBox(height: 12),

              const Text(
                'Password must contain at least 8 characters, '
                'one uppercase letter, one lowercase letter, '
                'and one special character.',
                style: TextStyle(
                  fontSize: 13,
                ),
              ),

              const SizedBox(height: 24),

              ElevatedButton(
                onPressed: _isLoading
                    ? null
                    : _changePassword,

                style: ElevatedButton.styleFrom(
                  minimumSize: const Size.fromHeight(52),
                ),

                child: _isLoading
                    ? const SizedBox(
                        width: 24,
                        height: 24,
                        child: CircularProgressIndicator(),
                      )
                    : const Text(
                        'Change Password',
                      ),
              ),

              const SizedBox(height: 24),
            ],
          ),
        ),
      ),
    );
  }
}