package com.hanyeah.hcanvas;

import org.json.JSONException;
import org.json.JSONObject;

import com.hanyeah.HEngine;
import com.hanyeah.display.HBitmap;
import com.hanyeah.display.HDisplayObject;
import com.hanyeah.display.HShape;
import com.hanyeah.display.HSprite;
import com.hanyeah.display.HSpriteSheet;
import com.hanyeah.display.HStage;
import com.hanyeah.display.HTextField;
import com.hanyeah.events.HEvent;
import com.hanyeah.events.HIListener;
import com.hanyeah.events.HTouchEvent;
import com.hanyeah.geom.HPoint;
import com.hanyeah.geom.HRectangle;
import com.hanyeah.hengine.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Demo。
 * 基本功能测试。
 * @author hanyeah
 * @date 2015-12-15
 */
public class MainActivity extends Activity {
	
	private static HStage stage;
	private HEngine hengine;

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        final TextView tf=(TextView)this.findViewById(R.id.tf);
        
        /*
        Paint paint=new Paint();
        paint.setColor(0xff0000);
        Canvas canvas=new Canvas();
        canvas.drawRect(0, 0, 100, 100, paint);
        canvas.translate(100, 100);
        canvas.scale(2, 2);
        canvas.rotate(90);
        tf.setText("h:"+canvas.getMatrix().toString());
        */
        if(stage!=null){
        	Log.e(TAG, "hengine:"+hengine);
        	hengine=new HEngine(this);
        	hengine.stage=stage;
        	setContentView(hengine);
        	
        	return;
        }
        hengine=new HEngine(this);
        stage=hengine.stage;
        
        setContentView(hengine);
        stage.frameRate=30;
        stage.addEventListener(HEvent.STAGE_CREATED, new HIListener(){

			@Override
			public boolean execute(HEvent e) {
				// TODO Auto-generated method stub
				Log.e("hanyeah","StageWidth:"+(float)stage.getStageWidth()+" ,StageHeight:"+(float)stage.getStageHeight());
				
				return false;
			}
        	
        });
        
        HShape bgshape=new HShape();
        stage.addChild(bgshape);
        Path bgPath=new Path();
        Paint bgPaint=new Paint();
        bgPaint.setColor(0xff00cc00);
        bgPath.addRect(0, 0, 480, 720,  Path.Direction.CCW);
        bgshape.graphics.addPath(bgPath, bgPaint);
        
