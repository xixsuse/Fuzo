package gg.rich.me.fuzo.sketch;



import java.util.Objects;
import java.util.ResourceBundle;

import gg.rich.me.fuzo.R;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;


/**
 * Created by Rich on 30/10/2017.
 *
 */

public class Interface2D {

    //GLOBAL VARIABLES
    private Fuzo_Main p5;
    //
    private PShape curtainShape;
    private PImage backgroundColor;
    //
    private float x;    // top left corner x position
    private float y;    // top left corner y position
    private float w;    // width of button
    private float h;    // height of button

    //translate of the button
    private float buttonX ;
    private float buttonY ;

    //translate of the curtain
    private float X ;
    private float Y ;

    //chrono
    private int startTime;
    private int stopTime;
    private int duration = 400;//450

    //sin movement
    float curtainAngle = 0;
    private float p;
    private float curtainOffset;

    //bouton sin
    float buttonAngle = 0;
    private float buttonOffset;

    //scroll
    private boolean scrollDone;

    // logicstuff
    private static final int CLOSED     = 0; // const
    private static final int OPENING    = 1;
    private static final int OPEN       = 2;
    private static final int CLOSING    = 3;
    private int menuState;        // current

    private boolean doOnce;
    private boolean initMenu;

    private float cursorA;
    private float cursorB;
    private float cursorC;

    private float oscillator = 0;

    PGraphics titleBadge;
    PGraphics tipBadge;

    //CONSTRUCTOR
    Interface2D(Fuzo_Main _p5){
        //Koan_Main _p5,    String _label,  float xpos,                          float ypos,             float _width,              float _height,   int _side
        //p5,               "oMENU",        p5.width - (80* p5.unitInPx),        15* p5.unitInPx,        60* p5.unitInPx,           60* p5.unitInPx,     1);
        p5 = _p5;
        buttonX = x =  p5.width - (80* p5.unitInPx); //p5.width - (80* p5.unitInPx)  // 453
        buttonY = y = 15* p5.unitInPx; //15* p5.unitInPx
        w = 60* p5.unitInPx ;
        h = 60* p5.unitInPx;

        buttonOffset = (-p5.height/4) - y;

        curtainOffset = (-p5.width);
        p = curtainOffset;
        X = p;
        Y = 0;

        backgroundColor = p5.loadImage("bgColor.png");
        curtainShape = p5.createShape(PApplet.RECT, 0, 0, p5.width, p5.height);
        curtainShape.setTexture(backgroundColor);

        buildTextShape();
    }

    void buildTextShape(){
        int w = (int)(700*p5.unitInPx);
        int h = (int)(75*p5.unitInPx);

        /////////////////////////////PGraphic  I///////////////////////////////////
        titleBadge = p5.createGraphics(w, h);
        titleBadge.beginDraw();
        //titleBadge.pushMatrix();
        //titleBadge.translate(w/2, h/2);

        titleBadge.textAlign(PConstants.LEFT);
        titleBadge.fill(128,92);
        titleBadge.textFont(p5.FontSemiBold);
        titleBadge.textSize(36 *p5.unitInPx);
        titleBadge.text(p5.context.getResources().getString(R.string.FUZO), 20 * p5.unitInPx, 46 * p5.unitInPx);
        //
        titleBadge.textFont(p5.FontRegular);
        titleBadge.textSize(18 *p5.unitInPx);
        titleBadge.text(p5.context.getResources().getString(R.string.title), 116 * p5.unitInPx, 46 * p5.unitInPx);

        //titleBadge.popMatrix();
        titleBadge.endDraw();



        /////////////////////////////PGraphic  II///////////////////////////////////
        tipBadge = p5.createGraphics(w, h);
        tipBadge.beginDraw();
        //tipBadge.background(255,0,0);
        tipBadge.textAlign(PConstants.CENTER);
        tipBadge.fill(128,92);
        //
        tipBadge.textFont(p5.FontItalic);
        tipBadge.textSize(32 *p5.unitInPx);

       // tipBadge.text("Simply move the dots", tipBadge.width/2, 64);

        tipBadge.text(p5.context.getResources().getString(R.string.simply), tipBadge.width/2, 64);
        //tipBadge.text("只需用手移动屏幕中的位置标签！", tipBadge.width/2, 64);
        //tipBadge.text("ポイントを動かすだけの簡単操作。 ", tipBadge.width/2, 64);
        tipBadge.endDraw();


    }

    int getMenuState(){
        return menuState;
    }

    void init(){
        initMenu = true;
        renderCurtain();
    }

    void run(){
        engine();
        renderCurtain();
        renderButton();
        scroll();

        title();
    }

