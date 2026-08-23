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
  State<RegisterAccountStep> createState() => _RegisterAccountStepState();
}

class _RegisterAccountStepState extends State<RegisterAccountStep> {

  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  final confirmPasswordController = TextEditingController();

  bool hasMinLength = false;
  bool hasUppercase = false;
  bool hasLowercase = false;
  bool hasSpecialCharacter = false;

  bool obscurePassword = true;
  bool obscureConfirmPassword = true;

  void checkPasswordRequirements(String password) {
    setState(() {
      hasMinLength = password.length >= 8;
      hasUppercase = RegExp(r'[A-Z]').hasMatch(password);
      hasLowercase = RegExp(r'[a-z]').hasMatch(password);
      hasSpecialCharacter = RegExp(r'[^a-zA-Z0-9]').hasMatch(password);
    });
  }

  void continueStep() {
    final email = emailController.text.trim();
    final password = passwordController.text;
    final confirmPassword = confirmPasswordController.text;

    if (email.isEmpty || password.isEmpty || confirmPassword.isEmpty) {

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Please fill all fields"),
        ),
      );

      return;
    }

    if (!hasMinLength || !hasUppercase || !hasLowercase || !hasSpecialCharacter) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            "Password does not meet the requirements",
          ),
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

    widget.data.email = email;
    widget.data.password = password;

    widget.onNext();
  }

  Widget passwordRequirement(String text, bool satisfied) {
    return Row(
      children: [
        Icon(
          satisfied
              ? Icons.check_circle
              : Icons.cancel,
          size: 18,
          color: satisfied
              ? Colors.green
              : Colors.grey,
        ),
        const SizedBox(width: 8),
        Text(
          text,
          style: TextStyle(
            color: satisfied
                ? Colors.green
                : Colors.grey,
          ),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {

    return SingleChildScrollView(
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
              border: UnderlineInputBorder(),
            ),
          ),

          const SizedBox(height: 16),

          TextField(
            controller: passwordController,
            obscureText: obscurePassword,
            decoration: InputDecoration(
              labelText: "Password",
              border: const UnderlineInputBorder(),
              suffixIcon: GestureDetector(
                onTapDown: (_) {
                  setState(() {
                    obscurePassword = false;
                  });
                },
                onTapUp: (_) {
                  setState(() {
                    obscurePassword = true;
                  });
                },
                onTapCancel: () {
                  setState(() {
                    obscurePassword = true;
                  });
                },
                child: const Icon(Icons.visibility),
              ),
            ),
            onChanged: checkPasswordRequirements,
          ),

          const SizedBox(height: 16),

          TextField(
            controller: confirmPasswordController,
            obscureText: obscureConfirmPassword,
            decoration: InputDecoration(
              labelText: "Confirm Password",
              border: const UnderlineInputBorder(),
              suffixIcon: GestureDetector(
                onTapDown: (_) {
                  setState(() {
                    obscureConfirmPassword = false;
                  });
                },
                onTapUp: (_) {
                  setState(() {
                    obscureConfirmPassword = true;
                  });
                },
                onTapCancel: () {
                  setState(() {
                    obscureConfirmPassword = true;
                  });
                },
                child: const Icon(Icons.visibility),
              ),
            ),
          ),

          const SizedBox(height: 12),

          Column(
            crossAxisAlignment:
                CrossAxisAlignment.start,
            children: [
              passwordRequirement("At least 8 characters", hasMinLength),

              const SizedBox(height: 4),

              passwordRequirement("At least 1 uppercase letter", hasUppercase),

              const SizedBox(height: 4),

              passwordRequirement("At least 1 lowercase letter", hasLowercase),

              const SizedBox(height: 4),

              passwordRequirement("At least 1 special character", hasSpecialCharacter),
            ],
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