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

public class frmfelicitaciones extends Activity implements B4AActivity{
	public static frmfelicitaciones mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmfelicitaciones");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmfelicitaciones).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmfelicitaciones");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmfelicitaciones", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmfelicitaciones) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmfelicitaciones) Resume **");
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
		return frmfelicitaciones.class;
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
            BA.LogInfo("** Activity (frmfelicitaciones) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmfelicitaciones) Pause event (activity is not paused). **");
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
            frmfelicitaciones mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmfelicitaciones) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static String _tiporio = "";
public static String _origen = "";
public static String _destino = "";
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldatosenviados = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntosenviados = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolverperfil = null;
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
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 44;BA.debugLine="Activity.LoadLayout(\"layFelicitaciones\")";
mostCurrent._activity.LoadLayout("layFelicitaciones",mostCurrent.activityBA);
 //BA.debugLineNum = 57;BA.debugLine="Timer1.Initialize(\"Timer1\",200)";
_timer1.Initialize(processBA,"Timer1",(long) (200));
 //BA.debugLineNum = 58;BA.debugLine="Timer1.Enabled=True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 75;BA.debugLine="Timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 71;BA.debugLine="Timer1.Enabled=True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static void  _btnvolverperfil_click() throws Exception{
ResumableSub_btnVolverPerfil_Click rsub = new ResumableSub_btnVolverPerfil_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnVolverPerfil_Click extends BA.ResumableSub {
public ResumableSub_btnVolverPerfil_Click(appear.pnud.preservamos.frmfelicitaciones parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmfelicitaciones parent;
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
 //BA.debugLineNum = 100;BA.debugLine="destino = \"\"";
parent._destino = "";
 //BA.debugLineNum = 101;BA.debugLine="If destino = \"residuos\" Then";
if (true) break;

case 1:
//if
this.state = 12;
if ((parent._destino).equals("residuos")) { 
this.state = 3;
}else {
this.state = 11;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 102;BA.debugLine="Msgbox2Async(\"¡Si realizas un análisis de residu";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¡Si realizas un análisis de residuos, ganarás 1 punto adicional! ¿Quieres hacer eso?"),BA.ObjectToCharSequence("Puedes ganar mas puntos!"),"Si, hacer análisis de residuos","","No, ahora no",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 103;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 104;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 4:
//if
this.state = 9;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 9;
 //BA.debugLineNum = 105;BA.debugLine="Timer1.Enabled = False";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 106;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 107;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 108;BA.debugLine="StartActivity(mod_Residuos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._mod_residuos.getObject()));
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 110;BA.debugLine="Timer1.Enabled = False";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 111;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 112;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 114;BA.debugLine="CallSubDelayed(Form_Main, \"gotoInformacion\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._form_main.getObject()),"gotoInformacion");
 if (true) break;

case 9:
//C
this.state = 12;
;
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 117;BA.debugLine="Timer1.Enabled = False";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 118;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 119;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 121;BA.debugLine="CallSubDelayed(Form_Main, \"gotoInformacion\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._form_main.getObject()),"gotoInformacion");
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblDatosEnviados As Label";
mostCurrent._lbldatosenviados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblpuntosenviados As Label";
mostCurrent._lblpuntosenviados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnVolverPerfil As Button";
mostCurrent._btnvolverperfil = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 11;BA.debugLine="Dim tiporio As String";
_tiporio = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim origen As String";
_origen = "";
 //BA.debugLineNum = 13;BA.debugLine="Dim destino As String";
_destino = "";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static void  _timer1_tick() throws Exception{
ResumableSub_Timer1_Tick rsub = new ResumableSub_Timer1_Tick(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Timer1_Tick extends BA.ResumableSub {
public ResumableSub_Timer1_Tick(appear.pnud.preservamos.frmfelicitaciones parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmfelicitaciones parent;
anywheresoftware.b4a.objects.ImageViewWrapper _image = null;
int _left = 0;
int _a = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 80;BA.debugLine="Dim image As ImageView";
_image = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 81;BA.debugLine="image.Initialize(\"\")";
_image.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 83;BA.debugLine="image.Bitmap =LoadBitmap(File.DirAssets, \"snow.pn";
_image.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"snow.png").getObject()));
 //BA.debugLineNum = 84;BA.debugLine="Dim left As Int =Rnd(0,100)";
_left = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (100));
 //BA.debugLineNum = 85;BA.debugLine="Panel1.AddView(image,(left*Panel1.Width)/100,-20%";
parent.mostCurrent._panel1.AddView((android.view.View)(_image.getObject()),(int) ((_left*parent.mostCurrent._panel1.getWidth())/(double)100),(int) (-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (16)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (16)));
 //BA.debugLineNum = 86;BA.debugLine="Dim a As Int = Rnd(0,10)";
_a = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (10));
 //BA.debugLineNum = 87;BA.debugLine="If a >5  Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_a>5) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 88;BA.debugLine="image.SendToBack";
_image.SendToBack();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 90;BA.debugLine="image.BringToFront";
_image.BringToFront();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 92;BA.debugLine="image.SetLayoutAnimated(5000,image.Left,120%y,ima";
_image.SetLayoutAnimated((int) (5000),_image.getLeft(),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (120),mostCurrent.activityBA),_image.getWidth(),_image.getHeight());
 //BA.debugLineNum = 93;BA.debugLine="image.SetVisibleAnimated(5000,False)";
_image.SetVisibleAnimated((int) (5000),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 94;BA.debugLine="Sleep(5000)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (5000));
this.state = 7;
return;
case 7:
//C
this.state = -1;
;
 //BA.debugLineNum = 95;BA.debugLine="image.RemoveView";
_image.RemoveView();
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
}
