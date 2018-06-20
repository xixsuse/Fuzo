package gg.rich.me.fuzo.sketch;

/**
 * Created by Rich on 30/10/2017.
 *
 */

import gg.rich.me.fuzo.BuildConfig;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.core.PVector;
import processing.data.Table;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.util.HashMap;

public class Fuzo_Main extends PApplet {

////////////////////////////////////////////////////////////
////           Fuzo has been designed and coded         ////
////                  by and for rich.gg                ////
////                 June the 17th of 2017              ////
////                 all rights reserved                ////
////                                                    ////
////         ...at least until I'm rich enough          ////
////              to have my bicycle fixed              ////
////////////////////////////////////////////////////////////

    public void settings() {
        fullScreen(P3D);
        smooth(); //changes a LOT //TODO

        // Remember the start model view matrix values

    }

    //TODO fixe "android:largeHeap="true"" in manifest
    private static final int LOADINGSTATE = 0;
    private static final int PAUSESTATE = 1;
    private static final int PLAYSTATE = 2;
    private int appState = LOADINGSTATE;



    //GLOBAL VARIABLES
    PMatrix3D baseMatrix;
    // the canon rail diameter should be:  height / 1.2
    float railRatio = 1.2f;  // straight OUTTA KOAN
    float unitInPx;  // 2.3255813 on K1 --- 1.3953488 on Z1private   PFont Time_Roman;

    private int rHeight;
    private int rWidth;
    private float density ; // density is rather 1.0 or 2.0 or may be more
    private int densityDpi ;

    private boolean showFPS = false;
    private float new_fps ;
    private float old_fps;
    private int averageFPS;

    private boolean mousePressedDoOnce ;

    private PShape globy;

    private PShape globy00;///
    private PShape globyP01;
    private PShape globyP02;
    private PShape globyP03;
    private PShape globyP04;
    private PShape globyP05;
    private PShape globyP06;
    private PShape globyP07;
    private PShape globyP08;
    private PShape globyP09;
    private PShape globyP10;
    private PShape globyP11;
    private PShape[] globyPX =  {globyP01, globyP02, globyP03, globyP04, globyP05, globyP06, globyP07, globyP08, globyP09, globyP10, globyP11};

    private PShape globy12;
    private PShape globyM01;
    private PShape globyM02;
    private PShape globyM03;
    private PShape globyM04;
    private PShape globyM05;
    private PShape globyM06;
    private  PShape globyM07;
    private  PShape globyM08;
    private PShape globyM09;
    private PShape globyM10;
    private PShape globyM11;
    private PShape[] globyMX =  {globyM01, globyM02, globyM03, globyM04, globyM05, globyM06, globyM07, globyM08, globyM09, globyM10, globyM11};



 //   PFont Time_Roman;

    private boolean touched = false;
    private boolean touchedDoOnce = false;
    private boolean touchMoved = false;

    private PImage imgGMTbCurrent;
    private PImage imgGMTrCurrent;

    private int imgGMTrCurrentIndex = 0;
    private int prevImgGMTrCurrentIndex = imgGMTrCurrentIndex;




    private int imgGMTbCurrentIndex = 0;
    private int prevImgGMTbCurrentIndex = imgGMTbCurrentIndex;
    //
    private int tmzGMTbCurrentIndex = 0;
    private int tmzGMTrCurrentIndex = 0;
    //
    private float rpm = 0;
    private float speedX = 0;

    private float A = 0; // obsolete ?
    private float B = 0; // obsolete ?
    private float D = 0; // obsolete ?
    private float C = 0; // obsolete ?

    private float startX = 0;
    private float startY = 0;

    private float deltaX = 0.0f;
    private float angleX = 180; //180
    //private float easingGlobe = 0.1f;
    private float easedDeltaX = 0;

    //private boolean onWhite ;

    private static int onNOTHING = 0;
    private static int onWHITE = 1;
    private static int onRED = 2;
    private static int onBLUE = 3;
    private int onWhat = onWHITE;


    //SPHERICAL TRIGO
//private float thetaR;
//private float phiR;
//private float thetaB;
//private float phiB;
    private float rho = 255;

    private float startAngle = -180;
    private float worldAngle = 0;
    //private boolean whiteSelected = false;
    //
    private int dotRadius = 75;
    //
    private float redDotAngleX = 0;
    private float redDotAngleY = 0;
    private float redDotAngleZ = 0;
    private float redDotLength = 260;
    private int redDotColor;
    //private boolean onRed = false;
    //private boolean redSelected = false;
    //
    private float blueDotAngleX = -12;
    private float blueDotAngleY = 0;
    private float blueDotLength = 260;
    private int blueDotColor;
    //private boolean onBlue = false;
    //private boolean blueSelected = false;
    //
    private int arcDotLengthPlus ;

    //GlobeTouch
    private float dummyAngleY;
    private float dummyAngleX;
    //touch tracker
    private float dummyX;
    private float dummyY;

    private float touchTheta;
    private float touchPhi;

    //PImage currentNumber;

    //redDot xyz
    private float redDotX;
    private float redDotY;
    private float redDotZ;
    //blueDot xyz
    private float blueDotX;
    private float blueDotY;
    private float blueDotZ;

//private PImage currentRedHour;
//
//private PImage currentBlueHour;

    private int redHour = 0;
    private int prevRedHour = redHour;
    private boolean redHourChange = true;
    private boolean redSphereChange = true;

    private int blueHour = 0;
    private int prevBlueHour = blueHour;
    private boolean blueHourChange = true;
    private boolean blueSphereChange = true;


    private boolean gapChange = true;

    private float easing = 0.1f;

    private float alphRedHour = 0;
    private float alphBlueHour = 0;

    //CADRANs
    private float cadranDPosXA = 350;//0
    private float cadranDPosXB = 0;//0
    private float cadranDMoonAlpha = 0;
    private boolean cadranOk = false;
    private boolean yesterday = false;
    private boolean tomorow = false;
    private boolean setterDoOnce = false;
    private boolean setterDONE = false;

    private int startTime;
    private int stopTime;
    private int duration = 500;
    private float motionAngle = 0.0f;
    private float p;
    private float origine;
    private float destination;

    private float startRedX = 0;
    private float redX = -3.75f;
    private float startRedY = 0;
    private float redY = 15;
    private float redZ = 0;
    //
    private float startBlueX = 0;
    private float blueX = 3.75f;
    private float startBlueY = 0;
    private float blueY = -15;
    private float blueZ = 0;

    private float cx, cy;
    private float fov;
    private int cameraZ = 2300;//2000
    private boolean preLoaded;
    //int  redDotColor, blueDotColor;
    private float ratioGlobeToHeight; // = 984 / 1080; //993/ 1080
    private float globeDiamInPx;
    private float globeRadiusInPx;

    //private Table richTable;

    private int totalDiscretImgs = 12;

    private PImage imgDemiCadranDA, imgDemiCadranDB, imgDemiCadranGB, imgDemiCadranGA,
            traitb, traitr, imgBlueMoon, imgBlueSun, imgRedMoon, imgRedSun, imgAiguilleRed, imgAiguilleBlue;

    private PImage [] discretIMG = {imgDemiCadranDA, imgDemiCadranDB, imgDemiCadranGB, imgDemiCadranGA,
            traitb, traitr, imgBlueMoon, imgBlueSun, imgRedMoon, imgRedSun, imgAiguilleRed, imgAiguilleBlue} ;

    private String [] discretImgString = {"DemiCadranDA02.png", "DemiCadranDB02.png", "DemiCadranGB02.png", "DemiCadranGA02.png",
            "TRAITb.png", "TRAITr.png", "blueMoon.png", "blueSun.png", "redMoon.png", "redSun.png", "aiguilleRed.png", "aiguilleBlue.png"} ;

    //numbers
    private int totalnumbersImgs = 25;
    private PImage[] numbersImgs = new PImage[totalnumbersImgs];

    //clocks
    private int totalredHoursImgs = 26;
    private PImage[] redHoursImgs = new PImage[totalredHoursImgs];
    private int totalblueHoursImgs = 26;
    private PImage[] blueHoursImgs = new PImage[totalblueHoursImgs];

    //colors TMZ
    private int totalgmtRpImgs = 12;
    private PImage[] gmtRpImgs = new PImage[totalgmtRpImgs];
    private int totalgmtRmImgs = 12;
    private PImage[] gmtRmImgs = new PImage[totalgmtRmImgs];
    //
    private int totalgmtBpImgs = 12;
    private PImage[] gmtBpImgs = new PImage[totalgmtBpImgs];
    private int totalgmtBmImgs = 12;
    private PImage[] gmtBmImgs = new PImage[totalgmtBmImgs];

    int totalImage = totalDiscretImgs + totalnumbersImgs + totalredHoursImgs
            + totalblueHoursImgs + totalgmtRpImgs + totalgmtRmImgs
            + totalgmtBpImgs + totalgmtBmImgs;

    private int ressourceCounter = 0;

    private float rX;
    private boolean doOnce;
    private boolean drawTheCities;
    private boolean  setRed, setBlue;

    //SHAPES
    private PShape blueSphere, redSphere;
    private PShape demiCadranGA, demiCadranDA, demiCadranGB, demiCadranDB;
    private PShape sunRed, sunBlue, moon1R, moon1B; //, moon2R, moon2B
    private PShape clockR, clockB, clockRtrait, clockBtrait, clockRneedleShort, clockBneedleShort;
    private PShape yesterdayShape, tomorowShape;
    private PShape gapDot, gapDotBG; //dotShadow, redDotShape, blueDotShape;


    //sound  code
    SoundPool soundPool;
    HashMap<Object, Object> soundPoolMap;
    //Activity act;
    Context context;
    AssetFileDescriptor soundClick, soundClick2, soundBgBuzz;
    int n0, n1, n2;
    int streamIdn0, streamIdn1, streamIdn2;
    //
    boolean soundOn;
    //
    PFont FontRegular;
    PFont FontLight;
    PFont FontItalic;
    //PFont FontItalicBold;
    PFont FontSemiBold;
    PFont FontBold;

    //HUD
    //HUDLayout HUDplay;
    private String HUDOutPut = "";
    Interface2D interface2D;

    private boolean doOnceInit;

    private boolean opened;

    int bgColor = color(245, 245, 236);

    //SPINNIG INTRO
    private boolean spinningDone = false;
    private boolean doOnceSpinning = false;
    private int startSpinning, stopSpinning;
    private int spinningTime = 8000; //4000

    //globePop loop timer
    int globePopStart ;
    int globePopDelay = 100;
    int globePopNext;
    int globePopCounter = 0;

    int versionCode;
    String versionName;



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////SETUP/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /*
void windowResized() {
 fov = map(width, 112, 1920, 0.525f, 0.2875f);
 //
 ratioGlobeToHeight = 984.0f / 1080.0f; //993/ 1080
 globeDiamInPx = height * ratioGlobeToHeight; //maybe ?
 globeRadiusInPx = globeDiamInPx / 2;
 //
 perspective(fov, PApplet.parseFloat(width)/PApplet.parseFloat(height), 0.1f, 3000);
 cx = width / 2;
 cy = height / 2;
 }
  */


