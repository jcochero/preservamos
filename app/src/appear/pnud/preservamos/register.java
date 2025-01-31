package appear.pnud.preservamos;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class register extends Activity implements B4AActivity{
	public static register mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.register");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (register).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.register");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.register", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (register) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (register) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return register.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (register) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (register) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            register mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (register) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtuserid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunmaskpass = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _cmbmunicipio = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabregister = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprev = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkempleadomunicipio = null;
public appear.pnud.preservamos.b4xdialog _dialog = null;
public static String _codigo_enviado = "";
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkpoliticas = null;
public static String _actionfail = "";
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.frmdatossinenviar _frmdatossinenviar = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmmunicipioestadisticas _frmmunicipioestadisticas = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.mod_hidro _mod_hidro = null;
public appear.pnud.preservamos.mod_hidro_fotos _mod_hidro_fotos = null;
public appear.pnud.preservamos.mod_residuos _mod_residuos = null;
public appear.pnud.preservamos.mod_residuos_fotos _mod_residuos_fotos = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras _reporte_habitat_rio_sierras = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras_bu _reporte_habitat_rio_sierras_bu = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.character_creation _character_creation = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static void  _action_maininuse(boolean _firsttime) throws Exception{
ResumableSub_action_MainInUse rsub = new ResumableSub_action_MainInUse(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_action_MainInUse extends BA.ResumableSub {
public ResumableSub_action_MainInUse(appear.pnud.preservamos.register parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
appear.pnud.preservamos.register parent;
boolean _firsttime;
int _result = 0;
boolean _completed = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 340;BA.debugLine="If firstTime = True Then";
if (true) break;

case 1:
//if
this.state = 42;
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 23;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 341;BA.debugLine="Msgbox2Async(\"El email (\" & txtEmail.Text & \") y";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El email ("+parent.mostCurrent._txtemail.getText()+") ya está en uso. Podemos enviarte un código a tu email para que puedas acceder."),BA.ObjectToCharSequence("Registro"),"Envíen un código a mi email!","Cancelar","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 342;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 43;
return;
case 43:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 343;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 4:
//if
this.state = 21;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 6;
}else {
this.state = 20;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 346;BA.debugLine="codigo_enviado = utilidades.RandomNumber(4)";
parent.mostCurrent._codigo_enviado = parent.mostCurrent._utilidades._randomnumber /*String*/ (mostCurrent.activityBA,(int) (4));
 //BA.debugLineNum = 347;BA.debugLine="ToastMessageShow(\"Enviando código a \" & txtEmai";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enviando código a "+parent.mostCurrent._txtemail.getText()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 349;BA.debugLine="Wait For (Enviar_Codigo) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _enviar_codigo());
this.state = 44;
return;
case 44:
//C
this.state = 7;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 350;BA.debugLine="If Completed = True Then";
if (true) break;

case 7:
//if
this.state = 18;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 9;
}else {
this.state = 17;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 352;BA.debugLine="Wait For (Codigo_Check) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _codigo_check());
this.state = 45;
return;
case 45:
//C
this.state = 10;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 353;BA.debugLine="If Completed = True Then";
if (true) break;

case 10:
//if
this.state = 15;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 12;
}else {
this.state = 14;
}if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 354;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 355;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 358;BA.debugLine="action_MainInUse(False)";
_action_maininuse(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 15:
//C
this.state = 18;
;
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 362;BA.debugLine="ToastMessageShow(\"No se pudo enviar el código,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo enviar el código, revisa que tengas internet y vuelve a intentarlo"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 18:
//C
this.state = 21;
;
 if (true) break;

case 20:
//C
this.state = 21;
 //BA.debugLineNum = 366;BA.debugLine="ToastMessageShow(\"No se envió el código\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se envió el código"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 367;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 21:
//C
this.state = 42;
;
 if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 370;BA.debugLine="Msgbox2Async(\"El código no es correcto... te env";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El código no es correcto... te enviaremos otro nuevo!"),BA.ObjectToCharSequence("Registro"),"Envíen un código a mi email!","Cancelar","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 371;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 46;
return;
case 46:
//C
this.state = 24;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 372;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 24:
//if
this.state = 41;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 26;
}else {
this.state = 40;
}if (true) break;

case 26:
//C
this.state = 27;
 //BA.debugLineNum = 374;BA.debugLine="codigo_enviado = utilidades.RandomNumber(4)";
parent.mostCurrent._codigo_enviado = parent.mostCurrent._utilidades._randomnumber /*String*/ (mostCurrent.activityBA,(int) (4));
 //BA.debugLineNum = 375;BA.debugLine="ToastMessageShow(\"Enviando código a \" & txtEmai";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enviando código a "+parent.mostCurrent._txtemail.getText()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 376;BA.debugLine="Wait For (Enviar_Codigo) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _enviar_codigo());
this.state = 47;
return;
case 47:
//C
this.state = 27;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 377;BA.debugLine="If Completed = True Then";
if (true) break;

case 27:
//if
this.state = 38;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 29;
}else {
this.state = 37;
}if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 378;BA.debugLine="Wait For (Codigo_Check) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _codigo_check());
this.state = 48;
return;
case 48:
//C
this.state = 30;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 379;BA.debugLine="If Completed = True Then";
if (true) break;

case 30:
//if
this.state = 35;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 32;
}else {
this.state = 34;
}if (true) break;

case 32:
//C
this.state = 35;
 //BA.debugLineNum = 380;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 381;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 384;BA.debugLine="action_MainInUse(False)";
_action_maininuse(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 35:
//C
this.state = 38;
;
 if (true) break;

case 37:
//C
this.state = 38;
 //BA.debugLineNum = 388;BA.debugLine="ToastMessageShow(\"No se pudo enviar el código,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo enviar el código, revisa que tengas internet y vuelve a intentarlo"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 38:
//C
this.state = 41;
;
 if (true) break;

case 40:
//C
this.state = 41;
 //BA.debugLineNum = 392;BA.debugLine="ToastMessageShow(\"No se envió el código\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se envió el código"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 393;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 41:
//C
this.state = 42;
;
 if (true) break;

case 42:
//C
this.state = -1;
;
 //BA.debugLineNum = 399;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static void  _complete(boolean _completed) throws Exception{
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 33;BA.debugLine="Activity.LoadLayout(\"layRegister\")";
mostCurrent._activity.LoadLayout("layRegister",mostCurrent.activityBA);
 //BA.debugLineNum = 35;BA.debugLine="tabRegister.LoadLayout(\"layRegister_Tab1\", \"1\")";
mostCurrent._tabregister.LoadLayout("layRegister_Tab1",BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 36;BA.debugLine="tabRegister.LoadLayout(\"layRegister_Tab2\", \"2\")";
mostCurrent._tabregister.LoadLayout("layRegister_Tab2",BA.ObjectToCharSequence("2"));
 //BA.debugLineNum = 37;BA.debugLine="tabRegister.LoadLayout(\"layRegister_Tab3\", \"3\")";
mostCurrent._tabregister.LoadLayout("layRegister_Tab3",BA.ObjectToCharSequence("3"));
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 40;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 41;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 42;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 44;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _btncancelarregistro_click() throws Exception{
 //BA.debugLineNum = 527;BA.debugLine="Sub btnCancelarRegistro_Click";
 //BA.debugLineNum = 528;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 529;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 530;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 97;BA.debugLine="If tabRegister.CurrentPage = 0 Then";
if (mostCurrent._tabregister.getCurrentPage()==0) { 
 //BA.debugLineNum = 98;BA.debugLine="tabRegister.ScrollTo(1, True)";
mostCurrent._tabregister.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabregister.getCurrentPage()==1) { 
 //BA.debugLineNum = 100;BA.debugLine="tabRegister.ScrollTo(2, True)";
mostCurrent._tabregister.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Private Sub btnPrev_Click";
 //BA.debugLineNum = 104;BA.debugLine="If tabRegister.CurrentPage = 1 Then";
if (mostCurrent._tabregister.getCurrentPage()==1) { 
 //BA.debugLineNum = 105;BA.debugLine="tabRegister.ScrollTo(0, True)";
mostCurrent._tabregister.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabregister.getCurrentPage()==2) { 
 //BA.debugLineNum = 107;BA.debugLine="tabRegister.ScrollTo(1, True)";
mostCurrent._tabregister.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static void  _btnregister_click() throws Exception{
ResumableSub_btnRegister_Click rsub = new ResumableSub_btnRegister_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnRegister_Click extends BA.ResumableSub {
public ResumableSub_btnRegister_Click(appear.pnud.preservamos.register parent) {
this.parent = parent;
}
appear.pnud.preservamos.register parent;
String _struserid = "";
String _stremail = "";
anywheresoftware.b4a.objects.collections.Map _mapreset = null;
anywheresoftware.b4a.objects.collections.List _newuser = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
boolean _completed = false;
String _rndstr = "";

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 118;BA.debugLine="Dim strUserID As String = txtUserID.Text.Trim";
_struserid = parent.mostCurrent._txtuserid.getText().trim();
 //BA.debugLineNum = 119;BA.debugLine="If strUserID = \"\" Then";
if (true) break;

case 1:
//if
this.state = 10;
if ((_struserid).equals("")) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 120;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 4:
//if
this.state = 9;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 6;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 9;
 //BA.debugLineNum = 121;BA.debugLine="MsgboxAsync(\"Ingrese nombre de usuario\", \"Error";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ingrese nombre de usuario"),BA.ObjectToCharSequence("Error"),processBA);
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 123;BA.debugLine="MsgboxAsync(\"Enter a username\", \"Error\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Enter a username"),BA.ObjectToCharSequence("Error"),processBA);
 if (true) break;

case 9:
//C
this.state = 10;
;
 //BA.debugLineNum = 125;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 128;BA.debugLine="Dim strEmail As String = txtEmail.Text.Trim";
_stremail = parent.mostCurrent._txtemail.getText().trim();
 //BA.debugLineNum = 129;BA.debugLine="If strEmail = \"\" Then";
if (true) break;

case 11:
//if
this.state = 20;
if ((_stremail).equals("")) { 
this.state = 13;
}if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 130;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 14:
//if
this.state = 19;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 16;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 18;
}if (true) break;

case 16:
//C
this.state = 19;
 //BA.debugLineNum = 131;BA.debugLine="MsgboxAsync(\"Ingrese email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ingrese email"),BA.ObjectToCharSequence("Error"),processBA);
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 133;BA.debugLine="MsgboxAsync(\"Enter your email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Enter your email"),BA.ObjectToCharSequence("Error"),processBA);
 if (true) break;

case 19:
//C
this.state = 20;
;
 //BA.debugLineNum = 135;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 137;BA.debugLine="If Validate_Email(strEmail) = False Then";

case 20:
//if
this.state = 29;
if (_validate_email(_stremail)==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 22;
}if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 138;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 23:
//if
this.state = 28;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 25;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 27;
}if (true) break;

case 25:
//C
this.state = 28;
 //BA.debugLineNum = 139;BA.debugLine="MsgboxAsync(\"Formato de email incorrecto\", \"Err";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Formato de email incorrecto"),BA.ObjectToCharSequence("Error"),processBA);
 if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 141;BA.debugLine="MsgboxAsync(\"Incorrect format for the email add";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Incorrect format for the email address"),BA.ObjectToCharSequence("Error"),processBA);
 if (true) break;

case 28:
//C
this.state = 29;
;
 //BA.debugLineNum = 143;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 146;BA.debugLine="If chkPoliticas.Checked = False Then";

case 29:
//if
this.state = 32;
if (parent.mostCurrent._chkpoliticas.getChecked()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 31;
}if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 147;BA.debugLine="MsgboxAsync(\"Para seguir con el registro, debe a";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Para seguir con el registro, debe aceptar la política de datos de PreserVamos"),BA.ObjectToCharSequence("Aceptar los términos"),processBA);
 //BA.debugLineNum = 148;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 32:
//C
this.state = 33;
;
 //BA.debugLineNum = 156;BA.debugLine="ProgressDialogShow(\"Registrando el usuario...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Registrando el usuario..."));
 //BA.debugLineNum = 158;BA.debugLine="If txtUserID.Text = \"test_user\"	And txtEmail.Text";
if (true) break;

case 33:
//if
this.state = 56;
if ((parent.mostCurrent._txtuserid.getText()).equals("test_user") && (parent.mostCurrent._txtemail.getText()).equals("test_user@testemail.com")) { 
this.state = 35;
}else {
this.state = 43;
}if (true) break;

case 35:
//C
this.state = 36;
 //BA.debugLineNum = 159;BA.debugLine="ToastMessageShow(\"Welcome Google!\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Welcome Google!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 162;BA.debugLine="Main.username = txtUserID.Text";
parent.mostCurrent._main._username /*String*/  = parent.mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 163;BA.debugLine="Main.strUserEmail = txtEmail.Text";
parent.mostCurrent._main._struseremail /*String*/  = parent.mostCurrent._txtemail.getText();
 //BA.debugLineNum = 164;BA.debugLine="Main.strUserFullName = txtUserID.Text";
parent.mostCurrent._main._struserfullname /*String*/  = parent.mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 165;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (true) break;

case 36:
//if
this.state = 41;
if (parent.mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 38;
}else {
this.state = 40;
}if (true) break;

case 38:
//C
this.state = 41;
 //BA.debugLineNum = 166;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 if (true) break;

case 40:
//C
this.state = 41;
 //BA.debugLineNum = 168;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 if (true) break;

case 41:
//C
this.state = 56;
;
 //BA.debugLineNum = 173;BA.debugLine="Dim MapReset As Map";
_mapreset = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 174;BA.debugLine="MapReset.Initialize";
_mapreset.Initialize();
 //BA.debugLineNum = 175;BA.debugLine="MapReset.Put(\"lastuser\", \"si\")";
_mapreset.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 176;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig\"";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastUser",(Object)("no"),_mapreset);
 //BA.debugLineNum = 180;BA.debugLine="Dim newUser As List";
_newuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 181;BA.debugLine="newUser.Initialize";
_newuser.Initialize();
 //BA.debugLineNum = 182;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 183;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 184;BA.debugLine="m.Put(\"username\", Main.username)";
_m.Put((Object)("username"),(Object)(parent.mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 185;BA.debugLine="m.Put(\"userEmail\", Main.strUserEmail)";
_m.Put((Object)("userEmail"),(Object)(parent.mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 186;BA.debugLine="m.Put(\"userOrg\", Main.strUserOrg)";
_m.Put((Object)("userOrg"),(Object)(parent.mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 187;BA.debugLine="m.Put(\"userTipoUsuario\", Main.strUserTipoUsuario";
_m.Put((Object)("userTipoUsuario"),(Object)(parent.mostCurrent._main._strusertipousuario /*String*/ ));
 //BA.debugLineNum = 189;BA.debugLine="m.Put(\"lastUser\", \"si\")";
_m.Put((Object)("lastUser"),(Object)("si"));
 //BA.debugLineNum = 190;BA.debugLine="m.Put(\"lang\", \"es\")";
_m.Put((Object)("lang"),(Object)("es"));
 //BA.debugLineNum = 191;BA.debugLine="newUser.Add(m)";
_newuser.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 192;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"userconfig\", n";
parent.mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig",_newuser);
 //BA.debugLineNum = 195;BA.debugLine="Log(\"Usuario registrado en DB interna - mail ya";
anywheresoftware.b4a.keywords.Common.LogImpl("06094927","Usuario registrado en DB interna - mail ya existente",0);
 //BA.debugLineNum = 197;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 198;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 43:
//C
this.state = 44;
 //BA.debugLineNum = 201;BA.debugLine="actionFail = \"\"";
parent.mostCurrent._actionfail = "";
 //BA.debugLineNum = 202;BA.debugLine="Wait For (RegisterUser) Complete (Completed As B";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _registeruser());
this.state = 57;
return;
case 57:
//C
this.state = 44;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 203;BA.debugLine="If Completed = True Then";
if (true) break;

case 44:
//if
this.state = 55;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 46;
}else {
this.state = 48;
}if (true) break;

case 46:
//C
this.state = 55;
 //BA.debugLineNum = 204;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 205;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 48:
//C
this.state = 49;
 //BA.debugLineNum = 209;BA.debugLine="If actionFail = \"MailInUse\" Then";
if (true) break;

case 49:
//if
this.state = 54;
if ((parent.mostCurrent._actionfail).equals("MailInUse")) { 
this.state = 51;
}else {
this.state = 53;
}if (true) break;

case 51:
//C
this.state = 54;
 //BA.debugLineNum = 210;BA.debugLine="action_MainInUse(True)";
_action_maininuse(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 53:
//C
this.state = 54;
 //BA.debugLineNum = 212;BA.debugLine="ToastMessageShow(\"No se pudo registrar el usua";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo registrar el usuario, hay un error de servidor"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 214;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 215;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = parent.mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 216;BA.debugLine="Main.username = \"guest_\" & RndStr";
parent.mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 //BA.debugLineNum = 217;BA.debugLine="Main.strUserEmail = Main.username";
parent.mostCurrent._main._struseremail /*String*/  = parent.mostCurrent._main._username /*String*/ ;
 if (true) break;

case 54:
//C
this.state = 55;
;
 if (true) break;

case 55:
//C
this.state = 56;
;
 if (true) break;

case 56:
//C
this.state = -1;
;
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(appear.pnud.preservamos.register parent) {
this.parent = parent;
}
appear.pnud.preservamos.register parent;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 56;BA.debugLine="Msgbox2Async(\"Cerrar PreserVamos?\", \"SALIR\", \"Si\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar PreserVamos?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 57;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 58;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 59;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 60;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 61;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _codigo_check() throws Exception{
ResumableSub_Codigo_Check rsub = new ResumableSub_Codigo_Check(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_Codigo_Check extends BA.ResumableSub {
public ResumableSub_Codigo_Check(appear.pnud.preservamos.register parent) {
this.parent = parent;
}
appear.pnud.preservamos.register parent;
appear.pnud.preservamos.b4xinputtemplate _input = null;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _mapreset = null;
anywheresoftware.b4a.objects.collections.List _newuser = null;
anywheresoftware.b4a.objects.collections.Map _m = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 436;BA.debugLine="dialog.Initialize(Activity)";
parent.mostCurrent._dialog._initialize /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(parent.mostCurrent._activity.getObject())));
 //BA.debugLineNum = 437;BA.debugLine="dialog.Title = \"Código\"";
parent.mostCurrent._dialog._title /*Object*/  = (Object)("Código");
 //BA.debugLineNum = 438;BA.debugLine="Dim input As B4XInputTemplate";
_input = new appear.pnud.preservamos.b4xinputtemplate();
 //BA.debugLineNum = 439;BA.debugLine="input.Initialize";
_input._initialize /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 440;BA.debugLine="input.lblTitle.Text = \"Ingrese el código que envi";
_input._lbltitle /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence("Ingrese el código que enviamos a su email"));
 //BA.debugLineNum = 441;BA.debugLine="input.RegexPattern = \".+\" 'require at least one c";
_input._regexpattern /*String*/  = ".+";
 //BA.debugLineNum = 442;BA.debugLine="input.TextField1.TextColor = Colors.Black";
_input._textfield1 /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 443;BA.debugLine="input.lblTitle.TextColor = Colors.DarkGray";
_input._lbltitle /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 444;BA.debugLine="input.TextField1.TextSize = 24";
_input._textfield1 /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setTextSize((float) (24));
 //BA.debugLineNum = 445;BA.debugLine="input.TextField1.Height = 50dip";
_input._textfield1 /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 446;BA.debugLine="input.ConfigureForNumbers(False,False)";
_input._configurefornumbers /*String*/ (anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 447;BA.debugLine="dialog.TitleBarColor = Colors.ARGB(255,76,159,56)";
parent.mostCurrent._dialog._titlebarcolor /*int*/  = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56));
 //BA.debugLineNum = 448;BA.debugLine="dialog.BackgroundColor = Colors.White";
parent.mostCurrent._dialog._backgroundcolor /*int*/  = anywheresoftware.b4a.keywords.Common.Colors.White;
 //BA.debugLineNum = 450;BA.debugLine="Wait For (dialog.ShowTemplate(input, \"OK\", \"\", \"C";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._dialog._showtemplate /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ ((Object)(_input),(Object)("OK"),(Object)(""),(Object)("Cancelar")));
this.state = 19;
return;
case 19:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 451;BA.debugLine="If Result = xui.DialogResponse_Positive Then";
if (true) break;

case 1:
//if
this.state = 18;
if (_result==parent._xui.DialogResponse_Positive) { 
this.state = 3;
}else {
this.state = 17;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 452;BA.debugLine="If input.Text = codigo_enviado Then";
if (true) break;

case 4:
//if
this.state = 15;
if ((_input._text /*String*/ ).equals(parent.mostCurrent._codigo_enviado)) { 
this.state = 6;
}else {
this.state = 14;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 453;BA.debugLine="Log(\"Codigo correcto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("06357011","Codigo correcto",0);
 //BA.debugLineNum = 454;BA.debugLine="ToastMessageShow(\"Código correcto, bienvenido/a";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Código correcto, bienvenido/a de vuelta!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 457;BA.debugLine="Main.username = txtUserID.Text";
parent.mostCurrent._main._username /*String*/  = parent.mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 458;BA.debugLine="Main.strUserEmail = txtEmail.Text";
parent.mostCurrent._main._struseremail /*String*/  = parent.mostCurrent._txtemail.getText();
 //BA.debugLineNum = 460;BA.debugLine="Main.strUserFullName = txtUserID.Text";
parent.mostCurrent._main._struserfullname /*String*/  = parent.mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 461;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (true) break;

case 7:
//if
this.state = 12;
if (parent.mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 462;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 464;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 if (true) break;

case 12:
//C
this.state = 15;
;
 //BA.debugLineNum = 469;BA.debugLine="Dim MapReset As Map";
_mapreset = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 470;BA.debugLine="MapReset.Initialize";
_mapreset.Initialize();
 //BA.debugLineNum = 471;BA.debugLine="MapReset.Put(\"lastuser\", \"si\")";
_mapreset.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 472;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastUser",(Object)("no"),_mapreset);
 //BA.debugLineNum = 475;BA.debugLine="Dim newUser As List";
_newuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 476;BA.debugLine="newUser.Initialize";
_newuser.Initialize();
 //BA.debugLineNum = 477;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 478;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 479;BA.debugLine="m.Put(\"username\", Main.username)";
_m.Put((Object)("username"),(Object)(parent.mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 480;BA.debugLine="m.Put(\"userEmail\", Main.strUserEmail)";
_m.Put((Object)("userEmail"),(Object)(parent.mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 481;BA.debugLine="m.Put(\"userOrg\", Main.strUserOrg)";
_m.Put((Object)("userOrg"),(Object)(parent.mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 482;BA.debugLine="m.Put(\"userTipoUsuario\", Main.strUserTipoUsuari";
_m.Put((Object)("userTipoUsuario"),(Object)(parent.mostCurrent._main._strusertipousuario /*String*/ ));
 //BA.debugLineNum = 484;BA.debugLine="m.Put(\"lastUser\", \"si\")";
_m.Put((Object)("lastUser"),(Object)("si"));
 //BA.debugLineNum = 485;BA.debugLine="m.Put(\"lang\", \"es\")";
_m.Put((Object)("lang"),(Object)("es"));
 //BA.debugLineNum = 486;BA.debugLine="newUser.Add(m)";
_newuser.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 487;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"userconfig\",";
parent.mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig",_newuser);
 //BA.debugLineNum = 490;BA.debugLine="Log(\"Usuario registrado en DB interna - mail ya";
anywheresoftware.b4a.keywords.Common.LogImpl("06357048","Usuario registrado en DB interna - mail ya existente",0);
 //BA.debugLineNum = 491;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 494;BA.debugLine="Log(\"Codigo incorrecto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("06357052","Codigo incorrecto",0);
 //BA.debugLineNum = 495;BA.debugLine="ToastMessageShow(\"Código incorrecto\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Código incorrecto"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 496;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 15:
//C
this.state = 18;
;
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 500;BA.debugLine="Log(\"Codigo incorrecto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("06357058","Codigo incorrecto",0);
 //BA.debugLineNum = 501;BA.debugLine="ToastMessageShow(\"Código incorrecto\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Código incorrecto"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 502;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 506;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _enviar_codigo() throws Exception{
ResumableSub_Enviar_Codigo rsub = new ResumableSub_Enviar_Codigo(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_Enviar_Codigo extends BA.ResumableSub {
public ResumableSub_Enviar_Codigo(appear.pnud.preservamos.register parent) {
this.parent = parent;
}
appear.pnud.preservamos.register parent;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 403;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 404;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",register.getObject());
 //BA.debugLineNum = 405;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\" &";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/enviar_codigo.php?"+"email="+parent.mostCurrent._txtemail.getText()+"&"+"codigo="+parent.mostCurrent._codigo_enviado;
 //BA.debugLineNum = 408;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 410;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 13;
return;
case 13:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 411;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 12;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 11;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 412;BA.debugLine="Log(\"Codigo recibido: \" & j.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("06291467","Codigo recibido: "+BA.ObjectToString(_j._success /*boolean*/ ),0);
 //BA.debugLineNum = 413;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 414;BA.debugLine="res = j.GetString";
_res = _j._getstring /*String*/ ();
 //BA.debugLineNum = 415;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 416;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 417;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 418;BA.debugLine="If action = \"CodigoOk\" Then";
if (true) break;

case 4:
//if
this.state = 9;
if ((_action).equals("CodigoOk")) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 9;
 //BA.debugLineNum = 419;BA.debugLine="Log(\"Código enviado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("06291474","Código enviado",0);
 //BA.debugLineNum = 420;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 421;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 424;BA.debugLine="Log(\"Código no enviado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("06291479","Código no enviado",0);
 //BA.debugLineNum = 425;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 426;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 9:
//C
this.state = 12;
;
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 429;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 430;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 432;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(appear.pnud.preservamos.httpjob _j) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private txtUserID As EditText";
mostCurrent._txtuserid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private txtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblUnmaskPass As Label";
mostCurrent._lblunmaskpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private cmbMunicipio As Spinner";
mostCurrent._cmbmunicipio = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private tabRegister As TabStrip";
mostCurrent._tabregister = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 18;BA.debugLine="Private lblCirculoPos1 As Label";
mostCurrent._lblcirculopos1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblCirculoPos2 As Label";
mostCurrent._lblcirculopos2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblCirculoPos3 As Label";
mostCurrent._lblcirculopos3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnPrev As Button";
mostCurrent._btnprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private chkEmpleadoMunicipio As CheckBox";
mostCurrent._chkempleadomunicipio = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private dialog As B4XDialog";
mostCurrent._dialog = new appear.pnud.preservamos.b4xdialog();
 //BA.debugLineNum = 26;BA.debugLine="Dim codigo_enviado As String";
mostCurrent._codigo_enviado = "";
 //BA.debugLineNum = 27;BA.debugLine="Private chkPoliticas As CheckBox";
mostCurrent._chkpoliticas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim actionFail As String";
mostCurrent._actionfail = "";
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _lblleerlegales_click() throws Exception{
 //BA.debugLineNum = 538;BA.debugLine="Sub lblLeerLegales_Click";
 //BA.debugLineNum = 539;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 540;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _registeruser() throws Exception{
ResumableSub_RegisterUser rsub = new ResumableSub_RegisterUser(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_RegisterUser extends BA.ResumableSub {
public ResumableSub_RegisterUser(appear.pnud.preservamos.register parent) {
this.parent = parent;
}
appear.pnud.preservamos.register parent;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _mapreset = null;
anywheresoftware.b4a.objects.collections.List _newuser = null;
anywheresoftware.b4a.objects.collections.Map _m = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 226;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (true) break;

case 1:
//if
this.state = 6;
if (parent.mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 227;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 229;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 233;BA.debugLine="Log(\"UserID=\" & txtUserID.Text & \"&\" & _ 	  \"Emai";
anywheresoftware.b4a.keywords.Common.LogImpl("06160393","UserID="+parent.mostCurrent._txtuserid.getText()+"&"+"Email="+parent.mostCurrent._txtemail.getText()+"&"+"userTipoUsuario="+parent.mostCurrent._main._strusertipousuario /*String*/ +"&"+"userOrg="+parent.mostCurrent._main._struserorg /*String*/ +"&"+"deviceID="+parent.mostCurrent._main._deviceid /*String*/ ,0);
 //BA.debugLineNum = 239;BA.debugLine="Main.username = txtUserID.Text";
parent.mostCurrent._main._username /*String*/  = parent.mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 240;BA.debugLine="Main.strUserEmail = txtEmail.Text";
parent.mostCurrent._main._struseremail /*String*/  = parent.mostCurrent._txtemail.getText();
 //BA.debugLineNum = 243;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 244;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",register.getObject());
 //BA.debugLineNum = 245;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\" &";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/signup.php?Action=Register&"+"UserID="+parent.mostCurrent._txtuserid.getText()+"&"+"Email="+parent.mostCurrent._txtemail.getText()+"&"+"userOrg="+parent.mostCurrent._main._struserorg /*String*/ +"&"+"userTipoUsuario="+parent.mostCurrent._main._strusertipousuario /*String*/ +"&"+"deviceID="+parent.mostCurrent._main._deviceid /*String*/ ;
 //BA.debugLineNum = 252;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 253;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 33;
return;
case 33:
//C
this.state = 7;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 255;BA.debugLine="If j.Success Then";
if (true) break;

case 7:
//if
this.state = 32;
if (_j._success /*boolean*/ ) { 
this.state = 9;
}else {
this.state = 31;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 256;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 257;BA.debugLine="res = j.GetString";
_res = _j._getstring /*String*/ ();
 //BA.debugLineNum = 258;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 259;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 260;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 261;BA.debugLine="If action = \"Mail\" Then";
if (true) break;

case 10:
//if
this.state = 29;
if ((_action).equals("Mail")) { 
this.state = 12;
}else if((_action).equals("MailInUse")) { 
this.state = 26;
}else {
this.state = 28;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 262;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 13:
//if
this.state = 18;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 15;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 17;
}if (true) break;

case 15:
//C
this.state = 18;
 //BA.debugLineNum = 266;BA.debugLine="ToastMessageShow(\"Has completado el registro,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Has completado el registro, ya puedes participar de PreserVamos"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 269;BA.debugLine="MsgboxAsync(\"User correctly registered!\", \"Con";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("User correctly registered!"),BA.ObjectToCharSequence("Congrats"),processBA);
 if (true) break;

case 18:
//C
this.state = 19;
;
 //BA.debugLineNum = 273;BA.debugLine="Main.username = txtUserID.Text";
parent.mostCurrent._main._username /*String*/  = parent.mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 275;BA.debugLine="Main.strUserEmail = txtEmail.Text";
parent.mostCurrent._main._struseremail /*String*/  = parent.mostCurrent._txtemail.getText();
 //BA.debugLineNum = 277;BA.debugLine="Main.strUserFullName = txtUserID.Text";
parent.mostCurrent._main._struserfullname /*String*/  = parent.mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 279;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (true) break;

case 19:
//if
this.state = 24;
if (parent.mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 21;
}else {
this.state = 23;
}if (true) break;

case 21:
//C
this.state = 24;
 //BA.debugLineNum = 280;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 282;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
parent.mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 if (true) break;

case 24:
//C
this.state = 29;
;
 //BA.debugLineNum = 287;BA.debugLine="Dim MapReset As Map";
_mapreset = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 288;BA.debugLine="MapReset.Initialize";
_mapreset.Initialize();
 //BA.debugLineNum = 289;BA.debugLine="MapReset.Put(\"lastuser\", \"si\")";
_mapreset.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 290;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastUser",(Object)("no"),_mapreset);
 //BA.debugLineNum = 294;BA.debugLine="Dim newUser As List";
_newuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 295;BA.debugLine="newUser.Initialize";
_newuser.Initialize();
 //BA.debugLineNum = 296;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 297;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 298;BA.debugLine="m.Put(\"username\", Main.username)";
_m.Put((Object)("username"),(Object)(parent.mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 299;BA.debugLine="m.Put(\"userEmail\", Main.strUserEmail)";
_m.Put((Object)("userEmail"),(Object)(parent.mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 300;BA.debugLine="m.Put(\"userOrg\", Main.strUserOrg)";
_m.Put((Object)("userOrg"),(Object)(parent.mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 301;BA.debugLine="m.Put(\"userTipoUsuario\", Main.strUserTipoUsuari";
_m.Put((Object)("userTipoUsuario"),(Object)(parent.mostCurrent._main._strusertipousuario /*String*/ ));
 //BA.debugLineNum = 303;BA.debugLine="m.Put(\"lastUser\", \"si\")";
_m.Put((Object)("lastUser"),(Object)("si"));
 //BA.debugLineNum = 304;BA.debugLine="m.Put(\"lang\", \"es\")";
_m.Put((Object)("lang"),(Object)("es"));
 //BA.debugLineNum = 306;BA.debugLine="newUser.Add(m)";
_newuser.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 307;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"userconfig\",";
parent.mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig",_newuser);
 //BA.debugLineNum = 310;BA.debugLine="Log(\"Usuario registrado en DB interna\")";
anywheresoftware.b4a.keywords.Common.LogImpl("06160470","Usuario registrado en DB interna",0);
 //BA.debugLineNum = 311;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 313;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 26:
//C
this.state = 29;
 //BA.debugLineNum = 316;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 317;BA.debugLine="actionFail = \"MailInUse\"";
parent.mostCurrent._actionfail = "MailInUse";
 //BA.debugLineNum = 318;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 320;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 321;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 29:
//C
this.state = 32;
;
 if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 325;BA.debugLine="actionFail = \"NoInternet\"";
parent.mostCurrent._actionfail = "NoInternet";
 //BA.debugLineNum = 326;BA.debugLine="Main.hayinternet = False";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 327;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 328;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 32:
//C
this.state = -1;
;
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _tabregister_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Private Sub tabRegister_PageSelected (Position As";
 //BA.debugLineNum = 72;BA.debugLine="If Position = 2 Then";
if (_position==2) { 
 //BA.debugLineNum = 73;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 74;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 75;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 76;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 77;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==0) { 
 //BA.debugLineNum = 83;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 84;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 85;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 86;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 87;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==1) { 
 //BA.debugLineNum = 89;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 90;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 91;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 92;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 93;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 };
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static boolean  _validate_email(String _emailaddress) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matchemail = null;
 //BA.debugLineNum = 508;BA.debugLine="Sub Validate_Email(EmailAddress As String) As Bool";
 //BA.debugLineNum = 509;BA.debugLine="Dim MatchEmail As Matcher = Regex.Matcher(\"^(?";
_matchemail = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_matchemail = anywheresoftware.b4a.keywords.Common.Regex.Matcher("^(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])$",_emailaddress);
 //BA.debugLineNum = 511;BA.debugLine="If MatchEmail.Find = True Then";
if (_matchemail.Find()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 513;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 516;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 518;BA.debugLine="End Sub";
return false;
}
}
