package com.example.rock.dragnroll;
import android.os.Bundle;
import android.app.Activity;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.*;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.View.DragShadowBuilder;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity implements OnTouchListener,OnDragListener{
    private static final String LOGCAT = null;
    ScaleGestureDetector scaleGestureDetector;
    TextView dragAndScaleText;
    protected static final String TAG = "GestureDetectorMainActivity";
    private GestureDetector mGestureDetector;
    float x1, x2, y1, y2, dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragAndScaleText = (TextView) findViewById(R.id.textView1);
      //  ImageSpan is = new ImageSpan(this, R.drawable.arrows115);
       // SpannableString text = new SpannableString("Lorem ipsum dolor sit amet");
        //text.setSpan(is, 5, 5 + 10, 0);
      //  dragAndScaleText.setCompoundDrawablesWithIntrinsicBounds( 0,
        //        R.drawable.arrows115, 0, 0 );


//       dragAndScaleText.setOnDragListener(this);

        findViewById(R.id.dragableFrame).setOnTouchListener(this);
        findViewById(R.id.pinkLayout).setOnDragListener(this);
        findViewById(R.id.yellowLayout).setOnDragListener(this);
        findViewById(R.id.resizeIcon).setOnTouchListener(this);

//                new OnSwipeTouchListener(this) {
//            @Override
//            public void onSwipeDown() {
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                float size = dragAndScaleText.getTextSize();
//                float factor = 0.10f;
//                float product = size - (size * factor);
//                dragAndScaleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);
//            }
//
//            @Override
//            public void onSwipeUp() {
//            }
//
//            @Override
//            public void onSwipeRight() {
//                float size = dragAndScaleText.getTextSize();
//                float factor = 0.10f;
//                float product = size + (size * factor);
//                dragAndScaleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);
//            }
//
//        });
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());
        setupGestureDetector();
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        scaleGestureDetector.onTouchEvent(event);
//        return true;
//    }
    public boolean onTouch(View view, MotionEvent motionEvent){
    if(view.getId() == R.id.resizeIcon){
        switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = motionEvent.getX();
                    y1 = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    x2 = motionEvent.getX();
                    y2 = motionEvent.getY();
                    dx = x2 - x1;
                    dy = y2 - y1;
                int direction = 1;
               if(dx >0 || dy > 0){
                   direction = -1;
               }
                float size = dragAndScaleText.getTextSize();
                float factor = 0.014f;
                float product = size - (size * factor * direction);
                dragAndScaleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);

                    break;
                case MotionEvent.ACTION_UP:
                    x2 = motionEvent.getX();
                    x2 = motionEvent.getY();
                    break;
        }
        return true;
//        //if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//           // if (motionEvent.getRawX()) {
//                float size = dragAndScaleText.getTextSize();
//                Log.d("TextSizeStart", String.valueOf(size));
//
//                float factor = 0.01f;
//                Log.d("Factor", String.valueOf(factor));
//
//
//                float product = size * factor;
//                Log.d("TextSize", String.valueOf(product));
//                dragAndScaleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);
//                return true;
//            //}
//        }
   }else
    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }else {
           // scaleGestureDetector.onTouchEvent(motionEvent);
            return false;
        }
    }
    public boolean onDrag(View layoutview, DragEvent dragevent) {
        int action = dragevent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(LOGCAT, "Drag event started");
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(LOGCAT, "Drag event entered into "+layoutview.toString());
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.d(LOGCAT, "Drag event exited from "+layoutview.toString());
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                Log.d(LOGCAT, "Drag event exited from "+layoutview.toString());
              break;
            case DragEvent.ACTION_DROP:
                Log.d(LOGCAT, "Dropped");
                View view = (View) dragevent.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                LinearLayout container = (LinearLayout) layoutview;
                container.addView(view);
                view.setX(dragevent.getX() - view.getWidth()/2);
                view.setY(dragevent.getY() - view.getHeight()/2);
                view.setVisibility(View.VISIBLE);
                view.invalidate();
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(LOGCAT, "Drag ended");
                break;
            default:
                break;
        }
        return true;
    }


    public class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
            float size = dragAndScaleText.getTextSize();
            Log.d("TextSizeStart", String.valueOf(size));

            float factor = detector.getScaleFactor();
            Log.d("Factor", String.valueOf(factor));


            float product = size*factor;
            Log.d("TextSize", String.valueOf(product));
            dragAndScaleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);

            size = dragAndScaleText.getTextSize();
            Log.d("TextSizeEnd", String.valueOf(size));
            return true;
        }
    }

    private void setupGestureDetector() {

        mGestureDetector = new GestureDetector(this,

                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onDoubleTapEvent(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                     }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                            float distanceX, float distanceY) {
                         return true;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });
    }
}