        HSprite sp=new HSprite();
        stage.addChild(sp);
        sp.x=300;
        sp.y=300;
        //sp.scaleX=2;
        Bitmap bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.cube);
        final HBitmap hbmp=new HBitmap(bmp);
        sp.addChild(hbmp);
        hbmp.rotation=45;
        hbmp.x=-50;
        hbmp.y=-50;
        hbmp.scaleX=hbmp.scaleY=2;
        HBitmap hbmp2=new HBitmap(bmp);
        sp.addChild(hbmp2);
        hbmp2.x=-50;
        hbmp2.y=-50;
        
        
        final HTextField textF=new HTextField();
        textF.text="hello world,hello world,hello world,hello world,山不在高，有仙则名。水不在深，有龙则灵。\n\n斯是陋室aaa，惟吾德馨。苔痕上阶绿，草色入帘青。谈笑有鸿儒，往来无白丁。\n可以调素琴，阅金经。无丝竹之乱耳，无案牍之劳形。南阳诸葛庐，西蜀子云亭。孔子云：何陋之有？";
        textF.x=50;
        textF.y=400;
        textF.width=400;
        textF.height=300;
        textF.wordWrap=true;
        textF.color=Color.RED;
        textF.size=30;
        textF.lineHeight=45;
        
        stage.addChild(textF);
        final HTextField fpsTf=new HTextField();
        stage.addChild(fpsTf);
        fpsTf.color=Color.RED;
        fpsTf.size=30;
        fpsTf.x=50;
        fpsTf.y=50;
        
        
        final HShape shape=new HShape();
        Path pa=new Path();
        Paint pain=new Paint();
        pain.setColor(0xffff0000);
        pa.addCircle(0, 0, 10, Path.Direction.CCW);
        shape.graphics.addPath(pa, pain);
        stage.addChild(shape);
        final HShape shape2=new HShape();
        shape2.graphics.addPath(pa, pain);
        
        final HSprite sp2=new HSprite();
        stage.addChild(sp2);
        sp2.scaleX=hbmp.scaleX;
        sp2.scaleY=hbmp.scaleY;
        sp2.x=hbmp.x;
        sp2.y=hbmp.y;
        sp2.addChild(shape2);
        
        Bitmap bmpsheet=BitmapFactory.decodeResource(this.getResources(), R.drawable.java);
        String jsonStr="{\"frames\":[[0,0,50,71,0,32.75,36.25],[50,0,50,71,0,32.75,36.25],[100,0,50,71,0,32.75,36.25],[150,0,50,71,0,32.75,36.25],[200,0,50,71,0,32.75,36.25],[0,71,50,71,0,32.75,36.25],[50,71,50,71,0,32.75,36.25],[100,71,50,71,0,32.75,36.25]],\"animations\":{\"stand\":[0,6,0.2],\"run\":{\"frames\":[0,1,2,3,4,5,6],\"speed\":0.2},\"jump\":[6,6]}}";
        JSONObject jsonObj=null;
		try {
			jsonObj = new JSONObject(jsonStr);
			
			HSpriteSheet sps=new HSpriteSheet(bmpsheet,jsonObj);
			
			stage.addChild(sps);
			sps.x=100;
			sps.y=100;
			sps.gotoAndPlay("run", 0);
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e("hanyeah_spriteSheet:", "error:"+e1.toString());
		}
        

        stage.addEventListener(HTouchEvent.CLICK, new HIListener(){

			@Override
			public boolean execute(HEvent e) {
				// TODO Auto-generated method stub
				//Log.e("hanyeah","mouseX:"+(float)stage.mouseX+" ,mouseY:"+(float)stage.mouseY);
				Path pa=new Path();
		        Paint pain=new Paint();
		        pain.setColor(0xffff0000);
		        pa.addCircle((float)stage.mouseX,(float)stage.mouseY, 10, Path.Direction.CCW);
		        stage.graphics.addPath(pa, pain);
		        
				return false;
			}
        	
        });
        
       //canvas取像素
        /*
        int[] co=new int[10000];
        for(int i=0;i<10000;i++){
        	co[i]=0x00000000;
        }
        
        Bitmap bitmap=Bitmap.createBitmap(co,100, 100, Bitmap.Config.ARGB_8888);
        Bitmap mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);//安卓：解决mCanvas.setBitmap(mBitmap)出现 java.lang.IllegalStateException问题http://blog.sina.com.cn/s/blog_714ed4b30101g7nd.html
        Canvas can=new Canvas();
        can.setBitmap(mBitmap);
        Paint paint00=new Paint();
        paint00.setColor(0xffff0000);
        can.drawCircle(50, 50, 25, paint00);
        can.drawBitmap(bmp, 0, 0, paint00);
    
        int color=mBitmap.getPixel(40, 40);
        Log.e("====hanyeah-=====","alpha:"+(color>>24&0xff));
        Log.e("====hanyeah-=====","red:"+(color>>16&0xff));
        Log.e("====hanyeah-=====","green:"+(color>>8&0xff));
        Log.e("====hanyeah-=====","blue:"+(color&0xff));
        textF.text="alpha:"+(color>>24&0xff)+"\n"+"red:"+(color>>16&0xff)+"\n"+"green:"+(color>>8&0xff)+"\n"+"blue:"+(color&0xff);
        
        HBitmap bmp00=new HBitmap(mBitmap);
        stage.addChild(bmp00);
        */
        
        //hbmp2.scaleX=hbmp2.scaleY=2;
        
        final HSprite rectSp=new HSprite();
        rectSp.mouseChildren=rectSp.mouseEnabled=false;
        stage.addChild(rectSp);
        
        Log.e("==========================hanyeah====================", "enterframe:"+hbmp.rotation);
        stage.addEventListener(HEvent.ENTER_FRAME, new HIListener(){
			@Override
			public boolean execute(HEvent e) {
				// TODO Auto-generated method stub
				hbmp.rotation++;
				
				HPoint p1=hbmp.localToGlobal(new HPoint(100,100));
				 shape.x=p1.x;
			     shape.y=p1.y;
			     
			     sp2.rotation=hbmp.rotation;
			        HPoint p2=sp2.globalToLocal(new HPoint(100,100));
			        shape2.x=p2.x;
			        shape2.y=p2.y;
			     
			     fpsTf.text="fps:"+stage.getFPS();
			     
			     HRectangle rect=hbmp.getRect(stage);
			     rectSp.graphics.clear();
			     Path p=new Path();
			     p.addRect((float)rect.x, (float)rect.y, (float)(rect.x+rect.width), (float)(rect.y+rect.height), Path.Direction.CCW);
			     Paint pa=new Paint();
			     pa.setStyle(Style.STROKE);
			     pa.setColor(0xff00ff00);
			     pa.setStrokeWidth(2.0f);
			     rectSp.graphics.addPath(p, pa);
			     //Log.e(TAG, rect.toString());
			     
				return false;
			}
        });
        Log.e(TAG, "start onCreate~~~");
    }
    //生命周期参考：http://kb.cnblogs.com/page/70125/
    private final String TAG="hanyeah";
    @Override  
    protected void onStart() {  
        super.onStart();  
        Log.e(TAG, "start onStart~~~");  
    }  
    @Override  
    protected void onRestart() {  
        super.onRestart();  
        Log.e(TAG, "start onRestart~~~");  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        Log.e(TAG, "start onResume~~~");  
    }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        Log.e(TAG, "start onPause~~~");  
    }  
    @Override  
    protected void onStop() {  
        super.onStop();  
        Log.e(TAG, "start onStop~~~");  
    }  
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        Log.e(TAG, "start onDestroy~~~");
    }  
   
    
}
