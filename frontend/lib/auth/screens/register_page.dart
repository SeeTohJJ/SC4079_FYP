import 'package:flutter/material.dart';

import '../models/register_data.dart';

import 'register_steps/register_account_step.dart';
import 'register_steps/register_profile_step.dart';
import 'register_steps/register_topics_step.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() =>
      _RegisterFlowPageState();
}

class _RegisterFlowPageState
    extends State<RegisterPage> {

  final PageController pageController =
      PageController();

  final RegisterData data = RegisterData();

  int currentStep = 0;

  void nextStep() {

    if (currentStep < 2) {

      setState(() {
        currentStep++;
      });

      pageController.nextPage(
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    }
  }

  void previousStep() {

    if (currentStep > 0) {

      setState(() {
        currentStep--;
      });

      pageController.previousPage(
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    }
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(

      appBar: AppBar(
        title: Text(
          "Step ${currentStep + 1} of 3",
        ),
      ),

      body: PageView(
        controller: pageController,

        physics:
            const NeverScrollableScrollPhysics(),

        children: [

          RegisterAccountStep(
            data: data,
            onNext: nextStep,
          ),

          RegisterProfileStep(
            data: data,
            onNext: nextStep,
            onBack: previousStep,
          ),

          RegisterTopicsStep(
            data: data,
            onBack: previousStep,
          ),
        ],
      ),
    );
  }
}