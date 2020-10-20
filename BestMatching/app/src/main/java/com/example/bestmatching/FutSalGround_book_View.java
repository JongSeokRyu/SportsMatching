package com.example.bestmatching;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FutSalGround_book_View extends View {
    private Paint paint;

    public FutSalGround_book_View(Context context) {
        super(context);
    }

    public FutSalGround_book_View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 뷰가 화면에 디스플레이 될때 자동으로 호출
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(); // 페인트 객체 생성
        paint.setColor(Color.RED); // 빨간색으로 설정

    }

    /**
     * 터치 이벤트를 처리
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(super.getContext(), "MotionEvent.ACTION_DOWN : " +
                    event.getX() + ", " + event.getY(), Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }
}

