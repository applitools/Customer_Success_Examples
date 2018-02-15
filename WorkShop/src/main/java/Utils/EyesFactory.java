package Utils;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Eyes;

public abstract class EyesFactory {

    private static BatchInfo batch = null;

    private static ThreadLocal<Eyes> localEyes_ = new ThreadLocal<Eyes>() {
        @Override
        protected Eyes initialValue() {
            Eyes eyes = new Eyes();
            eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

            eyes.setForceFullPageScreenshot(true);

            if (batch!=null){
                eyes.setBatch(batch);
            }

            return eyes;
        }
    };

    public static Eyes get() {
        return localEyes_.get();
    }

    public static void setBatch(String BatchName) {
        batch = new BatchInfo(BatchName);

    }
}
