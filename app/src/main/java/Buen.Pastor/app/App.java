package Buen.Pastor.app;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

public class App extends Application {
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp((DumperPluginsProvider) new SampleDumperPluginsProvider(context))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                        .build()
        );
    }

    public static class SampleDumperPluginsProvider implements DumperPluginsProvider {
        private Context context;

        public SampleDumperPluginsProvider(Context context) {
            this.context = context;
        }

        @Override
        public Iterable<DumperPlugin> get() {
            return Stetho.defaultDumperPluginsProvider(context).get();
        }
    }
}