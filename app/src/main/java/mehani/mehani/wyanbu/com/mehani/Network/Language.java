package mehani.mehani.wyanbu.com.mehani.Network;

import android.content.Context;

import java.util.Locale;

/**
 * Created by Anas on 9/15/2017.
 */

public class Language {

    public void change(Context context, String language){
        Locale locale3 = new Locale(language);
        Locale.setDefault(locale3);
        android.content.res.Configuration config3 = new android.content.res.Configuration();
        config3.locale = locale3;
        context.getResources().updateConfiguration(config3, context.getResources().getDisplayMetrics());

    }


}
