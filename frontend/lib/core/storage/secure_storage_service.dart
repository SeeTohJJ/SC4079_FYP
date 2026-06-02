import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorageService {

  final FlutterSecureStorage storage =
      const FlutterSecureStorage();

  static const String jwtKey = "jwt";

  Future<void> saveToken(String token) async {
    await storage.write(
      key: jwtKey,
      value: token,
    );
  }

  Future<String?> getToken() async {
    final token = await storage.read(key: jwtKey);
    print('[SecureStorageService] Read token: \\${token}');
    return token;
  }

  Future<void> clearToken() async {
    await storage.delete(key: jwtKey);
  }
}