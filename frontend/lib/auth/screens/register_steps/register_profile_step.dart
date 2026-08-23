import 'package:flutter/material.dart';
import 'package:country_picker/country_picker.dart';
import 'package:flutter/services.dart';
import '../../models/register_data.dart';

class RegisterProfileStep extends StatefulWidget {

  final RegisterData data;

  final VoidCallback onNext;
  final VoidCallback onBack;

  const RegisterProfileStep({
    super.key,
    required this.data,
    required this.onNext,
    required this.onBack,
  });

  @override
  State<RegisterProfileStep> createState() =>
      _RegisterProfileStepState();
}

class _RegisterProfileStepState
    extends State<RegisterProfileStep> {

  final usernameController =TextEditingController();
  final ageController = TextEditingController();
  final incomeController = TextEditingController();

  String selectedEmplomymentStatus = "Employed";
  String selectedGender = "Male";
  String selectedCountry = "Singapore";

  void continueStep() {

    if (usernameController.text.trim().isEmpty) {

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Username cannot be empty"),
        ),
      );

      return;
    }

    widget.data.username = usernameController.text.trim();

    widget.data.gender = selectedGender;

    widget.data.age = int.tryParse(ageController.text.trim()) ?? 0;
    widget.data.income = int.tryParse(incomeController.text.trim()) ?? 0;
    widget.data.employmentStatus = selectedEmplomymentStatus;
    widget.data.country = selectedCountry;  

    widget.onNext();
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
            "Profile Information",
            style: TextStyle(
              fontSize: 28,
              fontWeight: FontWeight.bold,
            ),
          ),

          const SizedBox(height: 32),

          TextField(
            controller: usernameController,
            decoration: const InputDecoration(
              labelText: "Username",
              border: OutlineInputBorder(),
            ),
          ),

          const SizedBox(height: 24),

          DropdownButtonFormField<String>(
            initialValue: selectedGender,

            decoration: const InputDecoration(
              labelText: "Gender",
              border: OutlineInputBorder(),
            ),

            items: const [
              DropdownMenuItem(
                value: "Male",
                child: Text("Male"),
              ),
              DropdownMenuItem(
                value: "Female",
                child: Text("Female"),
              ),
              DropdownMenuItem(
                value: "Other",
                child: Text("Other"),
              ),
            ],

            onChanged: (value) {
              setState(() {
                selectedGender = value!;
              });
            },
          ),

          const SizedBox(height: 24),

          TextField(
            controller: ageController,
            decoration: const InputDecoration(
              labelText: "Age",
              border: OutlineInputBorder(),
            ),
            keyboardType: TextInputType.number,
            inputFormatters: [
              FilteringTextInputFormatter.digitsOnly,
            ],
          ),

          const SizedBox(height: 24),

          DropdownButtonFormField<String>(
            initialValue: selectedEmplomymentStatus,

            decoration: const InputDecoration(
              labelText: "Employment Status",
              border: OutlineInputBorder(),
            ),

            items: const [
              DropdownMenuItem(
                value: "Employed",
                child: Text("Employed"),
              ),
              DropdownMenuItem(
                value: "Unemployed",
                child: Text("Unemployed"),
              ),
              DropdownMenuItem(
                value: "Self-Employed",
                child: Text("Self-Employed"),
              ),
              DropdownMenuItem(
                value: "Student",
                child: Text("Student"),
              ),
              DropdownMenuItem(
                value: "Other",
                child: Text("Other"),
              ),
            ],

            onChanged: (value) {
              setState(() {
                selectedEmplomymentStatus = value!;
              });
            },
          ),

          const SizedBox(height: 24),

          TextField(
            controller: incomeController,
            decoration: const InputDecoration(
              labelText: "Income",
              border: OutlineInputBorder(),
            ),
            keyboardType: TextInputType.number,
            inputFormatters: [
              FilteringTextInputFormatter.digitsOnly,
            ],
          ),

          const SizedBox(height: 24),

          TextField(
            readOnly: true,
            decoration: InputDecoration(
              labelText: "Country",
              border: OutlineInputBorder(),
              suffixIcon: Icon(Icons.arrow_drop_down),
            ),
            controller: TextEditingController(
              text: selectedCountry,
            ),
            onTap: () {
              showCountryPicker(
                context: context,
                showPhoneCode: false,
                onSelect: (Country country) {
                  setState(() {
                    selectedCountry = country.name;
                  });
                },
              );
            },
          ),

          const SizedBox(height: 32),

          Row(
            children: [

              Expanded(
                child: OutlinedButton(
                  onPressed: widget.onBack,
                  child: const Text("Back"),
                ),
              ),

              const SizedBox(width: 16),

              Expanded(
                child: ElevatedButton(
                  onPressed: continueStep,
                  child: const Text("Next"),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}