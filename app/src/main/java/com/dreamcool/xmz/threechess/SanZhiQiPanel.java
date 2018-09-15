package com.dreamcool.xmz.threechess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SanZhiQiPanel extends View {

    private int panelWidth;
    private float lineHeight;
    private  int MAX_LINE = 6;
    private Paint paint = new Paint();

    public SanZhiQiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置背景为半透明红色
        // setBackgroundColor(0x44ff0000);

        initPaint();
    }

    //初始化Paint
    private void initPaint() {
        paint.setColor(0xff000000);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize,heightSize);

        if(widthModel==MeasureSpec.UNSPECIFIED){
            width=heightSize;
        }else if(heightModel==MeasureSpec.UNSPECIFIED){
            width=widthSize;
        }

        setMeasuredDimension(width,width);
    }

    //初始化行宽行高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        panelWidth = w;
        lineHeight = panelWidth*1.0f/MAX_LINE;
    }

    //绘制棋盘
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
    }

    //绘制棋盘
    private void drawBoard(Canvas canvas) {
        int width = panelWidth;
        float mylineHeight = lineHeight;

        //绘制横线
        canvas.drawLine(mylineHeight/2,mylineHeight/2
                ,width-mylineHeight/2,mylineHeight/2,paint);

        canvas.drawLine(mylineHeight/2,width-mylineHeight/2
                ,width-mylineHeight/2,width-mylineHeight/2,paint);

        canvas.drawLine((float) (1.25*mylineHeight),(float) (1.25*mylineHeight)
                ,width-(float) (1.25*mylineHeight),(float) (1.25*mylineHeight),paint);

        canvas.drawLine((float) (1.25*mylineHeight),width-(float) (1.25*mylineHeight)
                ,width-(float) (1.25*mylineHeight),width-(float) (1.25*mylineHeight),paint);

        canvas.drawLine(mylineHeight*2,mylineHeight*2
                ,width-mylineHeight*2,mylineHeight*2,paint);

        canvas.drawLine(mylineHeight*2,width-mylineHeight*2
                ,width-mylineHeight*2,width-mylineHeight*2,paint);

        //绘制竖线
        canvas.drawLine(mylineHeight/2,mylineHeight/2
                ,mylineHeight/2,width-mylineHeight/2,paint);

        canvas.drawLine(width-mylineHeight/2,mylineHeight/2
                ,width-mylineHeight/2,width-mylineHeight/2,paint);

        canvas.drawLine((float) (1.25*mylineHeight),(float) (1.25*mylineHeight)
                ,(float) (1.25*mylineHeight),width-(float) (1.25*mylineHeight),paint);

        canvas.drawLine(width-(float) (1.25*mylineHeight),(float) (1.25*mylineHeight)
                ,width-(float) (1.25*mylineHeight),width-(float) (1.25*mylineHeight),paint);

        canvas.drawLine(mylineHeight*2,mylineHeight*2
                ,mylineHeight*2,width-mylineHeight*2,paint);

        canvas.drawLine(width-mylineHeight*2,mylineHeight*2
                ,width-mylineHeight*2,width-mylineHeight*2,paint);

        //绘制斜线
        canvas.drawLine(mylineHeight/2,mylineHeight/2
                ,mylineHeight*2,mylineHeight*2,paint);

        canvas.drawLine(width-mylineHeight/2,mylineHeight/2
                ,width-mylineHeight*2,mylineHeight*2,paint);

        canvas.drawLine(mylineHeight/2,width-mylineHeight/2
                ,mylineHeight*2,width-mylineHeight*2,paint);

        canvas.drawLine(width-mylineHeight/2,width-mylineHeight/2
                ,width-mylineHeight*2,width-mylineHeight*2,paint);


        canvas.drawLine(panelWidth/2,lineHeight/2,
                panelWidth/2,lineHeight*2,paint);

        canvas.drawLine(panelWidth/2,panelWidth-lineHeight/2
                ,panelWidth/2,panelWidth-lineHeight*2,paint);

        canvas.drawLine(lineHeight/2,panelWidth/2
                ,lineHeight*2,panelWidth/2,paint);

        canvas.drawLine(panelWidth-lineHeight*2,panelWidth/2
                ,panelWidth-lineHeight/2,panelWidth/2,paint);

    }
}
