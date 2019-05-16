#import "SystemSettingsPlugin.h"
#import <system_settings/system_settings-Swift.h>

@implementation SystemSettingsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftSystemSettingsPlugin registerWithRegistrar:registrar];
}
@end
