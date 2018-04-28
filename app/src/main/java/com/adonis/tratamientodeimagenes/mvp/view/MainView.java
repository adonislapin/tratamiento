package com.adonis.tratamientodeimagenes.mvp.view;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adonis.tratamientodeimagenes.R;
import com.adonis.tratamientodeimagenes.activity.MainActivity;
import com.adonis.tratamientodeimagenes.adapters.AccessoryAdapter;
import com.adonis.tratamientodeimagenes.customviews.StickerImageView;
import com.adonis.tratamientodeimagenes.mvp.view.base.ActivityView;

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
    private RelativeLayout setOfAccessories;

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

        listOfAccessories.setVisibility(View.GONE);
    }

    private void handleAddAccessoryBtn() {
        listOfAccessories.setVisibility(View.VISIBLE);

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
        setOfAccessories = getActivity().findViewById(R.id.setOfAccessories);

        filterBtn.setOnClickListener(this);
        addAccessory.setOnClickListener(this);

        accessoryAdapter = new AccessoryAdapter(getActivity(), loadAccessories(), this);
        listOfAccessories.setAdapter(accessoryAdapter);
        listOfAccessories.setLayoutManager(new GridLayoutManager(getContext(), 3));
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

    @Override
    public void onClickItem(int resource) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                200,
                200);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
/*
        ImageView touchImage = new ImageView(getContext());
        touchImage.setImageResource(resource);
        touchImage.setOnTouchListener(onTouchListener());


        touchImage.setLayoutParams(params);

*/
        StickerImageView imageView = new StickerImageView(getContext());
        imageView.setImageResource(resource);
        imageView.setLayoutParams(params);

        parentView.addView(imageView);

        restoreActionButtons();
    }

    private int _xDelta;
    private int _yDelta;

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            public boolean onTouch(View vi, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) vi.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vi.getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        vi.setLayoutParams(layoutParams);
                        break;
                }
                parentView.invalidate();
                return true;
            }
        };
    }

    private View.OnTouchListener getScaleListener(){
        return new View.OnTouchListener() {
            float centerX, centerY, startR, startScale, startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {

                    // calculate center of image
                    centerX = (v.getLeft() + v.getRight()) / 2f;
                    centerY = (v.getTop() + v.getBottom()) / 2f;

                    // recalculate coordinates of starting point
                    startX = e.getRawX() - v.getX() + centerX;
                    startY = e.getRawY() - v.getY() + centerY;

                    // get starting distance and scale
                    startR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);
                    startScale = v.getScaleX();

                } else if (e.getAction() == MotionEvent.ACTION_MOVE) {

                    // calculate new distance
                    float newR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);

                    // set new scale
                    float newScale = newR / startR * startScale;
                    v.setScaleX(newScale);
                    v.setScaleY(newScale);

                    // move handler image
                    v.setX(centerX + v.getWidth()/2f * newScale);
                    v.setY(centerY + v.getHeight()/2f * newScale);

                } else if (e.getAction() == MotionEvent.ACTION_UP) {

                }
                return true;
            }
        };
    }

}
