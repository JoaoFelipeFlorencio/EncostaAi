package encostai.encostai.com.br.encostaai.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    public static boolean permissionsValidate(int requestCode, Activity activity, String[] permissions){
        //se versao for maior que 23 requisita permicao
        if(Build.VERSION.SDK_INT >= 23){

            List<String> permissionList = new ArrayList<String>();

            //Verifica as permicoes ja foram concedidas
            for (String permission: permissions ) {
                Boolean permissionValidate = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if(!permissionValidate){
                    permissionList.add(permission);
                }
            }

            //requisita permicoes que ainda n foram concedidas
            if(permissionList.isEmpty()) {
                return true;
            }else{
                String[] newPermissions = new String[permissionList.size()];
                permissionList.toArray(newPermissions);
                ActivityCompat.requestPermissions(activity, newPermissions, requestCode);
            }
        }
        return true;
    }
}
