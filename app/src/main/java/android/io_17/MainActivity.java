package android.io_17;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String BUNDLE_EXPANDED = "BUNDLE_EXPANDED";

    private ConstraintSet constraintSet = new ConstraintSet();
    private ConstraintSet constraintSetExpanded = new ConstraintSet();
    private ConstraintLayout constraintLayout;
    private boolean expanded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getResources().getString(R.string.feed));
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint);
        ImageView expandIcon = (ImageView) findViewById(R.id.expand_icon);
        expandIcon.setBackgroundResource(getSelectableBackground());
        constraintSet.load(this, R.layout.activity_main_collapse);
        constraintSetExpanded.load(this, R.layout.activity_main_expand);
        if (savedInstanceState != null) {
            boolean prev = savedInstanceState.getBoolean(BUNDLE_EXPANDED);
            if (prev != expanded) {
                expanded = prev;
                applyConfig(expandIcon);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_EXPANDED, expanded);
    }

    public void expand(View v) {
        TransitionManager.beginDelayedTransition(constraintLayout);
        expanded = !expanded;
        applyConfig(v);
    }

    private void applyConfig(View v) {
        final Animator animator;
        if (expanded) {
            constraintSetExpanded.applyTo(constraintLayout);
            animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.icon_expand );
        } else {
            constraintSet.applyTo(constraintLayout);
            animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.icon_collapse);
        }
        animator.setTarget(v);
        animator.start();
    }

    private int getSelectableBackground() {
        int[] attrs = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? new int[]{R.attr.selectableItemBackgroundBorderless} : new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = MainActivity.this.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        typedArray.recycle();
        return backgroundResource;
    }
}
