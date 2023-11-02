package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class b4xsignaturetemplate extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "appear.pnud.preservamos.b4xsignaturetemplate");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", appear.pnud.preservamos.b4xsignaturetemplate.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public anywheresoftware.b4a.objects.B4XCanvas _cvs = null;
public float _lastx = 0f;
public float _lasty = 0f;
public float _strokewidth = 0f;
public int _strokecolor = 0;
public int _textcolor = 0;
public int _backgroundcolor = 0;
public boolean _adddateandtime = false;
public anywheresoftware.b4a.objects.B4XViewWrapper.B4XFont _textfont = null;
public int _numberofpoints = 0;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmdatossinenviar _frmdatossinenviar = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmmunicipioestadisticas _frmmunicipioestadisticas = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.mod_hidro _mod_hidro = null;
public appear.pnud.preservamos.mod_hidro_fotos _mod_hidro_fotos = null;
public appear.pnud.preservamos.mod_residuos _mod_residuos = null;
public appear.pnud.preservamos.mod_residuos_fotos _mod_residuos_fotos = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.reporte_habitat_rio_bu _reporte_habitat_rio_bu = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras _reporte_habitat_rio_sierras = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 3;BA.debugLine="Public mBase As B4XView";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 4;BA.debugLine="Private cvs As B4XCanvas";
_cvs = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 5;BA.debugLine="Private LastX, LastY As Float";
_lastx = 0f;
_lasty = 0f;
 //BA.debugLineNum = 6;BA.debugLine="Public StrokeWidth As Float = 2dip";
_strokewidth = (float) (__c.DipToCurrent((int) (2)));
 //BA.debugLineNum = 7;BA.debugLine="Public StrokeColor As Int = xui.Color_Black";
_strokecolor = _xui.Color_Black;
 //BA.debugLineNum = 8;BA.debugLine="Public TextColor As Int = 0xFFFF8800";
_textcolor = ((int)0xffff8800);
 //BA.debugLineNum = 9;BA.debugLine="Public BackgroundColor As Int = xui.Color_White";
_backgroundcolor = _xui.Color_White;
 //BA.debugLineNum = 10;BA.debugLine="Public AddDateAndTime As Boolean = True";
_adddateandtime = __c.True;
 //BA.debugLineNum = 11;BA.debugLine="Public TextFont As B4XFont";
_textfont = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XFont();
 //BA.debugLineNum = 12;BA.debugLine="Public NumberOfPoints As Int";