    void title(){

        p5.pushMatrix();
        p5.translate(0, buttonY);
        p5.image(titleBadge, 0,0);
//        p5.pushStyle();
//        //p5.textFont(p5.FontRegular);
//        //p5.textFont(p5.FontItalic);
//        //p5.textFont(p5.FontItalicBold);
//        //p5.textFont(p5.FontSemiBold);
//        //p5.textFont(p5.FontBold);
//        p5.image(titleBadge, 0,0);
//
//        p5.textAlign(PConstants.LEFT);
//        p5.fill(128,128);
//
//        p5.textFont(p5.FontSemiBold);
//        p5.textSize(36 *p5.unitInPx);
////
//         p5.text("FUZO", 20 * p5.unitInPx, 46 * p5.unitInPx);
//
//        //
//        p5.textFont(p5.FontRegular);
//        p5.textSize(18 *p5.unitInPx);
//        p5.text("Time Zone Converter", 116 * p5.unitInPx, 46 * p5.unitInPx);
//        p5.popStyle();
        p5.popMatrix();
        //////////////////////////////////////////////

        p5.pushMatrix();
        p5.translate(0, -buttonY);
        p5.pushStyle();
        p5.imageMode(PApplet.CENTER);
        p5.image(tipBadge, p5.width/2, p5. height - (10 * p5.unitInPx));
        p5.popStyle();

        p5.popMatrix();

    }

    private void engine(){

        if(menuState == CLOSED) {
            if (mouseOver() && p5.mousePressed) {
                menuState = OPENING;

            }
        } else if(menuState == OPEN){
            if (mouseOver() && p5.mousePressed) {
                menuState = CLOSING;
            }
        }
    }

