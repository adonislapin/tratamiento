package com.adonis.tratamientodeimagenes.mvp.view.base;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.adonis.tratamientodeimagenes.R;
import com.adonis.tratamientodeimagenes.activity.MainActivity;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MainView extends ActivityView<MainActivity, Void, Void>  implements View.OnClickListener{

    private MainActivity activity;

    private FloatingActionButton floatingActionButton;
    private ImageView imageView;

    public MainView(MainActivity activity) {
        super(activity);
        ButterKnife.bind(this, activity);
        this.activity = activity;
    }

    public MainActivity getActivity() {
        return activity;
    }

    @OnClick(R.id.fabApplyFilter)
    public void setBlackAndWhiteFilter(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageView.setColorFilter(filter);
    }

    public void setRedChannel(){
        float[] src = new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 255, 0, 0,
                0, 0, 0, 1, 0};

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(src);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(filter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fabApplyFilter){
            setBlackAndWhiteFilter();
        }
    }

    public void init(){
        imageView = getActivity().findViewById(R.id.image);
        floatingActionButton = getActivity().findViewById(R.id.fabApplyFilter);
        floatingActionButton.setOnClickListener(this);

        Glide.with(getActivity()).load(getActivity().getDrawable(R.drawable.img_2)).into(imageView);
    }
}