    public void setup() {
        //size(1024, 768, P3D);
        Activity act = this.getActivity();
        context = act.getApplicationContext();
        println("context : " + context);
        //
        baseMatrix = getMatrix(baseMatrix);
        //surface.setResizable(true);
        hint(ENABLE_DEPTH_MASK);
        hint(ENABLE_DEPTH_SORT);
        //frameRate(10);
        //fov = 20 / 180 * PI;

        //fov = map(width, 112, 1920, 0.525f, 0.2875f); //THE GOOD ONE

        //fov = map(width, 112, 2560, 0.525f, 0.3833333f); //too small

        //fov = map(width, 112, 2560, 0.525f, 0.2f); //about ok, but not on edge 6

        //pour JB fov = map(width, 112, 2560, 0.525f, 0.3f); //too small on other, almost good on edge 6

        //pour JB  fov = map(width, 112, 2560, 0.525f, 0.29f); //too small on other, almost very good on edge 6

        //fov = map(width, 112, 2560, 0.525f, 0.2875f);

//        String[] fontList = PFont.list();
//        printArray(fontList);


        fov =  0.2875f ;

        //GLOBE
        globy = createShape(GROUP);
        globy00 = loadShape("GMT00.obj");
        globy.addChild(globy00);
        for (int i = 1; i <=11; i++) {
            globyPX[i-1] = loadShape("GMTP" + i + ".obj");
            globy.addChild(globyPX[i-1]);
        }
        for (int i = 1; i <=11; i++) {
            globyMX[i-1] = loadShape("GMTM" + i + ".obj");
            globy.addChild(globyMX[i-1]);
        }
        globy12 = loadShape("GMT12.obj");
        globy.addChild(globy12);
        //
        println("-------------- fov ------------- " + fov);
        //fov = map(1080, 112, 1920, 0.525, 0.2875);
        //fov = map(windowWidth, 112, 1920, 0.525, 0.2875);
        //
        ratioGlobeToHeight = 984.0f / 1080.0f; //993/ 1080
        globeDiamInPx = height * ratioGlobeToHeight; //maybe ?
        globeRadiusInPx = globeDiamInPx / 2;
        //println(" ratioGlobeToHeight " + ratioGlobeToHeight);
        //
        //*******************screen check*********************
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        density = dm.density;
        densityDpi = dm.densityDpi;
        //println("density is " + density);
        //println("densityDpi is " + densityDpi);
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        rWidth = dm.widthPixels;
        rHeight = dm.heightPixels;
        //
        //using the fact that the original diameter of Canon Rail is 430 on a total height of 520 (railRatio = 1.2)
        //we calculate the size in pixels of a "unitInPx"
        //then we multiply the different sizes by unitInPx to get to adjusted size
        float railDiam = rHeight / railRatio;
        //unitInPx = 2.0f ; //instead of 1.7984495
        unitInPx = (railDiam / 430) ;
        //*****************************************
        //println("fov : " + fov);
        //perspective(fov, PApplet.parseFloat(width)/PApplet.parseFloat(height), 0.1f, 3000);
        //camera(0, 0, cameraZ, 0, 0, 0, 0, 1, 0);
        rectMode(CENTER);

        cx = width / 2;
        cy = height / 2;

        redDotColor = color(250, 88, 95);
        blueDotColor = color(150, 197, 201);
//        Time_Roman = createFont("Time_Roman.ttf", 25); //112
//        textFont(Time_Roman);

        //TABLE LOAD
        //richTable = loadTable("cities_gmt.csv");
        versionCode = BuildConfig.VERSION_CODE;
        versionName = BuildConfig.VERSION_NAME;

        //IMAGES LOADING/////////////////////////////////////////////////
        for(int i = 0; i < discretIMG.length; i++){
            MyTaskParams params = new MyTaskParams(discretIMG, i, discretImgString[i]);
            //println("tessssst : " + params.pImageNum);
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }

        //numbers
        for (int i = 0; i < totalnumbersImgs; i++) {
            MyTaskParams params = new MyTaskParams(numbersImgs, i, "number" + i + ".png");
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }
        //redHours
        for (int i = 0; i < totalredHoursImgs; i++) {
            //redHoursImgs[i] = loadImage("redHour" + i + ".png");
            //
            MyTaskParams params = new MyTaskParams(redHoursImgs, i, "redHour" + i + ".png");
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }
        //blueHours
        for (int i = 0; i < totalblueHoursImgs; i++) {
            //blueHoursImgs[i] = loadImage("blueHour" + i + ".png");
            //
            MyTaskParams params = new MyTaskParams(blueHoursImgs, i, "blueHour" + i + ".png");
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }
        //TMZ images
        for (int i = 0; i < totalgmtRpImgs; i++) {
            //gmtRpImgs[i] = loadImage("GMTp" + i + "r.png");
            //
            MyTaskParams params = new MyTaskParams(gmtRpImgs, i, "GMTp" + i + "r.png");
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }
        for (int i = 0; i < totalgmtBpImgs; i++) {
            //gmtBpImgs[i] = loadImage("GMTp" + i + "b.png");
            //
            MyTaskParams params = new MyTaskParams(gmtBpImgs, i, "GMTp" + i + "b.png");
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }
        for (int i = 0; i < totalgmtRmImgs; i++) {
            //gmtRmImgs[i] = loadImage("GMTm" + (i+1) + "r.png");
            //
            MyTaskParams params = new MyTaskParams(gmtRmImgs, i, "GMTm" + (i+1) + "r.png");
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }
        for (int i = 0; i < totalgmtBmImgs; i++) {
            //gmtBmImgs[i] = loadImage("GMTm" + (i+1) + "b.png");
            //
            MyTaskParams params = new MyTaskParams(gmtBmImgs, i, "GMTm" + (i+1) + "b.png");
            BitmapWorkerTask myTask = new BitmapWorkerTask();
            myTask.execute(params);
        }

        FontRegular = createFont("FontRegular.TTF", 150);
        FontLight = createFont("FontLight.TTF", 150);
        //FontItalic = createFont("Serif", 150);
         FontItalic = createFont("FontItalic.TTF", 150);
       // FontItalicBold = createFont("FontItalicBold.TTF", 150);
        FontSemiBold = createFont("FontSemiBold.TTF", 150);
        FontBold = createFont("FontBold.TTF", 150);

        //IMAGES LOADING/////////////////////////////////////////////////END

        //SOUNDS LOADING////////////////////////////////////////////////END
        // load up the files
        try {
            soundClick = context.getAssets().openFd("click.wav");   // touch paddle
            soundClick2 = context.getAssets().openFd("click2.wav");   // touch tile#1
            soundBgBuzz = context.getAssets().openFd("bgBuzz6.wav"); // soundBgBuzz = context.getAssets().openFd("bgBuzz4.mp3");


        }
        catch(IOException e) {
            println("error loading files:" + e);
        }
        // create the soundPool HashMap - apparently this constructor is now deprecated?
        soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap<Object, Object>(3);

        soundPoolMap.put(n0, soundPool.load(soundClick, 1));
        soundPoolMap.put(n1, soundPool.load(soundClick2, 1));
        soundPoolMap.put(n2, soundPool.load(soundBgBuzz, 1));

        //
        //HUDplay = new HUDLayout (this, "play");

        interface2D = new Interface2D(this);
        interface2D.init();
    }

    private static class MyTaskParams {
        PImage[] wichArray;
        int pImageNum ;
        String file;

        MyTaskParams(PImage[] wichArray, int pImageNum, String file ) {
            this.wichArray = wichArray;
            // println("wichArray      :::::::: " + wichArray);
            this.pImageNum = pImageNum;
            //println("pImage      :::::::: " + pImageNum);
            this.file = file;
            //println("file      :::::::: " + file);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    private class BitmapWorkerTask extends AsyncTask<MyTaskParams, Void, PImage> {
        // load image in background.
        @Override
        protected PImage doInBackground(MyTaskParams... params) {
            //pImage = params[0].pImage;
            //println("params[0].pImage : " + params[0].pImageNum );
            params[0].wichArray[params[0].pImageNum] = loadImage(params[0].file);

            //////////////FAKE LOOP/////////////
//            for(int i = 0; i < 10; i++){
//                try{
//                    Thread.sleep(2);
//                }catch(InterruptedException ie){
//                    ie.printStackTrace();
//                }
//            }//TO SIMULATE HEAVY TASK
            ////////////////////////////////////


            return params[0].wichArray[params[0].pImageNum];
            //return pImage;
        }

        // Once complete, ++the ressourceCounter
        @Override
        protected void onPostExecute(PImage result) {
            // println("loaded image is : " + result );
            ressourceCounter++;
        }
    }
    ///////////////////////////////////////////////////////////////////////////////