    private void renderCurtain(){
        if(menuState == OPENING){

        }else if(menuState == OPEN){

        }else if(menuState == CLOSING){

        }else if(menuState == CLOSED){

        }

        ///CURTAIN
        if(menuState == OPENING ) {
            if (!doOnce){
                clock();
                scrollDone = false;
                doOnce = true;
            }
            if(scrollDone){
                menuState = OPEN;
                doOnce = false;
            }

        }

        else if(menuState == CLOSING){
            if (!doOnce) {
                clock();
                scrollDone = false;
                doOnce = true;
            }
            if(scrollDone){
                menuState = CLOSED;
                doOnce = false;
            }
        }



        if(menuState == OPENING  || menuState == OPEN  || menuState == CLOSING || initMenu ) {
            p5.pushMatrix();
            if(initMenu){
                p5.translate(-p5.width, 0);
            }else {
                p5.translate(X, Y);  //X curtain POSITION
            }
            //p5.blendMode(p5.REPLACE    ); //SCREEN
            //

            if(oscillator > 1000){
                oscillator = 0;
            } else {
                oscillator += 0.05;
            }


            p5.pushStyle();
            //p5.blendMode(p5.EXCLUSION );
            p5.fill(64, 128 ); //p5.fill(p5.bgColor , 240 );
            p5.rect(0, 0, p5.width + (10 * p5.unitInPx), p5.height);
            p5.popStyle();


            p5.pushStyle();

            p5.textSize(64 *p5.unitInPx);
            p5.textAlign(PConstants.CENTER, PConstants.CENTER);
            p5.blendMode(p5.EXCLUSION );
            p5.fill(240);

            p5.textFont(p5.FontItalic);
            p5.textSize(22 *p5.unitInPx);
            //p5.text("Fuzo tells you what time it is here when it is 'x' there", p5.width/2, 75 * p5.unitInPx);
            p5.text(p5.context.getResources().getString(R.string.Fuzo_tells), p5.width/2, 75 * p5.unitInPx); //TODO ??????????????????????
            /////////////////////

            p5.textFont(p5.FontBold);
            p5.textSize(36 *p5.unitInPx);
            if(!Objects.equals(p5.context.getResources().getString(R.string.fuzo_language), "JA")) {
                p5.text("1", p5.width / 4, p5.height / 2 - (135 * p5.unitInPx));
            }else{
                p5.text("1", p5.width / 4, p5.height / 2 - (150 * p5.unitInPx));
            }

            p5.textSize(26 *p5.unitInPx);
            //p5.text("set the Time Zones", p5.width/4, p5.height/2 - (100 * p5.unitInPx));
            p5.text(p5.context.getResources().getString(R.string.set_timeZones), p5.width/4, p5.height/2 - (100 * p5.unitInPx));

            ////////////////////////////////////////////////////////////////////////////LEFT DOT
            p5.fill(220);
            p5.textSize(2 *p5.unitInPx);
            p5.textFont(p5.FontLight);
            p5.text("|", p5.width/4 - (34 *p5.unitInPx), p5.height/2 - (34 *p5.unitInPx));
            p5.text("|", p5.width/4 - (34 *p5.unitInPx), p5.height/2);
            p5.text("|", p5.width/4 - (34 *p5.unitInPx), p5.height/2 + (34 *p5.unitInPx));
            //
            p5.text("|", p5.width/4 - (94 *p5.unitInPx), p5.height/2 - (34 *p5.unitInPx));
            p5.text("|", p5.width/4 - (94 *p5.unitInPx), p5.height/2);
            p5.text("|", p5.width/4 - (94 *p5.unitInPx), p5.height/2 + (34 *p5.unitInPx));
            //
            //
            //p5.fill(220);
            p5.text("|", p5.width/4 + (34 *p5.unitInPx), p5.height/2 - (34 *p5.unitInPx));
            p5.text("|", p5.width/4 + (34 *p5.unitInPx), p5.height/2);
            p5.text("|", p5.width/4 + (34 *p5.unitInPx), p5.height/2 + (34 *p5.unitInPx));
            //
            p5.text("|", p5.width/4 + (94 *p5.unitInPx), p5.height/2 - (34 *p5.unitInPx));
            p5.text("|", p5.width/4 + (94 *p5.unitInPx), p5.height/2);
            p5.text("|", p5.width/4 + (94 *p5.unitInPx), p5.height/2 + (34 *p5.unitInPx));
            //DOT
            p5.pushMatrix();
            p5.translate(PApplet.sin(oscillator) * 60 * p5.unitInPx, 0);
            p5.blendMode(p5.REPLACE );
            p5.fill(240);
            p5.ellipse(p5.width/4, p5.height/2 , 60 * p5.unitInPx, 60 * p5.unitInPx );
            p5.blendMode(p5.EXCLUSION );
            p5.popMatrix();
            ////////////////////////////////////////////////////////////////////////////LEFT DOT

            p5.fill(240);

            p5.textFont(p5.FontRegular);
            p5.textSize(20 *p5.unitInPx);
            //p5.text("by moving the dots horizontally", p5.width/4, p5.height/2 + (100 * p5.unitInPx));
            p5.text(p5.context.getResources().getString(R.string.horizontally), p5.width/4, p5.height/2 + (100 * p5.unitInPx));

            /////////////////////////SEPARATOR
            p5.textSize(15 *p5.unitInPx);
            p5.textFont(p5.FontLight);
            p5.text("|", p5.width/2, p5.height/2 - (64 *p5.unitInPx));
            p5.text("|", p5.width/2, p5.height/2);
            p5.text("|", p5.width/2, p5.height/2 + (64 *p5.unitInPx));
            /////////////////////SEPARATOR END

            p5.textFont(p5.FontBold);
            p5.textSize(36 *p5.unitInPx);

            if(!Objects.equals(p5.context.getResources().getString(R.string.fuzo_language), "JA")) {
                p5.text("2", (p5.width/4)*3, p5.height/2 - (135 * p5.unitInPx));
            }else{
                p5.text("2", (p5.width/4)*3, p5.height/2 - (150 * p5.unitInPx));
            }

            p5.textSize(26 *p5.unitInPx);
            //p5.text("set the Hour you know", (p5.width/4)*3, p5.height/2 - (100 * p5.unitInPx));
            p5.text(p5.context.getResources().getString(R.string.set_hour), (p5.width/4)*3, p5.height/2 - (100 * p5.unitInPx));

            ////////////////////////////////////////////////////////////////////////////RIGHT DOT
            p5.fill(220);
            p5.textSize(2 *p5.unitInPx);
            p5.textFont(p5.FontLight);
            //
            p5.text("__________", (p5.width/4)*3, p5.height/2 - (78 *p5.unitInPx));
            p5.text("__________", (p5.width/4)*3, p5.height/2 - (54 *p5.unitInPx));
            //
            p5.text("__________", (p5.width/4)*3, p5.height/2 - (30 *p5.unitInPx));
            //
            p5.text("__________", (p5.width/4)*3, p5.height/2 - (6 *p5.unitInPx));
            p5.text("__________", (p5.width/4)*3, p5.height/2 + (18 *p5.unitInPx));
            //
            //DOT
            p5.pushMatrix();
            p5.translate(0, PApplet.cos(oscillator) * 40 * p5.unitInPx);

            p5.blendMode(p5.REPLACE );
            p5.fill(240);
            p5.ellipse((p5.width/4)*3, p5.height/2 , 60 * p5.unitInPx, 60 * p5.unitInPx );
            p5.blendMode(p5.EXCLUSION );
            p5.popMatrix();
            ////////////////////////////////////////////////////////////////////////////RIGHT DOT

            p5.textFont(p5.FontRegular);
            p5.textSize(20 *p5.unitInPx);
            //p5.text("by moving the dots vertically", (p5.width/4)*3, p5.height/2 + (100 * p5.unitInPx));
            p5.text(p5.context.getResources().getString(R.string.vertically), (p5.width/4)*3, p5.height/2 + (100 * p5.unitInPx));

            /////////////////////
            p5.textFont(p5.FontBold);
            p5.textSize(26 *p5.unitInPx);
            //p5.text("...and just read the clocks !", p5.width/2, p5.height - (75 * p5.unitInPx));
            p5.text(p5.context.getResources().getString(R.string.just_read), p5.width/2, p5.height - (75 * p5.unitInPx));


            p5.blendMode(p5.BLEND );
            p5.popStyle();

            p5.popMatrix();

            initMenu = false;
        }

    }

