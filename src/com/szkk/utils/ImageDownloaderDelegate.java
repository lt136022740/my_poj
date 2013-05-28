//#ifdef PACKAGE_NAME
//#expand package %PACKAGE_NAME%.utils;
//@package %PACKAGE_NAME%.utils;
//#else
package com.szkk.utils;
//#endif

import android.graphics.drawable.Drawable;

public interface ImageDownloaderDelegate {
	void didFinishedDownImage(Drawable drawable);
	void didFailedDownImage(int error);
}
