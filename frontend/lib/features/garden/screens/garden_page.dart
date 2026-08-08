import 'package:flutter/material.dart';
import 'package:frontend/core/storage/secure_storage_service.dart';

class GardenPage extends StatelessWidget {
  const GardenPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Garden"),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () async {
              // Clear token from secure storage
              final storage = SecureStorageService();
              await storage.clearToken();

              // Navigate to login page
              Navigator.pushReplacementNamed(context, '/login');
            },
          ),
        ],
      ),
      body: const Center(
        child: Text(
          "Welcome to Financial Literacy App!",
          style: TextStyle(fontSize: 18),
        ),
      ),
    );
  }
}