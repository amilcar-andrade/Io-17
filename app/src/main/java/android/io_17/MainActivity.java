package android.io_17;

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
        v.setActivated(expanded);
        if (expanded) {
            constraintSetExpanded.applyTo(constraintLayout);
        } else {
            constraintSet.applyTo(constraintLayout);
        }
    }
}