    private void renderButton(){
        ///BOUTON
        if (buttonAngle < 90){ // goes from -90 to 90
            buttonAngle +=3;
        }
        float sinval = PApplet.sin(PApplet.radians(buttonAngle));
        //then I want normSin to be from 0 to 1
        float normSin = (sinval +1)/2;
        buttonY = PApplet.map(normSin, 0, 1, buttonOffset, 0);
        /////////////////////////////////////////////////
        // Restore the base matrix and lighting ready for 2D
        //this.p5.setMatrix(p5.baseMatrix);
        p5.rectMode(PConstants.CORNER);
        p5.ellipseMode(PConstants.CORNER);

        p5.pushStyle();
        p5.pushMatrix();
        p5.translate(0, buttonY);

        p5.textSize(36 *p5.unitInPx);
        p5.textAlign(PConstants.CENTER, PConstants.CENTER);

        //ACTUAL DRAWING

        cursorA = PApplet. map(X, -p5.width, 0.0f, 0.0f, 12.0f);
        cursorB = PApplet.map(X, -p5.width, 0.0f, w/2 + 4* p5.unitInPx, w/4 + 4* p5.unitInPx);
        cursorC = PApplet.map(X, -p5.width, 0.0f, 0 , 1* p5.unitInPx);

        p5.pushStyle();
        p5.pushMatrix();
        p5.translate((x + w/4)-2* p5.unitInPx, (y + w/3.5f) + 12* p5.unitInPx );

        p5.stroke(200);
        p5.strokeWeight(w/12);
        p5.strokeCap(PApplet.ROUND);
        p5.line(cursorC, (-12 + cursorA)* p5.unitInPx, cursorB, -12* p5.unitInPx );
        //
        p5.line(0, 0* p5.unitInPx, (w/2)+ 4* p5.unitInPx, 0* p5.unitInPx );
        //
        p5.line(cursorC, (12 - cursorA)* p5.unitInPx, cursorB, 12*p5. unitInPx );

        p5.popMatrix();
        p5.popStyle();


        p5.popMatrix();

///////////////////////////////////////////////////////


        p5.textSize(12); // to make sure the rest of the text is not that big
        p5.rectMode(PConstants.CENTER);
        p5.ellipseMode(PConstants.CENTER);
        p5.textAlign(PConstants.CENTER, PConstants.BASELINE);
        /////////////////////////////////////////////////
        //PApplet.print("menuState = " + menuState);
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    boolean mouseOver() {
        float gaucheX = x;
        float droiteX = x + w;
        float hautY = y;
        float basY = y + h;

        if (p5.mouseX > gaucheX  && p5.mouseX < droiteX && p5.mouseY > hautY && p5.mouseY < basY) {
            //buttonColor = p5.color(0,255);  	//mouseOver color
            return true;
        }
        //buttonColor = p5.color(64);  		//normal color
        return false;
    }

    void clock() {
        startTime = p5.millis();
        stopTime = startTime + duration;
    }

    private void scroll() {

        //parameters for one way iN, depending on where from
        if (menuState == OPENING) {
            X = p + 1;
        }else if(menuState == CLOSING){
            X = curtainOffset - (p + 1);
        }else if(menuState == OPEN){
            X = 0;
        }else if(menuState == CLOSED){
            X = curtainOffset;
        }

        //this part does the actual movement
        if (!scrollDone) {

            if  (p5.millis () < stopTime) {
                // so curtainAngle must be between -PI/2 and -PI
                curtainAngle = PApplet.map(p5.millis(), startTime, stopTime, -PConstants.PI/2, PConstants.PI/2);
                float sinval = PApplet.sin(curtainAngle);
                //then I want normSin to be from 0 to 1
                float normSin = (sinval +1)/2;
                p = PApplet.map(normSin, 0, 1, curtainOffset, 0);

                //PApplet.print("X = " + X);

            } else {
                //PApplet.print("scrollDone = " + scrollDone);
                p = curtainOffset;
                scrollDone = true;

            }
        }


    }


}
