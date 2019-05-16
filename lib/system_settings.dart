import 'dart:async';

import 'package:flutter/services.dart';

class SystemSettings {
  static const MethodChannel _channel =
      const MethodChannel('system_settings');
  static const EventChannel eventChannel = EventChannel(
    'system_settings_airplane_mode',
  );

  Stream<bool> _onConnectivityChanged;
  Stream<bool> get onConnectivityChanged {
    if (_onConnectivityChanged == null) {
      _onConnectivityChanged = eventChannel
          .receiveBroadcastStream()
          .map((dynamic event) => event != "stopped");
    }
    return _onConnectivityChanged;
  }
  Future<bool> checkAirplaneMode() async {
    final String result = await _channel.invokeMethod<String>('check');
    return result != "stopped";
  }
}
