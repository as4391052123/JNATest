package test.icegalaxy;

import com.sun.jna.Library;

import com.sun.jna.Native;
import com.sun.jna.Platform;

/** Simple example of native library declaration and usage. */
public class HelloWorld {
   

    public static void main(String[] args) {
    	System.out.println(TestSPApi.SPApiDll.INSTANCE.SPAPI_Initialize());
    }
}
