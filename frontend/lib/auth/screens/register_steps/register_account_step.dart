import 'package:flutter/material.dart';
import '../../models/register_data.dart';

class RegisterAccountStep extends StatefulWidget {

  final RegisterData data;
  final VoidCallback onNext;

  const RegisterAccountStep({
    super.key,
    required this.data,
    required this.onNext,
  });

  @override
  State<RegisterAccountStep> createState() =>
      _RegisterAccountStepState();
}

class _RegisterAccountStepState
    extends State<RegisterAccountStep> {

  final emailController = TextEditingController();

  final passwordController =
      TextEditingController();

  final confirmPasswordController =
      TextEditingController();

  void continueStep() {

    final email = emailController.text.trim();
    final password = passwordController.text.trim();
    final confirmPassword =
        confirmPasswordController.text.trim();

    if (email.isEmpty ||
        password.isEmpty ||
        confirmPassword.isEmpty) {

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Please fill all fields"),
        ),
      );

      return;
    }

    if (password != confirmPassword) {

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Passwords do not match"),
        ),
      );

      return;
    }

    if (password.length < 8) {

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            "Password must be at least 8 characters",
          ),
        ),
      );

      return;
    }

    widget.data.email = email;
    widget.data.password = password;

    widget.onNext();
  }

  @override
  Widget build(BuildContext context) {

    return Padding(
      padding: const EdgeInsets.all(16),

      child: Column(
        mainAxisAlignment:
            MainAxisAlignment.center,

        children: [

          const Text(
            "Create Account",
            style: TextStyle(
              fontSize: 28,
              fontWeight: FontWeight.bold,
            ),
          ),

          const SizedBox(height: 32),

          TextField(
            controller: emailController,
            decoration: const InputDecoration(
              labelText: "Email",
              border: OutlineInputBorder(),
            ),
          ),

          const SizedBox(height: 16),

          TextField(
            controller: passwordController,
            obscureText: true,
            decoration: const InputDecoration(
              labelText: "Password",
              border: OutlineInputBorder(),
            ),
          ),

          const SizedBox(height: 16),

          TextField(
            controller: confirmPasswordController,
            obscureText: true,
            decoration: const InputDecoration(
              labelText: "Confirm Password",
              border: OutlineInputBorder(),
            ),
          ),

          const SizedBox(height: 24),

          SizedBox(
            width: double.infinity,

            child: ElevatedButton(
              onPressed: continueStep,
              child: const Text("Next"),
            ),
          ),
        ],
      ),
    );
  }
}