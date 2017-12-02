package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Camera mCamera;

    private Matrix mMatrix;

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 第一种：用canvas移动实现
        canvas.save();
        mCamera.save();
        mCamera.rotateX(30);
        // canvas的变换顺序是反的
        canvas.translate(
                point1.x + bitmap.getWidth() / 2, point1.y + bitmap.getHeight() / 2);
        mCamera.applyToCanvas(canvas);
        canvas.translate(
                -(point1.x + bitmap.getWidth() / 2), -(point1.y + bitmap.getHeight() / 2));
        mCamera.restore();
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        // 第二种：用Camera的Matrix代替canvas移动
        mCamera.save();
        mCamera.rotateY(30);
        mMatrix.reset();
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preTranslate(
                -(point2.x + bitmap.getWidth() / 2), -(point2.y + bitmap.getHeight() / 2));
        mMatrix.postTranslate(
                point2.x + bitmap.getWidth() / 2, point2.y + bitmap.getHeight() / 2);
        canvas.save();
        canvas.concat(mMatrix);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