    private void createTheShapes() {

        int redAmbient =  64;
        int blueAmbient =  64;
        noStroke();
        //ambient(255);
        sphereDetail(48);
        //globe = createShape(SPHERE, 255); //255
        //globe.setFill(color(200,0,0));
        //globe.setTexture(imgTerre);
        //gridSphere = createShape(SPHERE, 256); //257
        //gridSphere.setTexture(imgGrid);
        blueSphere = createShape(SPHERE, 260); //256
        // blueSphere.setTexture(imgGMTbCurrent);
        redSphere = createShape(SPHERE, 260); //256
        // redSphere.setTexture(imgGMTrCurrent);

        //demiCadranGA = createShape(RECT, 104, 0, 150, 600); //RECT, x, y, largeur, hauteur
        //demiCadranGA.setTexture(imgDemiCadranGA);
        demiCadranGA = createShape();
        demiCadranGA.beginShape();
        //demiCadranGA.texture(imgDemiCadranGA);
        demiCadranGA.texture(discretIMG[3]);
        demiCadranGA.ambient(redAmbient);
        demiCadranGA.vertex(29, -300, 0, 0);
        demiCadranGA.vertex(29+150, -300, 150, 0);
        demiCadranGA.vertex(29+150, 300, 150, 600);
        demiCadranGA.vertex(29, 300, 0, 600);
        demiCadranGA.endShape(CLOSE);

        //demiCadranDA = createShape(RECT, -104, 0, 150, 600);
        //demiCadranDA.setTexture(imgDemiCadranDA);
        demiCadranDA = createShape();
        demiCadranDA.beginShape();
        ///////////////////////////////////////demiCadranDA.texture(imgDemiCadranDA);
        demiCadranDA.texture(discretIMG[0]);
        demiCadranDA.ambient(redAmbient);
        demiCadranDA.vertex(-29, -300, 150, 0);
        demiCadranDA.vertex(-(29+150), -300, 0, 0);
        demiCadranDA.vertex(-(29+150), 300, 0, 600);
        demiCadranDA.vertex(-29, 300, 150, 600);
        demiCadranDA.endShape(CLOSE);


        demiCadranGB = createShape(RECT, -95, 0, 150, 600); //RECT, x, y, largeur, hauteur
        ////////////////////////////////demiCadranGB.setTexture(imgDemiCadranDB);
        demiCadranGB.setTexture(discretIMG[1]);

        demiCadranDB = createShape(RECT, 95, 0, 150, 600);
        /////////////////////////////////demiCadranDB.setTexture(imgDemiCadranGB);
        demiCadranDB.setTexture(discretIMG[2]);


        //sunRed = createShape(RECT, 44, 0, 44, 44);
        //sunRed.setTexture(imgRedSun);
        sunRed = createShape();
        sunRed.beginShape();
        //sunRed.texture(imgRedSun);
        sunRed.texture(discretIMG[9]);
        sunRed.ambient(redAmbient); ////
        //sunBlue.noStroke();
        sunRed.vertex(22, 22, 0, 0);
        sunRed.vertex(66, 22, 44, 0);
        sunRed.vertex(66, -22, 44, 44);
        sunRed.vertex(22, -22, 0, 44);
        sunRed.endShape(CLOSE);

        //sunBlue = createShape(RECT, -44, 0, 44, 44);
        sunBlue = createShape();
        sunBlue.beginShape();
        //sunBlue.texture(imgBlueSun);
        sunBlue.texture(discretIMG[7]);
        sunBlue.ambient(blueAmbient);
        //sunBlue.noStroke();
        sunBlue.vertex(-66, 22, 0, 0);
        sunBlue.vertex(-22, 22, 44, 0);
        sunBlue.vertex(-22, -22, 44, 44);
        sunBlue.vertex(-66, -22, 0, 44);
        sunBlue.endShape(CLOSE);

        //moon1R = createShape(RECT, 44, 0, 44, 44);
        //moon1R.setTexture(imgRedMoon);
        moon1R = createShape();
        moon1R.beginShape();
        //moon1R.texture(imgRedMoon);
        moon1R.texture(discretIMG[8]);
        moon1R.ambient(redAmbient);
        //sunBlue.noStroke();
        moon1R.vertex(66, 22, 44, 0);
        moon1R.vertex(22, 22, 0, 0);
        moon1R.vertex(22, -22, 0, 44);
        moon1R.vertex(66, -22, 44, 44);
        moon1R.endShape(CLOSE);
        //moon1B = createShape(RECT, -44, 0, 44, 44);
        //moon1B.setTexture(imgBlueMoon);
        moon1B = createShape();
        moon1B.beginShape();
        //moon1B.texture(imgBlueMoon);
        moon1B.texture(discretIMG[6]);
        moon1B.ambient(blueAmbient);
        //sunBlue.noStroke();
        moon1B.vertex(-66, 22, 0, 0);
        moon1B.vertex(-22, 22, 44, 0);
        moon1B.vertex(-22, -22, 44, 44);
        moon1B.vertex(-66, -22, 0, 44);
        moon1B.endShape(CLOSE);

        //clockR, clockB, clockRtrait, clockBtrait, clockRneedle, clockBneedle;
        clockR = createShape(RECT, -70, 0, 128, 64);
        //sunBlue = createShape(RECT, -44, 0, 44, 44);
        clockR = createShape();
        clockR.beginShape();
        //clockR.texture(imgBlueSun);
        clockR.ambient(redAmbient);
        //sunBlue.noStroke();
        clockR.vertex(-128, 32, 0, 128);
        clockR.vertex(0, 32, 128, 128);
        clockR.vertex(0, -32, 128, 0);
        clockR.vertex(-128, -32, 0, 0);
        clockR.endShape(CLOSE);

        //clockR.setTexture(currentRedHour);
        //clockRtrait = createShape(RECT, -80, 0, 160, 6);  //(RECT, -160, -3, 160, 6);
        //clockRtrait.setTexture(traitr);
        clockRtrait = createShape();
        clockRtrait.beginShape();
        //////////////////////////////////////clockRtrait.texture(traitr);
        clockRtrait.texture(discretIMG[5]);
        clockRtrait.ambient(redAmbient);
        //sunBlue.noStroke();
        clockRtrait.vertex(0, 3, 0, 0);
        clockRtrait.vertex(-160, 3, 195, 0);
        clockRtrait.vertex(-160, -3, 195, 8);
        clockRtrait.vertex(0, -3, 0, 8);
        clockRtrait.endShape(CLOSE);

        /////////////SHORT NEEDLE/////
        clockRneedleShort = createShape();
        clockRneedleShort.beginShape();
        //clockRneedleShort.texture(imgAiguilleRed);
        clockRneedleShort.texture(discretIMG[10]);
        clockRneedleShort.ambient(redAmbient);
        clockRneedleShort.vertex(260, 3, 0, 0);
        clockRneedleShort.vertex(283, 3, 23, 0);
        clockRneedleShort.vertex(283, -3, 23, 6);
        clockRneedleShort.vertex(260, -3, 0, 6);
        clockRneedleShort.endShape(CLOSE);
        //////////////////////////////



        //
        clockB = createShape(RECT, 60, 0, 128, 64);
        //currentBlueHour = blueHoursImgs[blueHour + 12];
        //clockB.setTexture(currentBlueHour);
        clockB = createShape();
        clockB.beginShape();
        //clockR.texture(imgBlueSun);
        clockB.ambient(blueAmbient);
        //sunBlue.noStroke();
        clockB.vertex(0, 32, 0, 128);
        clockB.vertex(128, 32, 128, 128);
        clockB.vertex(128, -32, 128, 0);
        clockB.vertex(0, -32, 0, 0);
        clockB.endShape(CLOSE);

        //clockBtrait = createShape(RECT, 80, 0, 160, 6);
        // clockBtrait.setTexture(traitb);
        clockBtrait = createShape();
        clockBtrait.beginShape();
        //////////////////////////clockBtrait.texture(traitb);
        clockBtrait.texture(discretIMG[4]);
        clockBtrait.ambient(blueAmbient);
        //sunBlue.noStroke();
        clockBtrait.vertex(0, 3, 195, 0);
        clockBtrait.vertex(160, 3, 0, 0);
        clockBtrait.vertex(160, -3, 0, 8);
        clockBtrait.vertex(0, -3, 195, 8);
        clockBtrait.endShape(CLOSE);

        //////////////SHORT VERSION//////////////
        clockBneedleShort  = createShape();
        clockBneedleShort.beginShape();
        //clockBneedleShort.texture(imgAiguilleBlue);
        clockBneedleShort.texture(discretIMG[11]);
        clockBneedleShort.ambient(blueAmbient);
        clockBneedleShort.vertex(260, 3, 0, 0);  //-5
        clockBneedleShort.vertex(283, 3, 23, 0);
        clockBneedleShort.vertex(283, -3, 23, 6);
        clockBneedleShort.vertex(260, -3, 0, 6);
        clockBneedleShort.endShape(CLOSE);
        /////////////////////////////////////////

//        redDotShape = createShape(ELLIPSE, 0, 0, 75, 75);
//        redDotShape.setTexture(redColor);
//
//        blueDotShape = createShape(ELLIPSE, 0, 0, 75, 75);
//        blueDotShape.setTexture(blueColor);

        gapDot = createShape(RECT, 0, 0, 30, 30);
        //gapDot.setTexture(currentNumber);
        gapDotBG = createShape(ELLIPSE, 0, 0, 25, 25);

//        dotShadow = createShape(RECT, 0, 0, dotRadius + 11, dotRadius + 11);
//        dotShadow.setTexture(imgShadow);

        //yesterdayShape = createShape(RECT, -70, 0, 128, 64);
        //yesterdayShape.setTexture(redHoursImgs[25]);
        yesterdayShape = createShape();
        yesterdayShape.beginShape();
        yesterdayShape.texture(redHoursImgs[25]);
        yesterdayShape.ambient(redAmbient);
        //sunBlue.noStroke();
        yesterdayShape.vertex(-20, 32, 128, 64);
        yesterdayShape.vertex(-148, 32, 0, 64);
        yesterdayShape.vertex(-148, -32, 0, 0);
        yesterdayShape.vertex(-20, -32, 128, 0);
        yesterdayShape.endShape(CLOSE);

        //tomorowShape = createShape(RECT, 70, 0, 128, 64);
        //tomorowShape.setTexture(blueHoursImgs[25]);
        tomorowShape = createShape();
        tomorowShape.beginShape();
        tomorowShape.texture(blueHoursImgs[25]);
        tomorowShape.ambient(blueAmbient);
        //sunBlue.noStroke();
        tomorowShape.vertex(20, 32, 0, 64);
        tomorowShape.vertex(148, 32, 128, 64);
        tomorowShape.vertex(148, -32, 128, 0);
        tomorowShape.vertex(20, -32, 0, 0);
        tomorowShape.endShape(CLOSE);

        appState = PLAYSTATE;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////DRAW//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void draw() {
        background(bgColor); //   background(240);
        //blendMode(BLEND);
        //println("appState : " + appState);
        if (appState == LOADINGSTATE){
            // totalImage
            // ressourceCounter

            //println("ressourceCounter  : " + ressourceCounter);

            if(ressourceCounter < totalImage){
                pushStyle();

//                fill(0);
//                textSize(100);
//                textAlign(CENTER, CENTER);
//                text("LOADING", width/2, height/2);
//                //showing %tage
//                text(((int)map(ressourceCounter, 0 , totalImage-1, 0, 100)) + " %", width/2, height/2 + 100); //-1 to show 100%
                rectMode(CORNER);
                fill(255,64,64);
                rect(0, 0, ((width / totalImage)* (ressourceCounter+1)), 4* unitInPx); //+1 to fill the whole screen (aka 100%)
                popStyle();
            }else{
                createTheShapes(); //appState = PLAYSTATE; is at the end of it
            }

//            if(millis() < 5000){
//                pushStyle();
//                fill(255,64,64);
//                rectMode(CORNER);
//                rect(0, 0, map(millis(), 0, 5000, 0, width), 4* unitInPx);
//                popStyle();
//            }else{
//                createTheShapes(); //appState = PLAYSTATE; is at the end of it
//            }

        }else if (appState == PLAYSTATE) {

            pushMatrix();

            perspective(fov, PApplet.parseFloat(width) / PApplet.parseFloat(height), 0.1f, 3000); //TODO check that
            camera(0, 0, cameraZ, 0, 0, 0, 0, 1, 0); // camera(0, 0, 1000);
            //camera(70.0, 35.0, 520.0, 50.0, 50.0, 0.0, 0.0, 1.0, 0.0);
            //LIGHTS
            //lightSpecular(204, 204, 204);
            //ambientLight(240, 240, 240);
            ambientLight(220, 220, 220);
            pointLight(100, 100, 100, -200, 200, 300);
            pointLight(100, 100, 100, 200, -200, 300);

            //int fullPreLoadCounter = 1 + 15 + totalnumbersImgs + totalredHoursImgs + totalblueHoursImgs + totalgmtRpImgs + totalgmtBpImgs + totalgmtRmImgs + totalgmtBmImgs + 3;
            //print('preLoadCounter :

            //GIVE 1 sec delay to let the loading proceed
            // setTimeout(function() {
            //code to execute after said time
            ////////////////////////SLICED GLOBE////////////////////////

            if(!preLoaded){

                pushMatrix();
                //translate(0,0,-1000);
                scale(0.1f);
                shape(globy00);
                for (int i= 1; i <= 11; i++) {          //PLUS SLICES
                    shape(globyPX[i-1] );
                }
                for (int i= 1; i <= 11; i++) {          //MINUS SLICES
                    shape(globyMX[i-1] );
                }
                shape(globy12);
                popMatrix();

                ///////////////////SLICED GLOBE END////////////////////////
                preLoaded = true;
            }

/*            if (!doOnceInit) {
                //initDaThing();
                doOnceInit = true;
            }*/



            if (!mousePressed) {
                onWhat = onNOTHING;
            }
            if(millis()>2000){ // delay to allow activities transition to show (doesn't show :( )
                //2000 is the time for the splashscreen so millis() start at launch
                if (!spinningDone) {
                    if (!doOnceSpinning) {
                        startSpinning = millis();
                        stopSpinning = startSpinning + spinningTime;
                        doOnceSpinning = true;
                    }
                    spinningIntro();
                } else {
                    //SCENE
                    //TODO Show slices once to load them up
                    //********************************************************************
                    renderCadran();
                    renderGlobe();
                    dayAfterBefore();
                    //********************************************************************
                    if (touched) {
                        //println("touched B");
                        //Log.d("MyApp","I am here");
                        globeTouch(); // display sphere based on angles
                    }
                    //********************************************************************
                    redDotTrackerer(); // 'return' redDot coordinates

                    //********************************************************************
                    blueDotTrackerer(); // 'return' blueDot coordinates

                    ////////////////////////////////////////////////////////////////////////
                    selectorHighlight();

                    if (interface2D.getMenuState() == 0) { //CLOSED
                        interactor();
                    }

                    arcLink();

                    //if (drawTheCities) {
                    //drawCities();
                    //}

                    clocks();

                    //********************************************************************
                    //Active Synchro
                    if (setRed) {
                        if (tmzGMTbCurrentIndex != tmzGMTrCurrentIndex) {
                            redSetter();
                        } else {
                            redY += 15;
                        }
                    }
                    if (setBlue) {
                        if (tmzGMTbCurrentIndex != tmzGMTrCurrentIndex) {
                            blueSetter();
                        } else {
                            blueY -= 15;
                        }
                    }
                }
            }

            if (showFPS) {
                pushStyle();
                textSize(50 * unitInPx);
                fill(128, 128);

                new_fps = old_fps * 0.99f + frameRate * 0.01f;
                averageFPS = (int) (new_fps);
                textAlign(CENTER, CENTER);
                text(averageFPS, width - (45 * unitInPx), 40 * unitInPx);
                old_fps = new_fps;
                popStyle();

                println("averageFPS : " + averageFPS);
                //noFill();
                //stroke(255,0,0);
                //ellipse(width, 0, 150* unitInPx, 150* unitInPx );
            }
            popMatrix();
//        }
//
//        if(appState == PLAYSTATE){
            // Restore the base matrix and lighting ready for 2D
            this.setMatrix(baseMatrix);
            camera(); //reset camera
            perspective(); // reset perspective
            ambientLight(0, 0, 0);

            //ambient(0,0,0);

            if (cadranOk) {
                //HUDplay.run();
                interface2D.run();
                if (!doOnce) {
                    //soundBgBuzz.loop(); sound
                    playSound(3);
                    soundOn = true;
                    doOnce = true;
                }
            }
            if (HUDOutPut.equals("oMenu")) {
                println(HUDOutPut);
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // private void initDaThing(){
    //HUDplay.reset();
    //}

    void spinningIntro(){
        float delta1  ;
        float sinDelta1  ;
        float alpha1 = 0.0f  ;
        //
        float delta2  ;
        float sinDelta2  ;
        float alpha2 = 0.0f  ;
        float alpha2b = 0.0f ;
        //
        float delta3  ;
        float sinDelta3  ;
        float alpha3 = 0.0f  ;
        float ecart = 0.0f ;

        if(millis() < stopSpinning ) {
            //println(millis());
            if (millis() < startSpinning + (spinningTime / 2)) {
                delta1 = map(millis(), startSpinning, startSpinning + (spinningTime / 2), 0, PI / 2);
                sinDelta1 = sin(delta1);
                alpha1 = map(sinDelta1, 0.0f, 1.0f, 255, 0);

                pushStyle();
                pushMatrix();
                translate(0,0,200);
                fill(255, alpha1);
                rect(0,0, width, height);
                popMatrix();
                popStyle();

                alpha2 = 255;
            }else if(millis() >= startSpinning + (spinningTime / 2) && millis() <= startSpinning + (spinningTime/4)*3){
                //alpha1 = PI / 2;

                delta2 = map(millis(), startSpinning + (spinningTime / 2), startSpinning + (spinningTime/4)*3, 0, PI / 2);
                sinDelta2 = sin(delta2);
                alpha2 = map(sinDelta2, 0.0f, 1.0f, 255, 0);
                alpha2b = map(sinDelta2, 0.5f, 1.0f, 0, 255);

                pushStyle();
                pushMatrix();
                //translate(0,0,200);
                stroke(0);
                strokeWeight(3);
                line(-255,0, 10, alpha2b , 0 , 10);
                popMatrix();
                popStyle();


            }else if(millis() > startSpinning + (spinningTime/4)*3){
                delta3 = map(millis(), startSpinning + (spinningTime/4)*3, stopSpinning, 0, PI / 2);
                sinDelta3 = sin(delta3);

                alpha3 = map(sinDelta3, 0.0f, 1.0f, 0, PI);
                ecart = map(sinDelta3, 0.0f, 1.0f, 0.1f, 15.0f);
            }

        }else{
            ecart = 15.0f;
            spinningDone = true;
        }

        pushMatrix();
        pushStyle();
        noFill();
        stroke(128); //0
        strokeWeight(1);

        if(millis() < startSpinning + (spinningTime/4)*3) {
            //rotateX(0);

            pushMatrix();
            translate(0, 0, (alpha1*-1) );

            for(float i = 0 ; i < 2*PI - ((2*PI)/7); i += ((2*PI)/7)){
                pushStyle();
                stroke(220, alpha2);
                strokeWeight(80);
                pushMatrix();
                rotate(i + (radians(14)));
                translate(115, 0, 0 );
                ellipse(0, 0, 50 * 2, 50 * 2);
                popMatrix();
                popStyle();
            }
            textFont(FontBold);
            //PFont FontRegular; PFont FontLight; PFont FontItalic; PFont FontItalicBold; PFont FontSemiBold; PFont FontBold;
            textSize(72);
            fill(128, alpha2);
            textAlign(CENTER, CENTER);
            text("rich.gg", 0, -8, 20);

            noFill();
            ellipse(0, 0, 255 * 2, 255 * 2);
            popMatrix();

            //////////////////////////////////////////////////////////////////////////////////////
            //println(versionCode + " :----------------------------------------: " + versionName);
            textFont(FontRegular);
            textSize(15);
            fill(128, alpha2);
            text("version " + versionCode + " :  vn " + versionName, 0, 300, 20);

        } else {

            rotateX(alpha3);
            rotateY(alpha3 + radians(7.5f));

            for (float i = 0; i < 12; i++) {
                //print(ecart);
                pushMatrix();
                rotateY(radians(i * ecart));
                ellipse(0, 0, 255 * 2, 255 * 2);
                popMatrix();
            }

            rotateX(PI / 2);
            strokeWeight(3);
            ellipse(0, 0, 255 * 2, 255 * 2);
        }
        //shape(demiCadranGA);
        popStyle();
        popMatrix();

    }

/*
void drawCities() {
 for (int i = 0; i < cities.getRowCount(); i++) {
 float latitude = cities.getNum(i, "lat");
 float longitude = cities.getNum(i, "lng");
 //setXY(latitude, longitude);

 pushMatrix();
 fill(128, 0, 0, 128);
 rotateY(+radians(longitude) + radians(worldAngle));
 rotateX(3 * (PI) + radians(latitude));
 pushMatrix();
 translate(0, 0, 255);
 ellipse(0, 0, 5, 5); //>>>>>>>>>>>>>>>>>>>>>>>CITY
 //sphere(5);
 popMatrix();
 //
 popMatrix();
 }
 }
 */

    private void clocks() {
        float diffAlphRed = redHour - alphRedHour;
        alphRedHour += diffAlphRed * easing;

        float redHourAlpha = map(alphRedHour, -12, 12, -45, 45);
        //
        float radiusR1 = 295; //295
        float redHourX1 = radiusR1 * -cos(radians(redHourAlpha));
        float redHourY1 = radiusR1 * -sin(radians(redHourAlpha));
        //
        float radiusR2 = 285; //285
        float redHourX2 = radiusR2 * -cos(radians(redHourAlpha));
        float redHourY2 = radiusR2 * -sin(radians(redHourAlpha));
        //
        if (cadranOk) {
            if(redHourChange) {
                if (redHour < -12) {
                    //currentRedHour = redHoursImgs[25];
                    clockR.setTexture(redHoursImgs[25]);
                } else {
                    if (redHour == 0) {
                        //currentRedHour = redHoursImgs[12]; //12
                        clockR.setTexture(redHoursImgs[12]);
                    } else {
                        //currentRedHour = redHoursImgs[redHour + 12];
                        clockR.setTexture(redHoursImgs[redHour + 12]);
                    }
                }
                redHourChange = false;
            }
            ////////////////////////////////////
            pushMatrix();
            rectMode(CORNER); // rectMode(CORNER, CENTER);
            translate(redHourX2 +5, redHourY2, 0);
            //ambient(255); // ambientMaterial(255, 0);
            //texture(traitr);
            //rect(-160, -3, 160, 6); //>>>>>>>>>>>>>>>>>>>>>>>>onRED TRAIT
            shape(clockRtrait);
            //fill(255,0,0);
            //ellipse(0,0,200,200);
            popMatrix();
            //
            //ambient(255); // ambientMaterial(255, 0);
            //texture(currentRedHour);
            pushMatrix();
            translate(redHourX1, redHourY1, 0);
            //texture(currentRedHour);
            //rect(-70, 0, 128, 64); //>>>>>>>>>>>>>>>>>>>>>>>ACTUAL CLOCK
            shape(clockR);
            popMatrix();
            //
            pushMatrix(); //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>AIGUILLE
            //ambient(255); // ambientMaterial(255, 0);
            //texture(imgAiguilleRed);
            rotateZ(radians(redHourAlpha + 180));
            //rect(200 / 2, 0, -363, 6);
            //pushMatrix();
            //translate(0, 0, 250);
            shape(clockRneedleShort);
            //popMatrix();

            popMatrix();
        }

        ///////////////////////onBLUE HOUR
        float diffAlphBlue = blueHour - alphBlueHour;
        alphBlueHour += diffAlphBlue * easing;

        float blueHourAlpha = map(alphBlueHour, -12, 12, -45, 45);
        //
        float radiusB1 = 295;
        float blueHourX1 = radiusB1 * cos(radians(blueHourAlpha));
        float blueHourY1 = radiusB1 * -sin(radians(blueHourAlpha));
        //
        float radiusB2 = 285;
        float blueHourX2 = radiusB2 * cos(radians(blueHourAlpha));
        float blueHourY2 = radiusB2 * -sin(radians(blueHourAlpha));
        //
        if (cadranOk) {

            if(blueHourChange) {
                if (blueHour > 12) {
                    //currentBlueHour = blueHoursImgs[25];
                    clockB.setTexture(blueHoursImgs[25]);
                } else {
                    if (blueHour == 0) {
                        //currentBlueHour = blueHoursImgs[12]; //12
                        clockB.setTexture(blueHoursImgs[12]);
                    } else {
                        //currentBlueHour = blueHoursImgs[blueHour + 12];
                        clockB.setTexture(blueHoursImgs[blueHour + 12]);
                    }
                }
                blueHourChange = false;
            }
            ////////////////////////////////////
            pushMatrix();
            rectMode(CORNER); // rectMode(CORNER, CENTER);
            translate(blueHourX2 - 5, blueHourY2, 0);
            //ambient(255); // ambientMaterial(255, 0);
            //texture(traitb);
            //rect(0, -3, 160, 6); //>>>>>>>>>>>>>>>>>>>>>>>>onBLUE TRAIT
            shape(clockBtrait);
            popMatrix();
            //
            //ambient(255); // ambientMaterial(255, 0);
            //texture(currentBlueHour);
            pushMatrix();
            translate(blueHourX1, blueHourY1, 0);
            //texture(currentBlueHour);
            //rect(70, 0, 128, 64, 10); //>>>>>>>>>>>>>>>>>>>>>>>ACTUAL CLOCK
            shape(clockB);
            popMatrix();
            //
            pushMatrix(); //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>AIGUILLE
            //ambient(255); // ambientMaterial(255, 0);
            //texture(imgAiguilleBlue);
            rotateZ(-radians(blueHourAlpha));
            //rect(-200 / 2, 0, 363, 6);
            //shape(clockBneedle);
            shape(clockBneedleShort);
            popMatrix();
        }
    }

    private void arcLink() {
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //>>>>>>>>>>>>arcAlongASphere>>>>>>>>>>>>>>>>
        //*******************************************************
        //var v1 = createVector(redDotX, redDotY, redDotZ);
        PVector v1 = new PVector(redDotX, redDotY, redDotZ);
  /*
                                                  fill(255, 0, 0, 200);
   pushMatrix();
   translate(v1.x, v1.y, v1.z);
   sphere(10);
   popMatrix();
   */
        //
        // var v2 = createVector(blueDotX, blueDotY, blueDotZ);
        PVector v2 = new PVector(blueDotX, blueDotY, blueDotZ);
  /*
                                                  fill(0, 0, 255, 200);
   pushMatrix();
   translate(v2.x, v2.y, v2.z);
   sphere(10);
   popMatrix();
   */
        //  I presume when you say "straight line over the surface of the sphere", you actually mean the shortest great circle path over the sphere. Here is one way to plot it. Let (x0,y0,z0) be the center of the sphere.
        // http://fr.mathworks.com/matlabcentral/newsreader/view_thread/277881
        // calculate the position of the point at v2
        int r = 255; //255
        //var v4a = p5.Vector.cross(v1, v2);
        PVector v4a = v1.cross(v2);
        //print("v4a : " + v4a);
        //var v4b = p5.Vector.cross(v4a, v1); //% v3 lies in plane of v1 & v2 and is orthog.to v1
        PVector v4b = v4a.cross(v1);
        //print("v4b : " + v4b)
        //var normV4 = v4b.mag();
        float normV4 = v4b.mag();
        //print("normV4 : " + normV4);
        //var v4c = p5.Vector.mult(v4b, r);
        PVector v4c = PVector.mult(v4b, r);
        //var v4 = p5.Vector.div(v4c, normV4); //% Make v3 of length r
        PVector v4 = PVector.div(v4c, normV4);
        //print("v4 : " + v4);
        //
        //range through from v1 to v2
        //determine range //tmax = atan2(sqrt(sum(cross(v1, v2). ^ 2, 1)), dot(v1, v2));
        //cross(v1, v2). ^ 2
        //var t1a = p5.Vector.cross(v1, v2);
        PVector t1a = v1.cross(v2);
        //print(t1a);
        //var t1b = t1a.magSq();
        float t1b = t1a.magSq();
        //print(t1b);
        float t1 = sqrt(t1b);
        //print(t1);
        //var t2 = p5.Vector.dot(v1, v2);
        float t2 = v1.dot(v2);
        //print(t2);
        float t3 = atan2(t1, t2); //>>>>>>>>>>>>>

        //print(t3);
        //trace along the way
        for (int i = 0; i < 20; i++) {
            float t = map(i, 0, 20, 0, t3);
            //var t = map(mouseX, 0, 100, 0, PI);
            //var v5a = p5.Vector.mult(v1, cos(t));
            PVector v5a = PVector.mult(v1, cos(t));
            //var v5b = p5.Vector.mult(v4, sin(t));
            PVector v5b = PVector.mult(v4, sin(t));
            //var v5 = p5.Vector.add(v5a, v5b); //>>>>>>>>>>>>
            PVector v5 = PVector.add(v5a, v5b);
            //
            //ambient(230); //50, 100, 250, 200

            if (v5.z > 0) {

                if (i < 9 || i > 11) {
                    pushStyle();
                    emissive(230);
                    pushMatrix();
                    translate(v5.x, v5.y, v5.z + arcDotLengthPlus);

//                    fill(255);
//                    ellipse(0, 0, 5, 5);
                    stroke(255);
                    strokeWeight(10);
                    //println(frameRate);
                    point(0,0);

                    popMatrix();
                    popStyle();

                }
            }
    /*
                                                        beginShape();
     vertex(0, 0, 0);
     vertex(v5.x, v5.y, v5.z);
     //vertex(150, 45,-100);
     endShape();
     */
        }

        //get middlePoint
        float u = map(10, 0, 20, 0, t3);
        //var v6a = p5.Vector.mult(v1, cos(u));
        PVector v6a = PVector.mult(v1, cos(u));
        //var v6b = p5.Vector.mult(v4, sin(u));
        PVector v6b = PVector.mult(v4, sin(u));
        //var v6 = p5.Vector.add(v6a, v6b); //>>>>>>>>>>>>
        PVector v6 = PVector.add(v6a, v6b);
        //PVector middlePoint = new PVector(v6.x, v6.y, v6.z);
        //var middlePoint = createVector(v6.x, v6.y, v6.z);
        //
        gapCounter(v6.x, v6.y, v6.z);
        //
    }

    private void gapCounter(float x, float y, float z) {
        int gap = abs(tmzGMTbCurrentIndex - tmzGMTrCurrentIndex);

        if (preLoaded) {

            if(gapChange) {
                if (tmzGMTbCurrentIndex == tmzGMTrCurrentIndex) {
                    //currentNumber = numbersImgs[0];
                    gapDot.setTexture(numbersImgs[0]);
                } else {
                    //currentNumber = numbersImgs[gap];
                    gapDot.setTexture(numbersImgs[gap]);
                }
                gapChange = false;
            }
            //print(imgGMTbCurrentIndex - imgGMTrCurrentIndex);
            if(z > 0) {

                pushStyle();
                emissive(230);
                pushMatrix();
                translate(x, y, z + 20);//10


                //ellipse(0, 0, 25, 25);
                shape(gapDotBG);
                popMatrix();
                popStyle();
                //

                //ambient(255); // ambientMaterial(255, 200);
                //texture(currentNumber);
                pushMatrix();
                translate(x, y, z + 30);//20
                //rect(0, 0, 30, 30);
                shape(gapDot);
                popMatrix();
            }
        }
    }

    private void renderGlobe() {

        ////SHAPES
        //PShape globe, gridSphere, blueSphere, redSphere;

        pushMatrix();
        worldAngle = angleX + rpm;
        rotateY(radians(worldAngle) - radians(90));
        /////////////////////////////


//if(startAngle  < 0){
//    rotateY(radians(startAngle));
//    startAngle++;
//}
        // shape(globe);
        //POP THE GLOBE
//        if (globePopCounter < (globyPX.length + globyPX.length + 2)) {
//            if (millis() > globePopNext) {
//
//                lettersVegetalement.get(globePopCounter).setState(1);
//
//                globePopCounter++;
//
//                globePopStart = millis();
//                globePopNext = globePopStart + globePopDelay;
//            }
//        } else {
//            globePopCounter = 0;
//        }

        ////////////////////////SLICED GLOBE////////////////////////
        if (sin(radians(worldAngle - 90)) > 0) { //GMT SLICE
            //globy00.setTint(255,128);
            shape(globy00);
        }
        for (int i= 1; i <= 11; i++) {          //PLUS SLICES
            if (sin(radians(worldAngle - 90 + (i * (360/24)))) > 0) {
                shape(globyPX[i-1] );
            }
        }
        for (int i= 1; i <= 11; i++) {          //MINUS SLICES
            if (sin(radians(worldAngle - 90 - (i * (360/24)))) > 0) {
                shape(globyMX[i-1] );
            }
        }
        if (sin(radians(worldAngle +90 )) > 0) { //GMT+-12
            shape(globy12);
        }
        ///////////////////SLICED GLOBE END////////////////////////
//println(frameRate);
        ////////////////////////GMT SPHERE/////////////////////////
        if (preLoaded) {
            //>>>>>>>>>>>>>>>>>>>>>>>>>GMT HIGHLIGHT

            if (imgGMTbCurrentIndex != imgGMTrCurrentIndex) {
                //ambient(0); // ambientMaterial(0, 1);
                //texture(imgGMTbCurrent);

                if(blueSphereChange) {
                    //println("blueSphereChange " + blueSphereChange);
                    blueSphere.setTexture(imgGMTbCurrent);
                    blueSphereChange = false;
                }
                //println("imgGMTbCurrentIndex " + imgGMTbCurrentIndex);
                // TODOshape(blueSphere);
                //sphere(256); //>>>>>>>>>>>>>>>>>>>>>>>>>onBLUE
                ////AUTOHIDE GMT SPHERE BLUE
                if(imgGMTbCurrentIndex < 13){
                    if (sin(radians(worldAngle - 90 - (imgGMTbCurrentIndex * (360/24)))) > 0) { //GMT+-12
                        shape(blueSphere);
                    }
                }else {
                    // println("imgGMTrCurrentIndex " + (12 - imgGMTrCurrentIndex));
                    if (sin(radians(worldAngle - 90 - ((12 -imgGMTbCurrentIndex) * (360/24)))) > 0) { //GMT+-12
                        shape(blueSphere);
                    }
                }
                //
                //ambient(0); // ambientMaterial(0, 1);
                //texture(imgGMTrCurrent);

                if(redSphereChange) {
                    //println("redSphereChange " + redSphereChange);
                    redSphere.setTexture(imgGMTrCurrent);
                    redSphereChange = false;
                }
                //sphere(256); //>>>>>>>>>>>>>>>>>>>>>>>>>onRED

                ////AUTOHIDE GMT SPHERE RED
                if(imgGMTrCurrentIndex < 13){
                    if (sin(radians(worldAngle - 90 - (imgGMTrCurrentIndex * (360/24)))) > 0) { //GMT+-12
                        shape(redSphere);
                    }
                }else {
                    // println("imgGMTrCurrentIndex " + (12 - imgGMTrCurrentIndex));
                    if (sin(radians(worldAngle - 90 - ((12 -imgGMTrCurrentIndex) * (360/24)))) > 0) { //GMT+-12
                        shape(redSphere);
                    }
                }

                //
            }
        }
        ////////////////////END GMT SPHERE/////////////////////////

        popMatrix();
    }

    private void setRedStraight() {
        if (redX % 3.75f != 0) {
            float realNumX = redX / 3.75f;
            float roundedNumX = round(realNumX);
            //print(realNum + " | " + roundedNum);
            redX = roundedNumX * 3.75f;
        }
        if (redY % 15 != 0) {
            float realNumY = redY / 15;
            float roundedNumY = round(realNumY);
            //print(realNum + " | " + roundedNum);
            redY = roundedNumY * 15;
        }
    }

    private void setBlueStraight() {
        if (blueX % 3.75f != 0) {
            float realNumX = blueX / 3.75f;
            float roundedNumX = round(realNumX);
            //print(realNum + " | " + roundedNum);
            blueX = roundedNumX * 3.75f;
        }
        if (blueY % 15 != 0) {
            float realNumY = blueY / 15;
            float roundedNumY = round(realNumY);
            //print(realNum + " | " + roundedNum);
            blueY = roundedNumY * 15;
        }
    }

    private void renderCadran() {
        //MOTEUR
        if (cadranDPosXA <= 360) { //340
            cadranDPosXA += 3; //6
        } else if (cadranDPosXB <= 160) {
            cadranDPosXB += 3; //6
        }else{
            opened = true;
        }

        if (cadranDPosXB > 150) {
            if (cadranDMoonAlpha <= 45) {
                cadranDMoonAlpha++;
            } else {
                cadranOk = true;
            }
        }

        if (cadranDPosXA > 200) {
            //DEMI CADRAN INT
            pushMatrix();
            translate(-cadranDPosXA, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgDemiCadranGA);
            //rect(104, 0, -150, 600);
            shape(demiCadranGA);
            popMatrix();
            //
            pushMatrix();
            translate(cadranDPosXA, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgDemiCadranDA);
            //rect(-104, 0, 150, 600);
            shape(demiCadranDA);
            popMatrix();

            //LADDER
            for (float i = -45; i <= 45; i += 3.75f) {
                float redHourAlpha = i;
                float redHourAlpha2 = map(alphRedHour, -12, 12, -45, 45);
                //
                float ladderAlphaR = sq(abs(redHourAlpha - redHourAlpha2));
                float radiusRladder = 285; //285
                float redHourXladder = radiusRladder * cos(radians(redHourAlpha));
                float redHourYladder = radiusRladder * -sin(radians(redHourAlpha));
                //println("redHourAlpha : " + redHourAlpha +" | redHourYladder : " + redHourYladder);
                //
                pushMatrix();
                rectMode(CORNER); // rectMode(CORNER, CENTER);
                translate(-redHourXladder, redHourYladder, 0);
                pushStyle();
                fill(128, ladderAlphaR);
                //
                rect(5, -1, -cadranDPosXB, 2);
                popStyle();
                //fill(255,0,0);
                //sphere(5);
                //
                if (redHourAlpha == 0) {
                    pushStyle();
                    fill(128, ladderAlphaR);
                    rect(5, -1, -cadranDPosXB, 2);
                    popStyle();
                }

                popMatrix();
            }
            //
            for (float i = -45; i <= 45; i += 3.75f) {
                float blueHourAlpha = i;
                float blueHourAlpha2 = map(alphBlueHour, -12, 12, -45, 45);
                //
                float ladderAlphaB = sq(abs(blueHourAlpha - blueHourAlpha2));
                float radiusBladder = 285;
                float blueHourXladder = radiusBladder * cos(radians(blueHourAlpha));
                float blueHourYladder = radiusBladder * -sin(radians(blueHourAlpha));
                //
                pushMatrix();
                rectMode(CORNER); // rectMode(CORNER, CENTER);
                translate(blueHourXladder, blueHourYladder, 0);
                pushStyle();
                fill(128, ladderAlphaB);
                //
                rect(-5, 0, cadranDPosXB, 2);
                popStyle();
                //
                if (blueHourAlpha == 0) {
                    pushStyle();
                    fill(128, ladderAlphaB);
                    rect(-5, -2, cadranDPosXB, 2);
                    popStyle();
                }

                popMatrix();
            }


            //DEMICADRAN EXT
            pushMatrix();
            translate(-(cadranDPosXA + cadranDPosXB), 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgDemiCadranDB);
            //rect(95, 0, -150, 600);
            shape(demiCadranDB);
            //rect(170, 0, 150, 600);
            popMatrix();
            //
            pushMatrix();
            translate(cadranDPosXA + cadranDPosXB, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgDemiCadranDB);
            //rect(-95, 0, 150, 600);
            shape(demiCadranGB);
            //rect(170, 0, 150, 600);
            popMatrix();

            //SUN  sunRed, sunBlue, moon1R, moon1B, moon2R, moon2B;
            pushMatrix();
            translate(-(cadranDPosXA + cadranDPosXB), 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgRedSun);
            //rect(44, 0, 44, 44);
            shape(sunRed);
            popMatrix();
            //
            pushMatrix();
            translate(cadranDPosXA + cadranDPosXB, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            // texture(imgBlueSun);
            //rect(-44, 0, 44, 44);
            //ambient(0);
            shape(sunBlue);
            popMatrix();
            //MOON1
            pushMatrix();
            translate(-(cadranDPosXA + cadranDPosXB), 0, 0);
            translate(360, 0, 0);
            rotateZ(radians(cadranDMoonAlpha));
            translate(-360, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgRedMoon);
            //rect(44, 0, -44, 44);
            shape(moon1R);
            popMatrix();
            //
            pushMatrix();
            translate(cadranDPosXA + cadranDPosXB, 0, 0);
            translate(-360, 0, 0);
            rotateZ(radians(cadranDMoonAlpha));
            translate(360, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgBlueMoon);
            //rect(-44, 0, 44, 44);
            shape(moon1B);
            popMatrix();
            //MOON2
            pushMatrix();
            translate(-(cadranDPosXA + cadranDPosXB), 0, 0);
            translate(360, 0, 0);
            rotateZ(-radians(cadranDMoonAlpha));
            translate(-360, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgRedMoon);
            //rect(44, 0, -44, 44);
            shape(moon1R);
            popMatrix();
            //
            pushMatrix();
            translate(cadranDPosXA + cadranDPosXB, 0, 0);
            translate(-360, 0, 0);
            rotateZ(-radians(cadranDMoonAlpha));
            translate(360, 0, 0);
            //ambient(0); // ambientMaterial(0, 0);
            //texture(imgBlueMoon);
            //rect(-44, 0, 44, 44);
            shape(moon1B);
            popMatrix();
        }
    }


    private void chrono() {
        startTime = millis();
        stopTime = startTime + duration;
    }


    private void redSetter() {
        int gap;
        if (!setterDoOnce) {
            tomorow = false;
            origine = redX;
            gap = tmzGMTbCurrentIndex - tmzGMTrCurrentIndex;
            int protoDestination = blueHour - gap;

            if (protoDestination >= -12) {
                yesterday = false;
                destination = protoDestination;

                // print("destination : " + destination + " | blueHour : " + blueHour + " MINUS gap :" + gap);
            } else {
                //si 'yesterday' -> destination = (12 - (protodesintation -12))
                yesterday = true;
                destination = (12 + (protoDestination + 12));
                // print("destination : " + destination + " | 12 - (" + protoDestination + " + 12 ))" );
            }

            chrono();
            setterDoOnce = true;
        }

        if (!setterDONE) {
            if (millis() < stopTime) {
                //first I want 1 sin cycle
                // so curtainAngle must be between -PI/2 and -PI
                motionAngle = map(millis(), startTime, stopTime, -PI / 2, PI / 2);
                float sinval = sin(motionAngle);
                //then I want normSin to be from 0 to 1
                float normSin = (sinval + 1) / 2;

                p = map(normSin, 0, 1, origine, destination * 3.75f); //

                redX = p;
            } else {
                setterDONE = false;
                setterDoOnce = false;
                setRed = false;
            }
        }
    }

    private void blueSetter() {
        int gap;
        if (!setterDoOnce) {
            yesterday = false;
            origine = blueX;
            gap = tmzGMTbCurrentIndex - tmzGMTrCurrentIndex;
            //destination = redHour + gap;
            int protoDestination = redHour + gap;

            if (protoDestination <= 12) {
                tomorow = false;
                destination = protoDestination;
                // print("destination : " + destination + " | blueHour : " + blueHour + " MINUS gap :" + gap);
            } else {
                //si 'yesterday' -> destination = (12 - (protodesintation -12))
                tomorow = true;
                destination = (-12 + (protoDestination - 12));
                // print("destination : " + destination + " | 12 - (" + protoDestination + " + 12 ))" );
                //TODO WRITE TOMOROW
            }

            chrono();
            setterDoOnce = true;
        }

        if (!setterDONE) {
            if (millis() < stopTime) {
                //first I want 1 sin cycle
                // so curtainAngle must be between -PI/2 and -PI
                motionAngle = map(millis(), startTime, stopTime, -PI / 2, PI / 2);
                float sinval = sin(motionAngle);
                //then I want normSin to be from 0 to 1
                float normSin = (sinval + 1) / 2;

                p = map(normSin, 0, 1, origine, destination * 3.75f); //

                blueX = p;
            } else {
                //print("DONE ");
                setterDONE = false;
                setterDoOnce = false;
                setBlue = false;
            }
        }
    }


    private void dayAfterBefore() {
        //onRED
        if (yesterday) {
            //TODO WRITE YESTERDAY
            float radiusR1 = 295;
            float redHourX1 = radiusR1 * -cos(radians(-50));
            float redHourY1 = radiusR1 * -sin(radians(-50));
            //
            pushMatrix();
            translate(redHourX1, redHourY1, 0);
            //ambient(255); //  ambientMaterial(255, 0);
            //texture(redHoursImgs[25]);
            //rect(-70, 0, 128, 64); //>>>>>>>>>>>>>>>>>>>>>>>YESTERDAY SIGN
            shape(yesterdayShape);
            popMatrix();
        }

        //BLEU

        if (tomorow) {
            //TODO WRITE YESTERDAY
            float radiusB1 = 295;
            float blueHourX1 = radiusB1 * cos(radians(50));
            float blueHourY1 = radiusB1 * -sin(radians(50));
            //
            pushMatrix();
            translate(blueHourX1, blueHourY1, 0);
            //ambient(255); //  ambientMaterial(255, 0);
            //texture(blueHoursImgs[25]);
            // rect(70, 0, 128, 64, 10); //>>>>>>>>>>>>>>>>>>>>>>>TOMOROW SIGN
            shape(tomorowShape);
            popMatrix();
        }
    }

    private void globeTouch() {
        //////////////////////////display a sphere where the mouse touches the globe
        float mousePosX;
        float mousePosY;
        //GET CURSOR relative to the globe

        if (mouseX > (cx - globeRadiusInPx) && mouseX < (cx + globeRadiusInPx)) {
            mousePosX = mouseX;
            //println("mousePosX");
        } else {
            mousePosX = cx;
        }
        if (mouseY > (cy - globeRadiusInPx) && mouseY < (cy + globeRadiusInPx)) {
            mousePosY = mouseY;
        } else {
            mousePosY = cy;
        }

        //println("cx : " + cx + " | mouseX : " + mouseX);
        //println("cy : " + cy + " | mouseY : " + mouseY + "---globeRadiusInPx" + globeRadiusInPx);

        //IF CURSOR IN GLOBE
        if (dist(cx, cy, mouseX, mouseY) < globeRadiusInPx) {
            //println("in globe");
            ///////////////////NEW
            //Calculate cursor move in terme of theta and phi effect (arcsin)
            float sinTheta = map(mousePosX, cx - globeRadiusInPx, cx + globeRadiusInPx, -1, 1);
            //println("sinTheta : " + sinTheta);
            float sinPhi = map(mousePosY, cy - globeRadiusInPx, cy + globeRadiusInPx, -1, 1);
            //
            touchTheta = asin(sinTheta);
            touchPhi = -asin(sinPhi);

            ///////////////////
            //return move angles in X and Y
            dummyAngleY = -1 * degrees(touchTheta + PI);
            dummyAngleX = degrees(touchPhi + PI);

            ///////////////////
            //println("touchTheta : " + touchTheta);
        }

        //display 3D dummy in cursor pos
/*
        pushMatrix();
        rotateY(touchTheta + PI);
        rotateX(-1 * (touchPhi + PI));
        translate(0, 0, 255);
        ambient(255);
        sphere(20);
        popMatrix();
*/
    }


    private void touchTracker() {
        ///////////////////////// calculate the X and Y of the TouchPoint (Z ?)
        //float dummyZa = (cos(radians(dummyAngleY)) * 255);
        dummyX = (sin(radians(dummyAngleY)) * 255);
        //print("dummyZa = " + dummyZa + " | dummyX = " + dummyX);
        //float dummyZb = (cos(radians(dummyAngleX)) * 255);
        dummyY = (sin(radians(dummyAngleX)) * 255);
        //
        // print("dummyZa = " + dummyZa + " | dummyZb = " + dummyZb);
        //float dummyZ = dummyZa; //apparently not used
    }



    private void redDotTrackerer() {
        //track interaction mouvements
        redDotAngleX = redX;
        redDotAngleY = redY;
        //CALCULE
        //pour \u03b8 autour de z et \u03d5 autour de ...
        //alors que nous on a width autour de y et height autour de x - > et on cherche z

        //http://mathinsight.org/spherical_coordinates
        float thetaR = -radians(redDotAngleY - worldAngle);
        float phiR = radians(-redDotAngleX + 90);
        //rho = 255;
        // x = \u03c1sin\u03d5 * cos\u03b8
        // y = \u03c1sin\u03d5 * sin\u03b8
        // z = \u03c1cos\u03d5.
        //
        redDotX = rho * -sin(thetaR) * sin(phiR);
        redDotY = rho * -cos(phiR);
        redDotZ = rho * -sin(phiR) * cos(thetaR);
        //END CALCULE
        //
  /*
   //SHOW CALCULATED POSITION
   pushMatrix();
   translate(redDotX, redDotY, redDotZ);
   fill(0, 255, 0);
   //box(20, 20, 20);
   popMatrix();
   //line(0, 0, 0, redDotX, redDotY, 255);
   beginShape();
   vertex(0, 0, 0);
   vertex(redDotX, redDotY, 300);
   //vertex(150, 45,-100);
   endShape();
   */
        //SHOW POINT FROM ANGLES
        if (sin(thetaR - (PI/2)) > 0) {
            pushMatrix();
            //fill(redDotColor);
            //ambient(redDotColor);
            rotateY(thetaR);
            rotateX((PI / 2) + phiR);  //rotateX(3 * (PI / 2) - phiR);
            pushMatrix();
            translate(0, 0, redDotLength + 10);//translate(0, 0, redDotLength + 6);
            //ellipse(0, 0, dotRadius, dotRadius); //>>>>>>>>>>>>>>>>>>>>>>>onRED DOT
            //shape(redDotShape);
            fill(redDotColor);
            ellipse(0, 0, dotRadius, dotRadius);
            popMatrix();
            //
            pushMatrix();
            translate(0, 0, redDotLength);//translate(0, 0, rho + 3 );
            //ambient(0); // ambientMaterial(0, 200);
            //texture(imgShadow);
            //rect(0, 0, 55, 55);
            //shape(dotShadow);

            if(redDotLength > 257) {
                fill(32, 128);
                ellipse(0, 0, dotRadius - 2, dotRadius - 2);
            }
//            int shadowSteps = 6;
//            int alphaStep = 7;
//            float shadowDiameter = (dotRadius + 11) * 1.0f;
//            float stepSize = shadowDiameter / shadowSteps;
//
//            for (int i = shadowSteps; i > 0; i--) {
//                // create a new color for this ring based on the shadowColor
//                int col = color(0, alphaStep);
//                fill(col);

//                pushMatrix();
//                translate(x, y);
            //   ellipse(0, 0, stepSize*i, stepSize*i);
//                popMatrix();
//            }


            popMatrix();
            //
            popMatrix();
        }
    }

    private void blueDotTrackerer() {
        //track interaction mouvements
        blueDotAngleX = blueX;
        blueDotAngleY = blueY;
        //CALCULE
        //pour \u03b8 autour de z et \u03d5 autour de ...
        //alors que nous on a width autour de y et height autour de x - > et on cherche z

        //http://mathinsight.org/spherical_coordinates
        float thetaB = -radians(blueDotAngleY - worldAngle);
        float phiB = radians(-blueDotAngleX + 90);
        //rho = 255;
        // x = \u03c1sin\u03d5 * cos\u03b8
        // y = \u03c1sin\u03d5 * sin\u03b8
        // z = \u03c1cos\u03d5.
        //
        blueDotX = rho * -sin(thetaB) * sin(phiB);
        blueDotY = rho * -cos(phiB);
        blueDotZ = rho * -sin(phiB) * cos(thetaB);
        //END CALCULE
        //

  /*
   //SHOW CALCULATED POSITION
   pushMatrix();
   translate(blueDotX, blueDotY, blueDotZ);
   //fill(0, 255, 0);
   box(20, 20, 20);
   popMatrix();
   //line(0, 0, 0, redDotX, redDotY, 255);
   //beginShape();
   //vertex(0, 0, 0);
   //vertex(blueDotX, blueDotY, 300);
   //vertex(150, 45,-100);
   //endShape();
   */

        ////

        ///
        //SHOW POINT FROM ANGLES
        if (sin(thetaB - (PI/2)) > 0) {
            pushMatrix();
            //fill(blueDotColor);
            //ambient(blueDotColor); //  blueDotColor = color(150, 197, 201);
            rotateY(thetaB);
            rotateX((PI / 2) + phiB);
            pushMatrix();
            translate(0, 0, blueDotLength + 10);
            //ellipse(0, 0, dotRadius, dotRadius); //>>>>>>>>>>>>>>>>>>>>>>>onBLUE DOT
            //shape(blueDotShape);
            fill(blueDotColor);
            ellipse(0, 0, dotRadius, dotRadius);
            popMatrix();
            //
            //SHADOW
            pushMatrix();
            translate(0, 0, blueDotLength);
            //ambient(0); // ambientMaterial(0, 200);
            //texture(imgShadow);
            //rect(0, 0, 55, 55);
            //shape(dotShadow);

            if(blueDotLength > 257) {
                fill(32, 128);
                ellipse(0, 0, dotRadius - 2, dotRadius - 2);
            }
            popMatrix();
            //
            popMatrix();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////
    private void selectorHighlight() {

        // String whatColor = "" ;

        pushStyle();
        fill(0);
        stroke(0);
        textSize(32);

        if (onWhat == onRED) {
            //    whatColor = "RED" ;
            redDotColor = color(249, 112, 118); //old 250, 88, 95
        } else {
            redDotColor = color(250, 88, 95); //old 230, 68, 75
        }
        //

        if (onWhat == onBLUE) {
            //   whatColor = "BLUE" ;
            blueDotColor = color(170, 217, 221);
        } else {
            blueDotColor = color(150, 197, 201);
        }

        if (onWhat == onWHITE){
            //   whatColor = "WHITE" ;

        }
        if (onWhat == onNOTHING){
            //   whatColor = "" ;
        }
        //text(whatColor, -400, -275);
        popStyle();
    }

//////////////////

//    private void newSelector() {
//        float selectionRadius = 50;
//        //
//        if (touched) {
//
//            if (!touchedDoOnce) {
///*
//                if (dist(cx, cy, mouseX, mouseY) < height * 0.5f) {
//
//                    /*
//                    float redDotTracker = dist(redDotX, redDotY, dummyX, dummyY);
//                    float blueDotTracker = dist(blueDotX, blueDotY, dummyX, dummyY);
//
//                    //onRED SELECTOR
//                    if (redDotZ > 0) { // if dot facing screen, to avoid selecting a dot that is behind the globe
//                        if (redDotTracker < selectionRadius) {
//                            onWhat = onRED;
//                        }
//                    }
//                    //onBLUE SELECTOR
//                    if (blueDotZ > 0) {
//                        if (blueDotTracker < selectionRadius) {
//                            onWhat = onBLUE;
//                        }
//                    }
//                    if (redDotTracker > selectionRadius && blueDotTracker > selectionRadius) {
//                        onWhat = onWHITE;
//                        println("onWhat 2 : " + onWhat);
//                    }
//
//
//                } else {
//                    onWhat = onNOTHING;
//                }
//              */
//                //println("onWhat : " + onWhat);
//                touchedDoOnce = true;
//            }
//        }else{
//            touchedDoOnce = false;
//            onWhat = onNOTHING;
//            //println("onWhat : " + onWhat);
//        }
//
//    }
//
//    private void selector() {
//        float selectionRadius = 50;
//        //
//
//        if(touched) {
//            //onRED SELECTOR
//            if (redDotZ > 0) { // if dot facing screen, to avoid selecting a dot that is behind the globe
//                float redDotTracker = dist(redDotX, redDotY, dummyX, dummyY);
//                if (redDotTracker < selectionRadius) {
//                    //print("onRed " + onRed);
//                    redDotColor = color(249, 112, 118); //old 250, 88, 95
//                    //onRed = true;
//                    //redDotLength = -255;
//                } else {
//                    redDotColor = color(250, 88, 95); //old 230, 68, 75
//                    //onRed = false;
//                    //redDotLength = -260;
//                }
//                //print("onRed " + onRed);
//                //
//            }
//
//            //onBLUE SELECTOR
//            if (blueDotZ > 0) {
//                float blueDotTracker = dist(blueDotX, blueDotY, dummyX, dummyY);
//                if (blueDotTracker < selectionRadius) {
//                    //print("touch onBLUE !!");
//                    blueDotColor = color(170, 217, 221);
//                    //onBlue = true;
//                    //blueDotLength = -255;
//                } else {
//                    blueDotColor = color(150, 197, 201);
//                    // onBlue = false;
//                    //blueDotLength = -260;
//                }
//            }
///*
//            if (dist(cx, cy, mouseX, mouseY) < height * 0.5f && !onRed && !onBlue) {
//                onWhite = true;
//                //println("onWhite " + mouseX + " : " + mouseY);
//
//            } else {
//                onWhite = false;
//            }
//            */
//        }
//  /*
//        if (!redSelected && !blueSelected && !whiteSelected) {
//            //print("--");
//            redDotLength = 260;
//            blueDotLength = 260;
//            arcDotLengthPlus = 4;
//        } else if (redSelected) {
//            redDotLength = 256;
//            arcDotLengthPlus = 2;
//            //print("red selected");
//        } else if (blueSelected) {
//            blueDotLength = 256;
//            arcDotLengthPlus = 2;
//            //print("blue selected");
//        } else if (whiteSelected) {
//            //print("white selected");
//        }
//*/
//        // println("onRed : " + onRed + " |onBlue : " + onBlue + " | whiteSelected : " + whiteSelected   );
//    }

    //////////
    private void interactor() {
        //print(tmzGMTrCurrentIndex + " / " + tmzGMTbCurrentIndex + " |gap = " + abs(tmzGMTbCurrentIndex - tmzGMTrCurrentIndex));
  /*
   mousePressed -> get curtainAngle : startX
   mouseisPressed -> curtainAngle = startX + delta
   mouseReleased curtainAngle =
   */
/*
        if (dist(cx, cy, mouseX, mouseY) < height * 0.5f && !onRed && !onBlue ) {
            onWhite = true;
            //println("onWhite " + mouseX + " : " + mouseY);

        } else {
            onWhite = false;
        }
*/

        if (onWhat == onWHITE) {
            //print(dist(cx, cy, mouseX, mouseY));
            //OLD//deltaX = (A - mouseX) / 25;
            deltaX = (degrees(C) - degrees(touchTheta));

            //println("degrees(C) : " +  C  + " | degrees(touchTheta) " + touchTheta  + " => deltaX = " + deltaX); //TODO

            //introduce easing
            float easingGlobe = 0.1f;
            float diffDeltaX = deltaX - easedDeltaX;
            easedDeltaX += diffDeltaX * easingGlobe;
            //
            angleX = startX - (easedDeltaX * 1.25f);
            //print( "angleX " + angleX +  " | startX " + startX +  " | easedDeltaX " + easedDeltaX );


            //int prevMouseX = mouseX;

            //if(touchMoved) {
            speedX = pmouseX - mouseX;
            //}else{
            //    speedX = 0;
            //}
            //println("pmouseX : " + pmouseX + " | mouseX : " + mouseX);
            // println("angleX : " + angleX + " | speedX : " + speedX  + " | deltaX : " + deltaX   );

        } else {
            rpm -= speedX / 25;
        }

        //*************************************************   onRED   ************************************************

        if (onWhat == onRED) {
            //print(dist(cx, cy, mouseX, mouseY));
            //redDeltaX = (A - mouseY) / 5; //
            //redDeltaX = curtainAngle en degree

            //********* MESSED UP SHIT X and Y are INVERTED compared to Globe CONTROLE **********
            float redDeltaX = (degrees(D) - degrees(touchPhi));
            redX = startRedX - redDeltaX;
            //
            //redDeltaY = (B - mouseX) / 5; //
            float redDeltaY = (degrees(C) - degrees(touchTheta));
            if (startRedY + redDeltaY > blueY) {
                redY = startRedY + redDeltaY;
                //print("redDeltaY : " + redDeltaY);
                //print("dummyAngleY : " + dummyAngleY);
                // print(");")
            }
        }
        //********************************************* redX
        if (prevRedHour != redHour) {
            //soundClick.play();
            redHourChange = true;
            //gapChange = true;
            playSound(1);

        }
        prevRedHour = redHour;
        //print(redX);
        if (redX < -46) {
            redX = -46; //-> YESTERDAY
            redHour = -13;
        }

        //lock +-45
        if (redX > 45) {
            redX = 45; //-> HAUT MINUIT
            redHour = 12;
        }
        if (redX < -44 && redX > -46) {
            redX = -45; //-> BAS MINUIT
            redHour = -12;
        }
        //snap red HOURS
        // SNAP red
        if (redX < 1.5f && redX > -1.5f) {
            redX = 0; //MIDI
            redHour = 0;
        } else if (redX < 5.25f && redX > 2.25f) {
            redX = 3.75f;
            redHour = 1;
        } else if (redX < 9 && redX > 6) {
            redX = 7.5f;
            redHour = 2;
        } else if (redX < 12.75f && redX > 9.75f) {
            redX = 11.25f;
            redHour = 3;
        } else if (redX < 16.5f && redX > 13.5f) {
            redX = 15;
            redHour = 4;
        } else if (redX < 20.25f && redX > 17.25f) {
            redX = 18.75f;
            redHour = 5;
        } else if (redX < 24 && redX > 21) {
            redX = 22.5f;
            redHour = 6;
        } else if (redX < 27.75f && redX > 24.75f) {
            redX = 26.25f;
            redHour = 7;
        } else if (redX < 31.5f && redX > 28.5f) {
            redX = 30;
            redHour = 8;
        } else if (redX < 35.25f && redX > 32.25f) {
            redX = 33.75f;
            redHour = 9;
        } else if (redX < 39 && redX > 36) {
            redX = 37.5f;
            redHour = 10;
        } else if (redX < 42.75f && redX > 39.75f) {
            redX = 41.25f;
            redHour = 11;
        }
        /////////////////////////////////////minus
        if (redX > -5.25f && redX < -2.25f) {
            redX = -3.75f;
            redHour = -1;
        } else if (redX > -9 && redX < -6) {
            redX = -7.5f;
            redHour = -2;
        } else if (redX > -12.75f && redX < -9.75f) {
            redX = -11.25f;
            redHour = -3;
        } else if (redX > -16.5f && redX < -13.5f) {
            redX = -15;
            redHour = -4;
        } else if (redX > -20.25f && redX < -17.25f) {
            redX = -18.75f;
            redHour = -5;
        } else if (redX > -24 && redX < -21) {
            redX = -22.5f;
            redHour = -6;
        } else if (redX > -27.75f && redX < -24.75f) {
            redX = -26.25f;
            redHour = -7;
        } else if (redX > -31.5f && redX < -28.5f) {
            redX = -30;
            redHour = -8;
        } else if (redX > -35.25f && redX < -32.25f) {
            redX = -33.75f;
            redHour = -9;
        } else if (redX > -39 && redX < -36) {
            redX = -37.5f;
            redHour = -10;
        } else if (redX > -42.75f && redX < -39.75f) {
            redX = -41.25f;
            redHour = -11;
        }

        //********************************************* redY
        //print(redY);
        //lock +-180
        if (prevImgGMTrCurrentIndex != imgGMTrCurrentIndex) {
            //soundClick2.play();
            redSphereChange = true;
            gapChange = true;
            playSound(2);
            //redSphere.setTexture(imgGMTrCurrent);
        }
        prevImgGMTrCurrentIndex = imgGMTrCurrentIndex;

        if (redY > 180) {
            redY = 180;
            // imgGMTrCurrent = GMT12r;
            imgGMTrCurrent = gmtRmImgs[11];

            imgGMTrCurrentIndex = 12;
            tmzGMTrCurrentIndex = -12;
        }
        if (redY < -165) {
            redY = -165;
            //
            //imgGMTrCurrent = GMTp11r;
            // imgGMTrCurrentIndex = 23;
            // tmzGMTrCurrentIndex = 11;
            //
            //imgGMTrCurrent = GMT12r;
            //imgGMTrCurrentIndex = 12;
            //tmzGMTrCurrentIndex = 12
        }
        // SNAP red TMZ
        if (redY < 5 && redY > -5) {
            redY = 0;
            //imgGMTrCurrent = imgGMTr;
            imgGMTrCurrent = gmtRpImgs[0];
            imgGMTrCurrentIndex = 0;
            tmzGMTrCurrentIndex = 0;
        } //GMT
        else if (redY < 20 && redY > 10) {
            redY = 15;
            //imgGMTrCurrent = GMTm1r;
            imgGMTrCurrent = gmtRmImgs[0];
            imgGMTrCurrentIndex = 1;
            tmzGMTrCurrentIndex = -1;
        } //-1
        else if (redY < 35 && redY > 25) {
            redY = 30;
            //imgGMTrCurrent = GMTm2r;
            imgGMTrCurrent = gmtRmImgs[1];
            imgGMTrCurrentIndex = 2;
            tmzGMTrCurrentIndex = -2;
        } //-2
        else if (redY < 50 && redY > 40) {
            redY = 45;
            //imgGMTrCurrent = GMTm3r;
            imgGMTrCurrent = gmtRmImgs[2];
            imgGMTrCurrentIndex = 3;
            tmzGMTrCurrentIndex = -3;
        } //-3
        else if (redY < 65 && redY > 55) {
            redY = 60;
            //imgGMTrCurrent = GMTm4r;
            imgGMTrCurrent = gmtRmImgs[3];
            imgGMTrCurrentIndex = 4;
            tmzGMTrCurrentIndex = -4;
        } //-4
        else if (redY < 80 && redY > 70) {
            redY = 75;
            //imgGMTrCurrent = GMTm5r;
            imgGMTrCurrent = gmtRmImgs[4];
            imgGMTrCurrentIndex = 5;
            tmzGMTrCurrentIndex = -5;
        } //-5
        else if (redY < 95 && redY > 85) {
            redY = 90;
            //imgGMTrCurrent = GMTm6r;
            imgGMTrCurrent = gmtRmImgs[5];
            imgGMTrCurrentIndex = 6;
            tmzGMTrCurrentIndex = -6;
        } //-6
        else if (redY < 110 && redY > 100) {
            redY = 105;
            //imgGMTrCurrent = GMTm7r;
            imgGMTrCurrent = gmtRmImgs[6];
            imgGMTrCurrentIndex = 7;
            tmzGMTrCurrentIndex = -7;
        } //-7
        else if (redY < 125 && redY > 115) {
            redY = 120;
            //imgGMTrCurrent = GMTm8r;
            imgGMTrCurrent = gmtRmImgs[7];
            imgGMTrCurrentIndex = 8;
            tmzGMTrCurrentIndex = -8;
        } //-8
        else if (redY < 140 && redY > 130) {
            redY = 135;
            //imgGMTrCurrent = GMTm9r;
            imgGMTrCurrent = gmtRmImgs[8];
            imgGMTrCurrentIndex = 9;
            tmzGMTrCurrentIndex = -9;
        } //-9
        else if (redY < 155 && redY > 145) {
            redY = 150;
            //imgGMTrCurrent = GMTm10r;
            imgGMTrCurrent = gmtRmImgs[9];
            imgGMTrCurrentIndex = 10;
            tmzGMTrCurrentIndex = -10;
        } //-10
        else if (redY < 170 && redY > 160) {
            redY = 165;
            //imgGMTrCurrent = GMTm11r;
            imgGMTrCurrent = gmtRmImgs[10];
            imgGMTrCurrentIndex = 11;
            tmzGMTrCurrentIndex = -11;
        } //-11
        /////////////////////////////////////plus
        else if (redY > -20 && redY < -10) {
            redY = -15;
            //imgGMTrCurrent = GMTp1r;
            imgGMTrCurrent = gmtRpImgs[1];
            imgGMTrCurrentIndex = 13;
            tmzGMTrCurrentIndex = 1;
        } //+1
        else if (redY > -35 && redY < -25) {
            redY = -30;
            //imgGMTrCurrent = GMTp2r;
            imgGMTrCurrent = gmtRpImgs[2];
            imgGMTrCurrentIndex = 14;
            tmzGMTrCurrentIndex = 2;
        } //+2
        else if (redY > -50 && redY < -40) {
            redY = -45;
            //imgGMTrCurrent = GMTp3r;
            imgGMTrCurrent = gmtRpImgs[3];
            imgGMTrCurrentIndex = 15;
            tmzGMTrCurrentIndex = 3;
        } //+3
        else if (redY > -65 && redY < -55) {
            redY = -60;
            //imgGMTrCurrent = GMTp4r;
            imgGMTrCurrent = gmtRpImgs[4];
            imgGMTrCurrentIndex = 16;
            tmzGMTrCurrentIndex = 4;
        } //+4
        else if (redY > -80 && redY < -70) {
            redY = -75;
            //imgGMTrCurrent = GMTp5r;
            imgGMTrCurrent = gmtRpImgs[5];
            imgGMTrCurrentIndex = 17;
            tmzGMTrCurrentIndex = 5;
        } //+5
        else if (redY > -95 && redY < -85) {
            redY = -90;
            //imgGMTrCurrent = GMTp6r;
            imgGMTrCurrent = gmtRpImgs[6];
            imgGMTrCurrentIndex = 18;
            tmzGMTrCurrentIndex = 6;
        } //+6
        else if (redY > -110 && redY < -100) {
            redY = -105;
            //imgGMTrCurrent = GMTp7r;
            imgGMTrCurrent = gmtRpImgs[7];
            imgGMTrCurrentIndex = 19;
            tmzGMTrCurrentIndex = 7;
        } //+7
        else if (redY > -125 && redY < -115) {
            redY = -120;
            //imgGMTrCurrent = GMTp8r;
            imgGMTrCurrent = gmtRpImgs[8];
            imgGMTrCurrentIndex = 20;
            tmzGMTrCurrentIndex = 8;
        } //+8
        else if (redY > -140 && redY < -130) {
            redY = -135;
            //imgGMTrCurrent = GMTp9r;
            imgGMTrCurrent = gmtRpImgs[9];
            imgGMTrCurrentIndex = 21;
            tmzGMTrCurrentIndex = 9;
        } //+9
        else if (redY > -155 && redY < -145) {
            redY = -150;
            //imgGMTrCurrent = GMTp10r;
            imgGMTrCurrent = gmtRpImgs[10];
            imgGMTrCurrentIndex = 22;
            tmzGMTrCurrentIndex = 10;
        } //+10
        else if (redY > -170 && redY < -160) {
            redY = -165;
            //imgGMTrCurrent = GMTp11r;
            imgGMTrCurrent = gmtRpImgs[11];
            imgGMTrCurrentIndex = 23;
            tmzGMTrCurrentIndex = 11;
        } //+11



        //*************************************************   onBLUE   ************************************************

        if (onWhat == onBLUE) {
            //print(dist(cx, cy, mouseX, mouseY));

            //blueDeltaX = (A - mouseY) / 5; //
            float blueDeltaX = (degrees(D) - degrees(touchPhi));
            blueX = startBlueX - blueDeltaX;
            //
            //blueDeltaY = (B - mouseX) / 5; //
            float blueDeltaY = (degrees(C) - degrees(touchTheta));
            if (startBlueY + blueDeltaY < redY) {
                blueY = startBlueY + blueDeltaY;
            }
            //print(deltaX);
        }
        //********************************************* blueX
        if (prevBlueHour != blueHour) {
            //soundClick.play();
            //gapChange = true;
            blueHourChange = true;
            playSound(1);
        }
        prevBlueHour = blueHour;
        // print(blueX);
        if (blueX > 46) {
            blueX = 46; //-> TOMORROW
            blueHour = 13;
            // print("TOMORROW");
        }

        //lock +-90
        if (blueX > 44 && blueX < 46) {
            blueX = 45; //-> HAUT MINUIT
            blueHour = 12;
            //print("MINUIT");
        }
        if (blueX < -45) {
            blueX = -45; //-> BAS MINUIT
            blueHour = -12;
        }
        //snap blue HOURS
        // SNAP blue
        if (blueX < 1.5f && blueX > -1.5f) {
            blueX = 0; //MIDI
            blueHour = 0;
        } else if (blueX < 5.25f && blueX > 2.25f) {
            blueX = 3.75f;
            blueHour = 1;
        } else if (blueX < 9 && blueX > 6) {
            blueX = 7.5f;
            blueHour = 2;
        } else if (blueX < 12.75f && blueX > 9.75f) {
            blueX = 11.25f;
            blueHour = 3;
        } else if (blueX < 16.5f && blueX > 13.5f) {
            blueX = 15;
            blueHour = 4;
        } else if (blueX < 20.25f && blueX > 17.25f) {
            blueX = 18.75f;
            blueHour = 5;
        } else if (blueX < 24 && blueX > 21) {
            blueX = 22.5f;
            blueHour = 6;
        } else if (blueX < 27.75f && blueX > 24.75f) {
            blueX = 26.25f;
            blueHour = 7;
        } else if (blueX < 31.5f && blueX > 28.5f) {
            blueX = 30;
            blueHour = 8;
        } else if (blueX < 35.25f && blueX > 32.25f) {
            blueX = 33.75f;
            blueHour = 9;
        } else if (blueX < 39 && blueX > 36) {
            blueX = 37.5f;
            blueHour = 10;
        } else if (blueX < 42.75f && blueX > 39.75f) {
            blueX = 41.25f;
            blueHour = 11;
        }
        /////////////////////////////////////minus
        if (blueX > -5.25f && blueX < -2.25f) {
            blueX = -3.75f;
            blueHour = -1;
        } else if (blueX > -9 && blueX < -6) {
            blueX = -7.5f;
            blueHour = -2;
        } else if (blueX > -12.75f && blueX < -9.75f) {
            blueX = -11.25f;
            blueHour = -3;
        } else if (blueX > -16.5f && blueX < -13.5f) {
            blueX = -15;
            blueHour = -4;
        } else if (blueX > -20.25f && blueX < -17.25f) {
            blueX = -18.75f;
            blueHour = -5;
        } else if (blueX > -24 && blueX < -21) {
            blueX = -22.5f;
            blueHour = -6;
        } else if (blueX > -27.75f && blueX < -24.75f) {
            blueX = -26.25f;
            blueHour = -7;
        } else if (blueX > -31.5f && blueX < -28.5f) {
            blueX = -30;
            blueHour = -8;
        } else if (blueX > -35.25f && blueX < -32.25f) {
            blueX = -33.75f;
            blueHour = -9;
        } else if (blueX > -39 && blueX < -36) {
            blueX = -37.5f;
            blueHour = -10;
        } else if (blueX > -42.75f && blueX < -39.75f) {
            blueX = -41.25f;
            blueHour = -11;
        }

        //********************************************* blueY
        if (prevImgGMTbCurrentIndex != imgGMTbCurrentIndex) {
            // soundClick2.play();
            blueSphereChange = true;
            gapChange = true;
            playSound(2);
            // blueSphere.setTexture(imgGMTbCurrent);
        }
        prevImgGMTbCurrentIndex = imgGMTbCurrentIndex;
        //lock +-180
        if (blueY > 165) {
            blueY = 165;
            //imgGMTbCurrent = GMT12b;
            //imgGMTbCurrentIndex = 12;
            //tmzGMTbCurrentIndex = -12
        }
        if (blueY < -180) {
            blueY = -180;
            //imgGMTbCurrent = GMT12b;
            imgGMTbCurrent = gmtBmImgs[11];
            imgGMTbCurrentIndex = 12;
            tmzGMTbCurrentIndex = 12;
        }

        //SNAP blue
        if (blueY < 5 && blueY > -5) {
            blueY = 0;
            // imgGMTbCurrent = imgGMTb;
            //imgGMTbCurrent = gmtBpImgs[0];
            imgGMTbCurrent = gmtBpImgs[0];
            imgGMTbCurrentIndex = 0;
            tmzGMTbCurrentIndex = 0;
        } //GMT
        else if (blueY < 20 && blueY > 10) {
            blueY = 15;
            //imgGMTbCurrent = GMTm1b;
            imgGMTbCurrent = gmtBmImgs[0];
            imgGMTbCurrentIndex = 1;
            tmzGMTbCurrentIndex = -1;
        } //-1
        else if (blueY < 35 && blueY > 25) {
            blueY = 30;
            //imgGMTbCurrent = GMTm2b;
            imgGMTbCurrent = gmtBmImgs[1];
            imgGMTbCurrentIndex = 2;
            tmzGMTbCurrentIndex = -2;
        } //-2
        else if (blueY < 50 && blueY > 40) {
            blueY = 45;
            //imgGMTbCurrent = GMTm3b;
            imgGMTbCurrent = gmtBmImgs[2];
            imgGMTbCurrentIndex = 3;
            tmzGMTbCurrentIndex = -3;
        } //-3
        else if (blueY < 65 && blueY > 55) {
            blueY = 60;
            //imgGMTbCurrent = GMTm4b;
            imgGMTbCurrent = gmtBmImgs[3];
            imgGMTbCurrentIndex = 4;
            tmzGMTbCurrentIndex = -4;
        } //-4
        else if (blueY < 80 && blueY > 70) {
            blueY = 75;
            //imgGMTbCurrent = GMTm5b;
            imgGMTbCurrent = gmtBmImgs[4];
            imgGMTbCurrentIndex = 5;
            tmzGMTbCurrentIndex = -5;
        } //-5
        else if (blueY < 95 && blueY > 85) {
            blueY = 90;
            //imgGMTbCurrent = GMTm6b;
            imgGMTbCurrent = gmtBmImgs[5];
            imgGMTbCurrentIndex = 6;
            tmzGMTbCurrentIndex = -6;
        } //-6
        else if (blueY < 110 && blueY > 100) {
            blueY = 105;
            //imgGMTbCurrent = GMTm7b;
            imgGMTbCurrent = gmtBmImgs[6];
            imgGMTbCurrentIndex = 7;
            tmzGMTbCurrentIndex = -7;
        } //-7
        else if (blueY < 125 && blueY > 115) {
            blueY = 120;
            //imgGMTbCurrent = GMTm8b;
            imgGMTbCurrent = gmtBmImgs[7];
            imgGMTbCurrentIndex = 8;
            tmzGMTbCurrentIndex = -8;
        } //-8
        else if (blueY < 140 && blueY > 130) {
            blueY = 135;
            //imgGMTbCurrent = GMTm9b;
            imgGMTbCurrent = gmtBmImgs[8];
            imgGMTbCurrentIndex = 9;
            tmzGMTbCurrentIndex = -9;
        } //-9
        else if (blueY < 155 && blueY > 145) {
            blueY = 150;
            //simgGMTbCurrent = GMTm10b;
            imgGMTbCurrent = gmtBmImgs[9];
            imgGMTbCurrentIndex = 10;
            tmzGMTbCurrentIndex = -10;
        } //-10
        else if (blueY < 170 && blueY > 160) {
            blueY = 165;
            //imgGMTbCurrent = GMTm11b;
            imgGMTbCurrent = gmtBmImgs[10];
            imgGMTbCurrentIndex = 11;
            tmzGMTbCurrentIndex = -11;
        } //-11
        /////////////////////////////////////plus
        else if (blueY > -20 && blueY < -10) {
            blueY = -15;
            //imgGMTbCurrent = GMTp1b;
            imgGMTbCurrent = gmtBpImgs[1];
            //imgGMTbCurrent = gmtBpImgs[1]; //   PROBLEM was HERE
            imgGMTbCurrentIndex = 13;
            tmzGMTbCurrentIndex = 1;
        } //+1
        else if (blueY > -35 && blueY < -25) {
            blueY = -30;
            //imgGMTbCurrent = GMTp2b;
            imgGMTbCurrent = gmtBpImgs[2]; //
            imgGMTbCurrentIndex = 14;
            tmzGMTbCurrentIndex = 2;
        } //+2
        else if (blueY > -50 && blueY < -40) {
            blueY = -45;
            //imgGMTbCurrent = GMTp3b;
            imgGMTbCurrent = gmtBpImgs[3];
            imgGMTbCurrentIndex = 15;
            tmzGMTbCurrentIndex = 3;
        } //+3
        else if (blueY > -65 && blueY < -55) {
            blueY = -60;
            //imgGMTbCurrent = GMTp4b;
            imgGMTbCurrent = gmtBpImgs[4];
            imgGMTbCurrentIndex = 16;
            tmzGMTbCurrentIndex = 4;
        } //+4
        else if (blueY > -80 && blueY < -70) {
            blueY = -75;
            //imgGMTbCurrent = GMTp5b;
            imgGMTbCurrent = gmtBpImgs[5];
            imgGMTbCurrentIndex = 17;
            tmzGMTbCurrentIndex = 5;
        } //+5
        else if (blueY > -95 && blueY < -85) {
            blueY = -90;
            //imgGMTbCurrent = GMTp6b;
            imgGMTbCurrent = gmtBpImgs[6];
            imgGMTbCurrentIndex = 18;
            tmzGMTbCurrentIndex = 6;
        } //+6
        else if (blueY > -110 && blueY < -100) {
            blueY = -105;
            //imgGMTbCurrent = GMTp7b;
            imgGMTbCurrent = gmtBpImgs[7];
            imgGMTbCurrentIndex = 19;
            tmzGMTbCurrentIndex = 7;
        } //+7
        else if (blueY > -125 && blueY < -115) {
            blueY = -120;
            //imgGMTbCurrent = GMTp8b;
            imgGMTbCurrent = gmtBpImgs[8];
            imgGMTbCurrentIndex = 20;
            tmzGMTbCurrentIndex = 8;
        } //+8
        else if (blueY > -140 && blueY < -130) {
            blueY = -135;
            //imgGMTbCurrent = GMTp9b;
            imgGMTbCurrent = gmtBpImgs[9];
            imgGMTbCurrentIndex = 21;
            tmzGMTbCurrentIndex = 9;
        } //+9
        else if (blueY > -155 && blueY < -145) {
            blueY = -150;
            //imgGMTbCurrent = GMTp10b;
            imgGMTbCurrent = gmtBpImgs[10];
            imgGMTbCurrentIndex = 22;
            tmzGMTbCurrentIndex = 10;
        } //+10
        else if (blueY > -170 && blueY < -160) {
            blueY = -165;
            //imgGMTbCurrent = GMTp11b;
            imgGMTbCurrent = gmtBpImgs[11];
            imgGMTbCurrentIndex = 23;
            tmzGMTbCurrentIndex = 11;
        } //+11

        /////////////////////////////////////
        if (onWhat == onNOTHING) {
            //print("--");
            redDotLength = 260;
            blueDotLength = 260;
            arcDotLengthPlus = 8;//4
        } else if (onWhat == onRED) {
            redDotLength = 256;
            arcDotLengthPlus = 4;//2
            //print("red selected");
        } else if (onWhat == onBLUE) {
            blueDotLength = 256;
            arcDotLengthPlus = 4;//2
            //print("blue selected");
        } else if (onWhat == onWHITE) {
            //print("white selected");
        }

    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////CONTROL////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void mousePressed() {
        //println("TOUCH mouse " + mouseX + " - " + mouseY);
        //replace A and B by thetaA and phiA
        float selectionRadius = 50;
        easedDeltaX = 0;
        touched = true;
        //println("onWhat : " + onWhat);
        ///////////////
        //Calculate cursor move in terme of theta and phi effect (arcsin)
        if (dist(cx, cy, mouseX, mouseY) < globeRadiusInPx) {
            //println("mouseX " + mouseX + " | cx - globeRadiusInPx  " + (cx - globeRadiusInPx) + " | cx + globeRadiusInPx  " + (cx + globeRadiusInPx)  );
            float sinTheta = map(mouseX, cx - globeRadiusInPx, cx + globeRadiusInPx, -1, 1);
            float sinPhi = map(mouseY, cy - globeRadiusInPx, cy + globeRadiusInPx, -1, 1);
            //
            touchTheta = asin(sinTheta);
            touchPhi = -asin(sinPhi);
            //println( " touchTheta  " + touchTheta  ); //TODO
            //return move angles in X and Y
            dummyAngleY = -1 * degrees(touchTheta + PI);
            dummyAngleX = degrees(touchPhi + PI);

            //println("startX : " + startX + " | mouseX : " + mouseX  + " | touchTheta : " + touchTheta );
            ////////////////////////////////////////////////////////////////
            dummyX = (sin(radians(dummyAngleY)) * 255);
            dummyY = (sin(radians(dummyAngleX)) * 255);
            ////////////////////////////SELECTOR////////////////////////////
            float redDotTracker = dist(redDotX, redDotY, dummyX, dummyY);
            float blueDotTracker = dist(blueDotX, blueDotY, dummyX, dummyY);
            //onRED SELECTOR
            if (redDotZ > 0) { // if dot facing screen, to avoid selecting a dot that is behind the globe
                if (redDotTracker < selectionRadius) {
                    onWhat = onRED;
                }
            }
            //onBLUE SELECTOR
            if (blueDotZ > 0) {
                if (blueDotTracker < selectionRadius) {
                    onWhat = onBLUE;
                }
            }
            if (redDotTracker > selectionRadius && blueDotTracker > selectionRadius) {
                onWhat = onWHITE;
            }
            //println("onWhat 2 : " + onWhat);
            ///////////////////////////////////////////////////////////////////

            if (onWhat == onWHITE) {
                //whiteSelected = true;
                A = mouseX; //in degrees
                C = touchTheta; // in radians
                startX = angleX;
                //println( "angleX " + angleX +  " | startX " + startX  );
            } else {
                A = mouseY;
                C = touchTheta;
                B = mouseX;
                D = touchPhi;
            }
        }else{
            onWhat = onNOTHING;
        }
        ///////////////

        if (onWhat == onBLUE) {
            //blueSelected = true;
            startBlueX = blueX;
            startBlueY = blueY;
        }

        if (onWhat == onRED) {
            //redSelected = true; //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            startRedX = redX;
            startRedY = redY;
        }

/*
        if (onRed && onBlue) {
            redSelected = true; //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            startRedX = redX;
            startRedY = redY;
        }
        */

        //print("pressed : " + angleX);
        startY = mouseY;
        // prevent default
        //return false;
    }


    public void mouseReleased() {
        touched = false;


        if (onWhat == onWHITE) {
            //whiteSelected = false;
            // print("set nothing");
        } else if (onWhat == onBLUE) {
            setBlueStraight();
            setRed = true;
            //blueSelected = false;
            // print("set onRED");
        } else if (onWhat == onRED) {
            setRedStraight();
            setBlue = true;
            //redSelected = false;
            //  print("set onBLUE");
        }

        onWhat = onNOTHING;
        //easedDeltaX = 0;
        //print("width = " + width + " | height" + height);
        // return false;
    }


/*
void mouseWheel(MouseEvent event) {
 //print(event.delta);
 //move the square according to the vertical scroll amount
 //println(event.getCount());
 cameraZ += event.getCount()*10;

 //fov += event.getCount() / 8000;
 //print("width : " + width + " | fov : " + fov);
 //perspective(fov, float(width) / float(height), 0.1, 1000);
 perspective(fov, float(width)/float(height), 0.1, 3000);


 //uncomment to block page scrolling
 //return false;
 }
 */
/*
public void keyPressed() {
  if (key == ' ') {
    drawTheCities = !drawTheCities;
  }
  // return false;
}
*/
////////////////////////////////////////////////////////////////SOUND FUNCTIONS/////////////////

    public void onPause(){
        super.onPause();
        if(appState == PLAYSTATE) {
            appState = PAUSESTATE;
        }
        if (soundOn) {
            pauseSound(1);
            pauseSound(2);
            pauseSound(3);
        }
    }

    public void onResume() {
        super.onResume();
        if (appState == PAUSESTATE) {
            appState = PLAYSTATE;
        }
        if (soundOn) {
            resumeSound(1);
            resumeSound(2);
            resumeSound(3);
        }
    }

    void playSound(int soundID)
    {
        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        //soundPool.stop(streamId); // kill previous sound - quick hack to void mousePressed triggering twice
        //streamId = soundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1f);
        if (soundID == 1){
            //0
            streamIdn0 = soundPool.play(1, 1.0f, 1.0f, 10, 0, 1f);
        }
        if (soundID == 2){
            //2
            streamIdn1 = soundPool.play(2, 1.0f, 1.0f, 10, 0, 1f);
        }
        if (soundID == 3){
            //0
            streamIdn2 = soundPool.play(3, 1.0f, 1.0f, 10, -1, 1f);
        }
    }

    void stopSound(int soundID) {
        if (soundID == 1) {
            soundPool.stop(streamIdn0);
        }
        if (soundID == 2) {
            soundPool.stop(streamIdn1);
        }
        if (soundID == 3) {
            soundPool.stop(streamIdn2);
        }
    }

    void pauseSound(int soundID) {
        if (soundID == 1) {
            soundPool.pause(streamIdn0);
        }
        if (soundID == 2) {
            soundPool.pause(streamIdn1);
        }
        if (soundID == 3) {
            soundPool.pause(streamIdn2);
        }
    }

    void resumeSound(int soundID) {
        if (soundID == 1) {
            soundPool.resume(streamIdn0);
        }
        if (soundID == 2) {
            soundPool.resume(streamIdn1);
        }
        if (soundID == 3) {
            soundPool.resume(streamIdn2);
        }

    }

}
