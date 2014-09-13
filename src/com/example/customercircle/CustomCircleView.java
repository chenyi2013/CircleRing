package com.example.customercircle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomCircleView extends View {

	private Paint mPaint = new Paint();

	private int selledCount = 15;
	private int totalCount = 360;
	private int yesterdayCount = 38;

	private final int space = 10;

	private String totalTitle = "总房屋套数";
	private String selledTitle = "总销售套数";
	private String yesterDayTitle = "昨日销量";

	private int circularWidth = 250;
	private int smallCircleRadius = 5;
	private int indictorLineWeight = 2;

	private int titleFontSize = 28;
	private int countFontSize = 24;

	private int yesterdayTitleFontSize = 28;
	private int yesterdayCountFontSize = 24;

	private int totalCountTitleColor = Color.GREEN;
	private int totalCountColor = Color.CYAN;

	private int selledCountTitleColor = Color.GREEN;
	private int selledCountColor = Color.CYAN;

	private int yesterdayCountTitleColor = Color.RED;
	private int yesterdayCountColor = Color.BLUE;

	private int totalRingColor = Color.RED;
	private int selledRingColor = Color.GREEN;

	private int lineColor = Color.MAGENTA;

	private final int fontMarginBottom = 10;

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

		int fontHeight = getFontHeight(countFontSize) / 2
				+ getFontHeight(titleFontSize) / 2;
		int fontWidth = getFontWidth(countFontSize) + 20;

		int validWidth = getWidth() - fontWidth * 2 - circularWidth
				- smallCircleRadius * 2;
		int validHeight = getHeight() - fontHeight * 2 - circularWidth
				- smallCircleRadius * 2;

		int startX = fontWidth + circularWidth / 2 + smallCircleRadius;
		int startY = fontHeight + circularWidth / 2 + smallCircleRadius;

		int dia = validWidth > validHeight ? validHeight : validWidth;
		int radius = dia / 2;

		startX = startX + (getWidth() - dia - startX * 2) / 2;
		startY = startY + (getHeight() - dia - startY * 2) / 2;

		int endX = startX + dia;
		int endY = startY + dia;

		int x = startX + radius;// 圆心处在X轴方向的座标
		int y = startY + radius;// 圆心处在Y轴方向的座标

		float selled = convertNumberToPI(selledCount, totalCount);
		float total = convertNumberToPI(selledCount - totalCount, totalCount);

		mPaint.setAntiAlias(true);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setColor(yesterdayCountColor);
		mPaint.setTextSize(yesterdayCountFontSize);
		canvas.drawText(yesterdayCount + "", x, y
				+ getFontHeight(yesterdayTitleFontSize) / 2 + fontMarginBottom,
				mPaint);

		mPaint.setColor(yesterdayCountTitleColor);
		mPaint.setTextSize(yesterdayTitleFontSize);
		mPaint.setStrokeWidth(2);
		canvas.drawText(yesterDayTitle, x, y, mPaint);

		mPaint.setTextAlign(Align.LEFT);
		mPaint.setColor(lineColor);
		if (getXValuse(x, radius, selled / 2) > x) {

			canvas.drawLine(getXValuse(x, radius, selled / 2),
					getYValuse(y, radius, selled / 2), getWidth(),
					getYValuse(y, radius, selled / 2), mPaint);

			canvas.drawCircle(getWidth() - smallCircleRadius, y + radius
					* (float) Math.sin(selled / 2), smallCircleRadius, mPaint);

			mPaint.setColor(selledCountColor);
			mPaint.setTextSize(countFontSize);
			canvas.drawText("" + selledCount, x + radius + circularWidth / 2
					+ space, y + radius * (float) Math.sin(selled / 2)
					- indictorLineWeight / 2 - fontMarginBottom, mPaint);

			mPaint.setColor(selledCountTitleColor);
			mPaint.setTextSize(titleFontSize);
			canvas.drawText(selledTitle,
					x + radius + circularWidth / 2 + space, y + radius
							* (float) Math.sin(selled / 2) - indictorLineWeight
							/ 2 - 2 * fontMarginBottom
							- getFontHeight(countFontSize) / 2, mPaint);

		} else {

			canvas.drawLine(getXValuse(x, radius, selled / 2),
					getYValuse(y, radius, selled / 2), 0,
					getYValuse(y, radius, selled / 2), mPaint);

			canvas.drawCircle(0 + smallCircleRadius,
					y + radius * (float) Math.sin(selled / 2),
					smallCircleRadius, mPaint);

			mPaint.setColor(selledCountColor);
			mPaint.setTextSize(countFontSize);
			canvas.drawText("" + selledCount, 0 + smallCircleRadius * 2, y
					+ radius * (float) Math.sin(selled / 2)
					- indictorLineWeight / 2 - fontMarginBottom, mPaint);

			mPaint.setColor(selledCountTitleColor);
			mPaint.setTextSize(titleFontSize);
			canvas.drawText(selledTitle, 0 + smallCircleRadius * 2, y + radius
					* (float) Math.sin(selled / 2) - indictorLineWeight / 2 - 2
					* fontMarginBottom - getFontHeight(countFontSize) / 2,
					mPaint);

		}

		mPaint.setColor(lineColor);
		if (getXValuse(x, radius, total / 2) > x) {

			canvas.drawLine(getXValuse(x, radius, total / 2),
					getYValuse(y, radius, total / 2), getWidth(),
					getYValuse(y, radius, total / 2), mPaint);

			canvas.drawCircle(getWidth() - smallCircleRadius, y + radius
					* (float) Math.sin(total / 2), smallCircleRadius, mPaint);

			mPaint.setColor(totalCountColor);
			mPaint.setTextSize(countFontSize);
			canvas.drawText("" + totalCount, x + radius + circularWidth / 2
					+ space, y + radius * (float) Math.sin(total / 2)
					- indictorLineWeight / 2 - fontMarginBottom, mPaint);

			mPaint.setColor(totalCountTitleColor);
			mPaint.setTextSize(titleFontSize);
			canvas.drawText(totalTitle, x + radius + circularWidth / 2 + space,
					y + radius * (float) Math.sin(total / 2)
							- indictorLineWeight / 2 - 2 * fontMarginBottom
							- getFontHeight(countFontSize) / 2, mPaint);

		} else {
			canvas.drawLine(getXValuse(x, radius, total / 2),
					getYValuse(y, radius, total / 2), 0,
					getYValuse(y, radius, total / 2), mPaint);

			canvas.drawCircle(0 + smallCircleRadius,
					y + radius * (float) Math.sin(total / 2),
					smallCircleRadius, mPaint);

			mPaint.setColor(totalCountColor);
			mPaint.setTextSize(countFontSize);
			canvas.drawText("" + totalCount, 0 + smallCircleRadius * 2, y
					+ radius * (float) Math.sin(total / 2) - indictorLineWeight
					/ 2 - fontMarginBottom, mPaint);

			mPaint.setColor(totalCountTitleColor);
			mPaint.setTextSize(titleFontSize);
			canvas.drawText(totalTitle, 0 + smallCircleRadius * 2, y + radius
					* (float) Math.sin(total / 2) - indictorLineWeight / 2 - 2
					* fontMarginBottom - getFontHeight(countFontSize) / 2,
					mPaint);
		}

		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(circularWidth);

		mPaint.setColor(totalRingColor);
		canvas.drawArc(new RectF(startX, startY, endX, endY),
				convertNumberToAngle(selledCount, totalCount), 360, false,
				mPaint);

		mPaint.setColor(selledRingColor);
		canvas.drawArc(new RectF(startX, startY, endX, endY), 0,
				convertNumberToAngle(selledCount, totalCount), false, mPaint);

		canvas.save();
	}

	private float getXValuse(float x, float radius, float angle) {
		return x + radius * (float) Math.cos(angle);
	}

	private float getYValuse(float y, float radius, float angle) {
		return y + radius * (float) Math.sin(angle);
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
