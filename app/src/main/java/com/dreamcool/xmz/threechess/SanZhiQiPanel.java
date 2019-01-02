package com.dreamcool.xmz.threechess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SanZhiQiPanel extends View {

    private int panelWidth;
    private float lineHeight;
    private int MAX_LINE = 6;
    private Paint paint = new Paint();
    //棋子大
    //小比例
    private float picesProportion = 2 * 1.0f / 4;

    //定义棋子
    private Bitmap whitePices;
    private Bitmap blackPices;

    //变量值为true时白棋先手
    private boolean isWhitePices = true;

    //声明集合保存黑白棋坐标
    List<Point> whitePiceLocation = new ArrayList<>();
    List<Point> blackPiceLocation = new ArrayList<>();

    public SanZhiQiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置背景为半透明红色
        // setBackgroundColor(0x44ff0000);

        initPaint();
        initPices();
    }

    //初始化棋子
    private void initPices() {
        whitePices = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        blackPices = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
    }


    //初始化Paint
    private void initPaint() {
        paint.setColor(0xff663800);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取屏幕宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);


        int width = Math.min(widthSize, heightSize);

        if (widthModel == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightModel == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }

        setMeasuredDimension(width, width);
    }

    //初始化行宽行高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        panelWidth = w;
        lineHeight = panelWidth * 1.0f / MAX_LINE;

        //棋子的长宽
        int picesLength = (int) (lineHeight * picesProportion);

        //定义棋子的大小
        whitePices = Bitmap.createScaledBitmap(whitePices, picesLength, picesLength, false);
        blackPices = Bitmap.createScaledBitmap(blackPices, picesLength, picesLength, false);
    }

    //自定义View点击响应
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //表明自定义View的态度,只要是在自定义View上操作都由自定义View来处理
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {

            //获取点击坐标
            int x = (int) event.getX();
            int y = (int) event.getY();

            //将坐标封装到Point对象中
            Point piceLocation = getValidPoint(x, y);

            //判断点击处是否存在棋子
            if (blackPiceLocation.contains(piceLocation) || whitePiceLocation.contains(piceLocation))
                return false;

            //保存坐标到响应集合
            if (isWhitePices) {
                whitePiceLocation.add(piceLocation);
            } else {
                blackPiceLocation.add(piceLocation);
            }
            //请求重绘
            invalidate();
            isWhitePices = !isWhitePices;
        }

        return true;
    }

    //定义区域点
    private Point getValidPoint(int x, int y) {
        int x1 = 0;
        int y1 = 0;

        if (x < 180 || x > 900 || y < 180 || y > 900) {
            if (x > 180 & x < 900)
                x1 = 3;
            else
                x1 = (int) (x / lineHeight);

            if (y > 180 & y < 900)
                y1 = 3;
            else
                y1 = (int) (y / lineHeight);

        } else if ((x > 180 && x < 315) || (x > 765 && x < 900) ||
                (y > 180 && y < 315) || (y > 765 && y < 900)) {

            if (x > 360 && x < 720)
                x1 = 3;
            else
                x1 = (int) (x / lineHeight);

            if (y > 360 && y < 720)
                y1 = 3;
            else
                y1 = (int) (y / lineHeight);
        } else if (x >= 315 && x <= 765 && y >= 315 && y <= 765) {
            if (x > 405 && x < 675)
                x1 = 3;
            else{
                if (x < 540)
                    x1 = 2;
                else
                    x1 = 4;
            }

            if (y > 405 && y < 675)
                y1 = 3;
            else{
                if (y < 540)
                    y1 = 2;
                else
                    y1 = 4;
            }

        }


        return new Point(x1, y1);
    }

    //绘制棋盘
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制棋盘
        drawBoard(canvas);
        //绘制棋子
        drawPices(canvas);
    }

    //绘制棋子
    private void drawPices(Canvas canvas) {
        //绘制白棋子
        for (int i = 0, n = whitePiceLocation.size(); i < n; i++) {
            Point whitePicesPoint = whitePiceLocation.get(i);

            float left = 0;
            float top = 0;

            int x = whitePicesPoint.x;
            int y = whitePicesPoint.y;

            if (x < 1 || x > 5 || y < 1 || y > 5) {
                if (x == 3)
                    left = x * lineHeight - 45;
                else
                    left = (whitePicesPoint.x + (1 - picesProportion) / 2) * lineHeight;

                if (y == 3)
                    top = y * lineHeight - 45;
                else
                    top = (whitePicesPoint.y + (1 - picesProportion) / 2)
                            * lineHeight;
            } else if ((x >= 1 && x < 1.5) || (x > 4.5 && x < 5.5)
                    || (y >= 1 && y < 1.5) || (y > 4.5 && y < 5.5)) {

                if (x == 3) {
                    left = x * lineHeight - 45;
                } else {
                    if (x >= 1 && x < 1.5)
                        left = (whitePicesPoint.x + (1 - picesProportion) / 2)
                                * lineHeight - 45;
                    else
                        left = (whitePicesPoint.x + (1 - picesProportion) / 2)
                                * lineHeight + 45;
                }

                if (y == 3)
                    top = y * lineHeight - 45;
                else {
                    if (y >= 1 && y < 1.5)
                        top = (whitePicesPoint.y + (1 - picesProportion) / 2)
                                * lineHeight - 45;
                    else
                        top = (whitePicesPoint.y + (1 - picesProportion) / 2)
                                * lineHeight + 45;
                }

            } else {
                if (x == 3) {
                    left = x * lineHeight - 45;
                } else {
                    if (x > 1.5 && x < 2.5)
                        left = (whitePicesPoint.x + (1 - picesProportion) / 2)
                                * lineHeight - 90;
                    else
                        left = (whitePicesPoint.x + (1 - picesProportion) / 2)
                                * lineHeight + 90;
                }

                if (y == 3)
                    top = y * lineHeight - 45;
                else {
                    if (y > 1.5 && y < 2.5)
                        top = (whitePicesPoint.y + (1 - picesProportion) / 2)
                                * lineHeight - 90;
                    else
                        top = (whitePicesPoint.y + (1 - picesProportion) / 2)
                                * lineHeight + 90;
                }
            }


            canvas.drawBitmap(whitePices, left, top, null);

        }

        //绘制黑棋子
        for (int i = 0, n = blackPiceLocation.size(); i < n; i++) {

            Point blackPicesPoint = blackPiceLocation.get(i);

            float left = 0;
            float top = 0;

            float x = blackPicesPoint.x;
            float y = blackPicesPoint.y;

            if (x < 1 || x > 4 || y < 1 || y > 4) {
                if (x == 3)
                    left = x * lineHeight - 45;
                else
                    left = (blackPicesPoint.x + (1 - picesProportion) / 2) * lineHeight;

                if (y == 3)
                    top = y * lineHeight - 45;
                else
                    top = (blackPicesPoint.y + (1 - picesProportion) / 2) * lineHeight;
            } else if ((x >= 1 && x < 1.5) || (x > 3.5 && x < 5.5)
                    || (y >= 1 && y < 1.5) || (y > 3.5 && y < 5.5)) {

                if (x == 3) {
                    left = x * lineHeight - 45;
                } else {
                    if (x >= 1 && x < 1.5)
                        left = (blackPicesPoint.x + (1 - picesProportion) / 2)
                                * lineHeight - 45;
                    else
                        left = (blackPicesPoint.x + (1 - picesProportion) / 2)
                                * lineHeight + 45;
                }

                if (y == 3)
                    top = y * lineHeight - 45;
                else {
                    if (y >= 1 && y < 1.5)
                        top = (blackPicesPoint.y + (1 - picesProportion) / 2)
                                * lineHeight - 45;
                    else
                        top = (blackPicesPoint.y + (1 - picesProportion) / 2)
                                * lineHeight + 45;
                }

            }

            canvas.drawBitmap(blackPices, left, top, null);
        }
    }

    //绘制棋盘
    private void drawBoard(Canvas canvas) {
        int width = panelWidth;
        float mylineHeight = lineHeight;

        //绘制横线
        canvas.drawLine(mylineHeight / 2, mylineHeight / 2
                , width - mylineHeight / 2, mylineHeight / 2, paint);

        canvas.drawLine(mylineHeight / 2, width - mylineHeight / 2
                , width - mylineHeight / 2, width - mylineHeight / 2, paint);

        canvas.drawLine((float) (1.25 * mylineHeight), (float) (1.25 * mylineHeight)
                , width - (float) (1.25 * mylineHeight), (float) (1.25 * mylineHeight), paint);

        canvas.drawLine((float) (1.25 * mylineHeight), width - (float) (1.25 * mylineHeight)
                , width - (float) (1.25 * mylineHeight), width - (float) (1.25 * mylineHeight), paint);

        canvas.drawLine(mylineHeight * 2, mylineHeight * 2
                , width - mylineHeight * 2, mylineHeight * 2, paint);

        canvas.drawLine(mylineHeight * 2, width - mylineHeight * 2
                , width - mylineHeight * 2, width - mylineHeight * 2, paint);

        //绘制竖线
        canvas.drawLine(mylineHeight / 2, mylineHeight / 2
                , mylineHeight / 2, width - mylineHeight / 2, paint);

        canvas.drawLine(width - mylineHeight / 2, mylineHeight / 2
                , width - mylineHeight / 2, width - mylineHeight / 2, paint);

        canvas.drawLine((float) (1.25 * mylineHeight), (float) (1.25 * mylineHeight)
                , (float) (1.25 * mylineHeight), width - (float) (1.25 * mylineHeight), paint);

        canvas.drawLine(width - (float) (1.25 * mylineHeight), (float) (1.25 * mylineHeight)
                , width - (float) (1.25 * mylineHeight), width - (float) (1.25 * mylineHeight), paint);

        canvas.drawLine(mylineHeight * 2, mylineHeight * 2
                , mylineHeight * 2, width - mylineHeight * 2, paint);

        canvas.drawLine(width - mylineHeight * 2, mylineHeight * 2
                , width - mylineHeight * 2, width - mylineHeight * 2, paint);

        //绘制斜线
        canvas.drawLine(mylineHeight / 2, mylineHeight / 2
                , mylineHeight * 2, mylineHeight * 2, paint);

        canvas.drawLine(width - mylineHeight / 2, mylineHeight / 2
                , width - mylineHeight * 2, mylineHeight * 2, paint);

        canvas.drawLine(mylineHeight / 2, width - mylineHeight / 2
                , mylineHeight * 2, width - mylineHeight * 2, paint);

        canvas.drawLine(width - mylineHeight / 2, width - mylineHeight / 2
                , width - mylineHeight * 2, width - mylineHeight * 2, paint);


        canvas.drawLine(panelWidth / 2, lineHeight / 2,
                panelWidth / 2, lineHeight * 2, paint);

        canvas.drawLine(panelWidth / 2, panelWidth - lineHeight / 2
                , panelWidth / 2, panelWidth - lineHeight * 2, paint);

        canvas.drawLine(lineHeight / 2, panelWidth / 2
                , lineHeight * 2, panelWidth / 2, paint);

        canvas.drawLine(panelWidth - lineHeight * 2, panelWidth / 2
                , panelWidth - lineHeight / 2, panelWidth / 2, paint);

    }
}
