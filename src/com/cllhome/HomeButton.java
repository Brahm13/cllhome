package com.cllhome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class HomeButton extends View {
	private Paint hp;

	public HomeButton(Context context) {
		super(context);
		initHomeButton();
	}

	public HomeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHomeButton();
	}

	public HomeButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHomeButton();
	}

	private void initHomeButton() {
		hp = new Paint();
		hp.setAntiAlias(true);
		hp.setColor(R.color.bg);
		hp.setStrokeWidth(2);
		hp.setStyle(Paint.Style.FILL_AND_STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(30, 30, 30, hp);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(60, 60);
	}

}