_numberofpoints = 0;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public String  _dialogclosed(int _result) throws Exception{
anywheresoftware.b4a.objects.B4XCanvas.B4XRect _r = null;
int _baseline = 0;
 //BA.debugLineNum = 56;BA.debugLine="Private Sub DialogClosed (Result As Int)";
 //BA.debugLineNum = 57;BA.debugLine="If Result = xui.DialogResponse_Positive And AddDa";
if (_result==_xui.DialogResponse_Positive && _adddateandtime) { 
 //BA.debugLineNum = 58;BA.debugLine="Dim r As B4XRect = cvs.MeasureText(\"M\", TextFont";
_r = _cvs.MeasureText("M",_textfont);
 //BA.debugLineNum = 59;BA.debugLine="Dim Baseline As Int = cvs.TargetRect.Bottom - r.";
_baseline = (int) (_cvs.getTargetRect().getBottom()-_r.getHeight()-_r.getTop()-__c.DipToCurrent((int) (2)));
 //BA.debugLineNum = 60;BA.debugLine="cvs.DrawText($\"$DateTime{DateTime.Now}\"$, 2dip,";
_cvs.DrawText(ba,(""+__c.SmartStringFormatter("datetime",(Object)(__c.DateTime.getNow()))+""),(float) (__c.DipToCurrent((int) (2))),(float) (_baseline),_textfont,_textcolor,BA.getEnumFromString(android.graphics.Paint.Align.class,"LEFT"));
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper  _getbitmap() throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Public Sub getBitmap As B4XBitmap";
 //BA.debugLineNum = 53;BA.debugLine="Return cvs.CreateBitmap";
if (true) return _cvs.CreateBitmap();
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _getpanel(appear.pnud.preservamos.b4xdialog _dialog) throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Public Sub GetPanel (Dialog As B4XDialog) As B4XVi";
 //BA.debugLineNum = 29;BA.debugLine="Return mBase";
if (true) return _mbase;
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 15;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 16;BA.debugLine="mBase = xui.CreatePanel(\"mBase\")";
_mbase = _xui.CreatePanel(ba,"mBase");
 //BA.debugLineNum = 17;BA.debugLine="mBase.SetLayoutAnimated(0, 0, 0, 300dip, 200dip)";
_mbase.SetLayoutAnimated((int) (0),(int) (0),(int) (0),__c.DipToCurrent((int) (300)),__c.DipToCurrent((int) (200)));
 //BA.debugLineNum = 18;BA.debugLine="cvs.Initialize(mBase)";
_cvs.Initialize(_mbase);
 //BA.debugLineNum = 19;BA.debugLine="TextFont = xui.CreateDefaultFont(14)";
_textfont = _xui.CreateDefaultFont((float) (14));
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public String  _mbase_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Private Sub mBase_Touch (Action As Int, X As Float";
 //BA.debugLineNum = 39;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_mbase.TOUCH_ACTION_DOWN,_mbase.TOUCH_ACTION_MOVE)) {
case 0: {
 //BA.debugLineNum = 41;BA.debugLine="LastX = X";
_lastx = _x;
 //BA.debugLineNum = 42;BA.debugLine="LastY = Y";
_lasty = _y;
 break; }
case 1: {
 //BA.debugLineNum = 44;BA.debugLine="cvs.DrawLine(LastX, LastY, X, Y, StrokeColor, S";
_cvs.DrawLine(_lastx,_lasty,_x,_y,_strokecolor,_strokewidth);
 //BA.debugLineNum = 45;BA.debugLine="LastX = X";
_lastx = _x;
 //BA.debugLineNum = 46;BA.debugLine="LastY = Y";
_lasty = _y;
 //BA.debugLineNum = 47;BA.debugLine="cvs.Invalidate";
_cvs.Invalidate();
 //BA.debugLineNum = 48;BA.debugLine="NumberOfPoints = NumberOfPoints + 1";
_numberofpoints = (int) (_numberofpoints+1);
 break; }
}
;
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public String  _resize(int _width,int _height) throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Public Sub Resize(Width As Int, Height As Int)";
 //BA.debugLineNum = 23;BA.debugLine="mBase.SetLayoutAnimated(0, 0, 0, Width, Height)";
_mbase.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_width,_height);
 //BA.debugLineNum = 24;BA.debugLine="cvs.Resize(Width, Height)";
_cvs.Resize((float) (_width),(float) (_height));
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public String  _show(appear.pnud.preservamos.b4xdialog _dialog) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Private Sub Show (Dialog As B4XDialog) 'ignore";
 //BA.debugLineNum = 33;BA.debugLine="cvs.DrawRect(cvs.TargetRect, BackgroundColor, Tru";
_cvs.DrawRect(_cvs.getTargetRect(),_backgroundcolor,__c.True,(float) (0));
 //BA.debugLineNum = 34;BA.debugLine="cvs.Invalidate";
_cvs.Invalidate();
 //BA.debugLineNum = 35;BA.debugLine="NumberOfPoints = 0";
_numberofpoints = (int) (0);
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
if (BA.fastSubCompare(sub, "DIALOGCLOSED"))
	return _dialogclosed(((Number)args[0]).intValue());
if (BA.fastSubCompare(sub, "GETPANEL"))
	return _getpanel((appear.pnud.preservamos.b4xdialog) args[0]);
if (BA.fastSubCompare(sub, "SHOW"))
	return _show((appear.pnud.preservamos.b4xdialog) args[0]);
return BA.SubDelegator.SubNotFound;
}
}
