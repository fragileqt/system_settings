import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:system_settings/system_settings.dart';

void main() {
  const MethodChannel channel = MethodChannel('system_settings');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await SystemSettings.platformVersion, '42');
  });
}
