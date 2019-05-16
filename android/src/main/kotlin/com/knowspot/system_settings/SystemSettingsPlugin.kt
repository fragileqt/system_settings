package com.knowspot.system_settings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class SystemSettingsPlugin: MethodCallHandler, EventChannel.StreamHandler {
  lateinit var receiver: BroadcastReceiver
  var registrar: Registrar

  constructor(registrar: Registrar) {
    this.registrar = registrar
  }

  override fun onListen(p0: Any?, p1: EventChannel.EventSink?) {
    receiver = createReceiver(p1)
    registrar.context().registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
  }

  override fun onCancel(p0: Any?) {
    registrar.context().unregisterReceiver(receiver)
  }

  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "system_settings")
      val eventChannel = EventChannel(registrar.messenger(), "system_settings_airplane_mode")
      val instance = SystemSettingsPlugin(registrar)
      channel.setMethodCallHandler(instance)
      eventChannel.setStreamHandler(instance)
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "check") {
      handleCheck(call,result)
    } else {
      result.notImplemented()
    }
  }

  private fun handleCheck(call: MethodCall, result: Result) {
    if(Settings.System.getInt(this.registrar.context().contentResolver,Settings.Global.AIRPLANE_MODE_ON, 0) == 0) {
      result.success("stopped")
    }else{
      result.success("started")
    }
  }

  fun createReceiver(events: EventChannel.EventSink?): BroadcastReceiver {
    return object : BroadcastReceiver() {
      override fun onReceive(p0: Context?, p1: Intent?) {
        if(Settings.System.getInt(p0?.contentResolver,Settings.Global.AIRPLANE_MODE_ON, 0) == 0) {
          events?.success("stopped")
        }else{
          events?.success("started")
        }
      }
    }
  }

}
