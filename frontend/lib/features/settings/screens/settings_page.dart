import 'package:flutter/material.dart';
import 'package:frontend/core/storage/secure_storage_service.dart';

class SettingsPage extends StatelessWidget {
  const SettingsPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Settings'),
      ),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            children: [
              // Main content area
              const Expanded(
                child: Center(
                  child: Text(
                    "Welcome to the Settings Page!",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
              ),

              // Full-width rounded Logout Button
              SizedBox(
                width: double.infinity, // Spans all available horizontal space
                height: 52, // Standard tap height
                child: ElevatedButton.icon(
                  onPressed: () async {
                    // Clear token from secure storage
                    final storage = SecureStorageService();
                    await storage.clearToken();

                    // Navigate to login page
                    Navigator.pushReplacementNamed(context, '/login');
                  },
                  icon: const Icon(Icons.logout),
                  label: const Text(
                    "Logout",
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.red.shade600,
                    foregroundColor: Colors.white,
                    elevation: 2,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0), // Rounded pill edges
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}