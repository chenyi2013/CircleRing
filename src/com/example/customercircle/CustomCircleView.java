package com.example.customercircle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomCircleView extends View {

	private Paint mOuterCircle = new Paint();
	private Paint mInnerCircle = new Paint();
	private Paint mTextPaint = new Paint();

	private int selledCount = 90;
	private int totalCount = 360;
	private int yesterdayCount;

	private int circularWidth = 20;
	private int smallCircleRadius = 25;
	private int indictorLineWeight = 2;

	private int totalCountFontSize = 28;
	private int totalTitleFontSize;

	public CustomCircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomCircleView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mInnerCircle.setAntiAlias(true);
		mInnerCircle.setStyle(Style.STROKE);
		mInnerCircle.setStrokeWidth(circularWidth);
		mInnerCircle.setColor(Color.RED);

		mOuterCircle.setAntiAlias(true);
		mOuterCircle.setStyle(Style.STROKE);
		mOuterCircle.setStrokeWidth(circularWidth / 2);
		mOuterCircle.setColor(Color.GRAY);

		int fontHeight = getFontHeight(totalCountFontSize) * 2;
		int fontWidth = getFontWidth(totalCountFontSize) + 40;

		int validWidth = getWidth() - fontWidth * 2 - circularWidth
				- smallCircleRadius * 2;
		int validHeight = getHeight() - fontHeight * 2 - circularWidth
				- smallCircleRadius * 2;

		int dia = validWidth > validHeight ? validHeight : validWidth;
		int radius = dia / 2;

		float selled = convertNumberToPI(selledCount, 360);
		float total = convertNumberToPI(totalCount - selled, 360);

		canvas.drawArc(new RectF(fontWidth + circularWidth + smallCircleRadius,
				fontHeight + circularWidth + smallCircleRadius,
				fontWidth + dia, fontHeight + dia), 0,
				convertNumberToAngle(selledCount, totalCount), false,
				mInnerCircle);

		// canvas.drawArc(new RectF(fontWidth-14, fontHeight-14, fontWidth + dia
		// + 14,
		// fontHeight + dia + 14), 0, 90, false, mOuterCircle);

		mInnerCircle.setColor(Color.BLUE);
		canvas.drawArc(new RectF(fontWidth + circularWidth + smallCircleRadius,
				fontHeight + circularWidth + smallCircleRadius,
				fontWidth + dia, fontHeight + dia),
				convertNumberToAngle(selledCount, totalCount),
				360 - convertNumberToAngle(selledCount, totalCount), false,
				mInnerCircle);

		mInnerCircle.setStrokeWidth(2);
		canvas.drawLine(
				fontWidth + radius + radius * (float) Math.cos(selled / 2)
						+ circularWidth / 4, fontHeight + radius + radius
						* (float) Math.sin(selled / 2) + circularWidth / 4,
				getWidth(),
				fontHeight + radius + radius * (float) Math.sin(selled / 2)
						+ circularWidth / 4, mInnerCircle);

//		canvas.drawLine(radius + radius * (float) Math.cos(total / 2), radius
//				+ radius * (float) Math.sin(total / 2), 0, radius + radius
//				* (float) Math.sin(total / 2), mInnerCircle);

		mInnerCircle.setStyle(Style.FILL);
		canvas.drawCircle(0 + smallCircleRadius, fontHeight + radius + radius
				* (float) Math.sin(total / 2), smallCircleRadius, mInnerCircle);
		canvas.drawCircle(getWidth() - smallCircleRadius, fontHeight + radius
				+ radius * (float) Math.sin(selled / 2) + circularWidth / 4,
				smallCircleRadius, mInnerCircle);

		Paint paint = new Paint();
		paint.setTextSize(28);

		canvas.drawText("0000000000", fontWidth + radius + radius, fontHeight
				+ radius + radius * (float) Math.sin(selled / 2), paint);

		canvas.drawText("0000000000", 0 + smallCircleRadius * 2, fontHeight
				+ radius + radius * (float) Math.sin(total / 2) - 10, paint);

	}

	/**
	 * 将圆角度数转换成PI值表示
	 * 
	 * @param angle
	 * @return
	 */
	private float convertNumberToPI(float part, float total) {

		return (float) (part / total * 2 * Math.PI);
	}

	private float convertNumberToAngle(float part, float total) {
		return part / total * 360;
	}

	/**
	 * 得到字体高度
	 * 
	 * @param fontSize
	 * @return
	 */
	public int getFontHeight(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.top) + 2;
	}

	/**
	 * 得到文字的宽度
	 * 
	 * @return
	 */
	public int getFontWidth(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		return (int) paint.measureText("0000000000");
	}

}
