import 'package:flutter/material.dart';
import '../screens/reset_password_page.dart';
import '../services/auth_service.dart';

class VerifyOtpPage extends StatefulWidget {
  final String email;

  const VerifyOtpPage({
    super.key,
    required this.email,
  });

  @override
  State<VerifyOtpPage> createState() => _VerifyOtpPageState();
}

class _VerifyOtpPageState extends State<VerifyOtpPage> {  
  final _otpController = TextEditingController();
  final AuthService _authService = AuthService();


  bool _isLoading = false;

  @override
  void dispose() {
    _otpController.dispose();
    super.dispose();
  }

  Future<void> _verifyOtp() async {
    final otp = _otpController.text.trim();

    if (otp.isEmpty) {
      _showMessage("Please enter the verification code.");
      return;
    }

    setState(() {
      _isLoading = true;
    });

    try {
      final resetToken = await _authService.verifyResetOtp(
        widget.email,
        otp,
      );

      if (!mounted) return;

      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => ResetPasswordPage(
            email: widget.email,
            otp: otp,
            resetToken: resetToken,
          ),
        ),
      );
    } catch (e) {
      if (!mounted) return;

      _showMessage("Invalid or expired verification code.");
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
        title: const Text("Verify Code"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const SizedBox(height: 40),

            const Text(
              "Enter verification code",
              style: TextStyle(
                fontSize: 28,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 12),

            Text(
              "We sent a verification code to ${widget.email}",
            ),

            const SizedBox(height: 32),

            TextField(
              controller: _otpController,
              keyboardType: TextInputType.number,
              maxLength: 6,
              decoration: const InputDecoration(
                labelText: "Verification Code",
                border: OutlineInputBorder(),
              ),
            ),

            const SizedBox(height: 24),

            ElevatedButton(
              onPressed: _isLoading ? null : _verifyOtp,
              child: _isLoading
                  ? const CircularProgressIndicator()
                  : const Text("Verify"),
            ),
          ],
        ),
      ),
    );
  }
}