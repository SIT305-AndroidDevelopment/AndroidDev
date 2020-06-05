package com.example.assessment_2.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.assessment_2.R;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class SideBar extends View {
    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 触摸字母索引发生变化的回调接口
     */
    private onLetterTouchedChangeListener onLetterTouchedChangeListener = null;
    //侧边栏字母显示
    private String[] alphabet = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"
    };
    //变量 currentChoosenAlphabetIndex 用来标示当前手指触摸的字母索引在 alphabet 数组中的下标
    private int currentChoosenAlphabetIndex = -1;
    //定义画笔
    private Paint paint = new Paint();
    //当手指在 SideBar 上滑动的时候，会有一个 TextView 来显示当前手指触摸的字母索引，所以还需要一个属性
    private TextView textViewDialog = null;

    /**
     * 为SideBar设置显示字母的TextView
     * @param textViewDialog
     */
    public void setTextViewDialog(TextView textViewDialog) {
        this.textViewDialog = textViewDialog;
    }

    /**
     * 绘制列表控件的方法
     * 将要绘制的字母以从上到下的顺序绘制在一个指定区域
     * 如果是进行选中的字母就进行高亮显示
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取SideBar的高度
        int viewHeight = getHeight();
        //获取SideBar的宽度
        int viewWidth = getWidth();
        //获得每个字母索引的高度
        int singleHeight = viewHeight / alphabet.length;

        //绘制每一个字母的索引
        for (int i = 0; i < alphabet.length; i++) {
            paint.setColor(Color.rgb(34, 66, 99));//设置字母颜色
            paint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体
            paint.setTextSize(20);//设置字体大小
            paint.setAntiAlias(true);//抗锯齿

            //如果当前的手指触摸索引和字母索引相同，那么字体颜色进行区分
            if (currentChoosenAlphabetIndex == i) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }

            /*
             * 绘制字体，需要制定绘制的x、y轴坐标
             *
             * x轴坐标 = 控件宽度的一半 - 字体宽度的一半
             * y轴坐标 = singleHeight * i + singleHeight
             */

            float xpos = viewWidth / 2 - paint.measureText(alphabet[i]) / 2;
            float ypos = singleHeight * i + singleHeight;
            canvas.drawText(alphabet[i], xpos, ypos, paint);

            // 重置画笔，准备绘制下一个字母索引
            paint.reset();
        }
    }

    public void setOnLetterTouchedChangeListener(
            onLetterTouchedChangeListener onLetterTouchedChangeListener) {

        this.onLetterTouchedChangeListener = onLetterTouchedChangeListener;
    }

    private onLetterTouchedChangeListener getOnLetterTouchedChangeListener() {
        return onLetterTouchedChangeListener;
    }

    /**
     * 当手指触摸的字母索引发生变化时，调用该回调接口
     *
     * @author owen
     */
    public interface onLetterTouchedChangeListener {
        public void onTouchedLetterChange(String letterTouched);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 触摸事件的代码
        final int action = event.getAction();
        //手指触摸点在屏幕的Y坐标
        final float touchYPos = event.getY();
        // 因为currentChoosenAlphabetIndex会不断发生变化，所以用一个变量存储起来
        int preChoosenAlphabetIndex = currentChoosenAlphabetIndex;
        final onLetterTouchedChangeListener listener = getOnLetterTouchedChangeListener();

        // 比例 = 手指触摸点在屏幕的y轴坐标 / SideBar的高度
        // 触摸点的索引 = 比例 * 字母索引数组的长度
        final int currentTouchIndex = (int) (touchYPos / getHeight() * alphabet.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                // 如果手指没有触摸屏幕，SideBar的背景颜色为默认，索引字母提示控件不可见
                setBackground(new ColorDrawable(0x00000000));
                currentChoosenAlphabetIndex = -1;
                invalidate();
                if (textViewDialog != null) {
                    textViewDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                // 其他情况，比如滑动屏幕、点击屏幕等等，SideBar会改变背景颜色，索引字母提示控件可见，同时需要设置内容
                setBackgroundResource(R.drawable.sidebar_background);

                // 不是同一个字母索引
                if (currentTouchIndex != preChoosenAlphabetIndex) {
                    // 如果触摸点没有超出控件范围
                    if (currentTouchIndex >= 0 && currentTouchIndex < alphabet.length) {
                        if (listener != null) {
                            listener.onTouchedLetterChange(alphabet[currentTouchIndex]);
                        }

                        if (textViewDialog != null) {
                            textViewDialog.setText(alphabet[currentTouchIndex]);
                            textViewDialog.setVisibility(View.VISIBLE);
                        }

                        currentChoosenAlphabetIndex = currentTouchIndex;
                        invalidate();
                    }
                }
                break;
        }

        return super.dispatchTouchEvent(event);
    }
}
