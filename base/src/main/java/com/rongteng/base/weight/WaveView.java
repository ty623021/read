package com.rongteng.base.weight;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.rongteng.base.R;

public class WaveView extends View {

	private Paint paint;
	private Path path;

	public WaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(getResources().getColor(R.color.colorPrimary));
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setAntiAlias(true);
		path = new Path();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		path.reset();
		path.moveTo(0, 0);
		LinearGradient shader = new LinearGradient(0,0,0,getMeasuredHeight(), Color.parseColor("#70CAF8"),Color.parseColor("#2F68D6"),Shader.TileMode.CLAMP);
		paint.setShader(shader);
//		path.lineTo(0,getHeight()/2);
//		path.cubicTo(0,getHeight()/2,getWidth()/4,getHeight()/2+40,getWidth()/2,getHeight()/2);
//		path.cubicTo(getWidth()/2,getHeight()/2,(getWidth()/4)*3,getHeight()/2-40,getWidth(),getHeight()/2);
//		path.lineTo(0,getHeight()-40);
//		path.cubicTo(0,getHeight()-40,getWidth()/4,getHeight(),getWidth()/2,getHeight()-40);
//		path.cubicTo(getWidth()/2,getHeight()-40,(getWidth()/4)*3,getHeight()-80,getWidth(),getHeight()-40);
		path.lineTo(0,getMeasuredHeight()-20);
		path.cubicTo(0,getMeasuredHeight()-20,getWidth()/4,getMeasuredHeight()+20,getWidth()/2,getMeasuredHeight()-20);
		path.cubicTo(getWidth()/2,getMeasuredHeight()-20,(getWidth()/4)*3,getMeasuredHeight()-60,getWidth(),getMeasuredHeight()-20);

//		path.lineTo(0,getHeight());
//		path.cubicTo(0,getHeight(),getWidth()/4,getHeight()+40,getWidth()/2,getHeight());
//		path.cubicTo(getWidth()/2,getHeight(),(getWidth()/4)*3,getHeight()-40,getWidth(),getHeight());
		path.lineTo(getWidth(), 0);
		path.close();
		canvas.drawPath(path, paint);

	}




}
