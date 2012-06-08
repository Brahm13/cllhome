package com.cllhome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
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
		Resources r = getResources();
		Shader s = new RadialGradient(30, 30, 30, new int[] {
				r.getColor(R.color.cs1), r.getColor(R.color.cs2),
				getResources().getColor(R.color.cs3) }, null,
				Shader.TileMode.REPEAT);

		hp.setShader(s);
		hp.setStrokeWidth(2);
		hp.setStyle(Paint.Style.FILL);
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
