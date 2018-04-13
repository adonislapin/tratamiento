package com.adonis.tratamientodeimagenes.mvp.view.base;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adonis.tratamientodeimagenes.R;
import com.adonis.tratamientodeimagenes.activity.MainActivity;
import com.adonis.tratamientodeimagenes.adapters.AccessoryAdapter;
import com.adonis.tratamientodeimagenes.mvp.view.IClickInterface;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainView extends ActivityView<MainActivity, Void, Void> implements View.OnClickListener,
        IClickInterface {

    private MainActivity activity;

    private FloatingActionButton filterBtn;
    private FloatingActionButton addAccessory;
    private ImageView imageView;
    private RecyclerView listOfAccessories;
    private View referenceView;
    private AccessoryAdapter accessoryAdapter;
    private RelativeLayout parentView;

    private ArrayList<Object> accessories = new ArrayList<>();

    public MainView(MainActivity activity) {
        super(activity);
        ButterKnife.bind(this, activity);
        this.activity = activity;
    }

    public MainActivity getActivity() {
        return activity;
    }

    @OnClick(R.id.fabApplyFilter)
    public void setBlackAndWhiteFilter() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageView.setColorFilter(filter);
    }

    public void setRedChannel() {
        float[] src = new float[]{
                255, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(src);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(filter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabApplyFilter) {
            setBlackAndWhiteFilter();
        } else if (v.getId() == R.id.addAccessory) {
            if (listOfAccessories.getVisibility() == View.VISIBLE) {
                restoreActionButtons();
            } else {
                handleAddAccessoryBtn();
            }
        }
    }

    private void restoreActionButtons() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) addAccessory.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.RIGHT_OF, referenceView.getId());

        addAccessory.setLayoutParams(params);

        Animation animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
        Animation animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);

        filterBtn.startAnimation(animationIn);
        filterBtn.setVisibility(View.VISIBLE);

        listOfAccessories.startAnimation(animationOut);
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listOfAccessories.setVisibility(View.GONE);
                addAccessory.setImageResource(R.drawable.ic_add_white_24px);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void handleAddAccessoryBtn() {
        Animation animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
        Animation animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);

        listOfAccessories.setVisibility(View.VISIBLE);
        listOfAccessories.startAnimation(animationIn);
        filterBtn.startAnimation(animationOut);

        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                filterBtn.setVisibility(View.GONE);
                addAccessory.setImageResource(R.drawable.ic_clear_white_24px);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) addAccessory.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        params.removeRule(RelativeLayout.RIGHT_OF);

        addAccessory.setLayoutParams(params);
    }

    public void init() {
        listOfAccessories = getActivity().findViewById(R.id.listOfAccessories);
        imageView = getActivity().findViewById(R.id.image);
        filterBtn = getActivity().findViewById(R.id.fabApplyFilter);
        addAccessory = getActivity().findViewById(R.id.addAccessory);
        referenceView = getActivity().findViewById(R.id.refView);
        parentView = getActivity().findViewById(R.id.parentView);

        filterBtn.setOnClickListener(this);
        addAccessory.setOnClickListener(this);

        accessoryAdapter = new AccessoryAdapter(getActivity(), loadAccessories(), this);
        listOfAccessories.setAdapter(accessoryAdapter);
        listOfAccessories.setLayoutManager(new GridLayoutManager(getContext(), 3));

        windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
    }

    private int[] loadAccessories() {
        int[] accessories = new int[]{
                R.drawable.sombrero,
                R.drawable.bigote,
                R.drawable.cerveza,
                R.drawable.corona,
                R.drawable.gafas,
                R.drawable.guitarra
        };

        return accessories;
    }

    public void applyBlurUsingGlide() {
    }

    @Override
    public void onClickItem(int resource) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(resource);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setOnTouchListener(onTouchListener());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
        imageView.setLayoutParams(params);

        parentView.addView(imageView);

        restoreActionButtons();
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        x = event.getX();
                        y = event.getY();
                        dx = x - view.getX();
                        dy = y - view.getY();
                    }
                    break;
                    case MotionEvent.ACTION_MOVE: {
                        view.setX(event.getX() - dx);
                        view.setY(event.getY() - dy);
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        //your stuff
                    }
                }
                    return true;
            }
        };
    }

    int windowwidth;
    int windowheight;
    float x, y, dx, dy;

    private RelativeLayout.LayoutParams layoutParams;

}
