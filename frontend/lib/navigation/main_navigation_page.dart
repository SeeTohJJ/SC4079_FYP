import 'package:flutter/material.dart';
import 'package:frontend/common/widgets/app_bottom_nav_bar.dart';
import 'package:frontend/features/progress/screens/progress_page.dart';
import 'package:frontend/features/settings/screens/settings_page.dart';
import 'package:frontend/features/study/screens/study_page.dart';
import 'package:frontend/features/home/screens/home_page.dart';


class MainNavigationPage extends StatefulWidget {
  const MainNavigationPage({super.key});

  @override
  State<MainNavigationPage> createState() => _MainNavigationPageState();
}

class _MainNavigationPageState extends State<MainNavigationPage> {
  int currentIndex = 0;

  final pages = [
    HomePage(),
    StudyPage(),
    ProgressPage(),
    SettingsPage(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: pages[currentIndex],
      bottomNavigationBar: AppBottomNavBar(
        currentIndex: currentIndex,
        onTap: (index) {
          setState(() {
            currentIndex = index;
          });
        },
      ),
    );
  }
}